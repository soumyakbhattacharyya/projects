package hudson.plugins.ec2.ssh;

import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.plugins.ec2.EC2Computer;
import hudson.plugins.ec2.EC2ComputerLauncher;
import hudson.plugins.ec2.EC2Constant;
import hudson.plugins.ec2.resource.ServiceLocator;
import hudson.remoting.Channel;
import hudson.slaves.ComputerLauncher;
import hudson.slaves.SlaveComputer;
import hudson.util.IOUtils;
import hudson.util.Secret;
import hudson.util.StreamCopyThread;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dasein.cloud.services.server.Server;
import org.jets3t.service.S3ServiceException;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.ConnectionInfo;
import com.trilead.ssh2.SCPClient;
import com.trilead.ssh2.SFTPException;
import com.trilead.ssh2.SFTPv3Client;
import com.trilead.ssh2.SFTPv3FileAttributes;
import com.trilead.ssh2.SFTPv3FileHandle;
import com.trilead.ssh2.ServerHostKeyVerifier;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

/**
 * {@link ComputerLauncher} that connects to a Unix slave on EC2 by using SSH.
 * 
 * @author Soumyak 
 */
public class EC2UnixLauncher extends EC2ComputerLauncher
{

	/**
	 * Establish a channel on a virtual machine.
	 * 
	 * Steps as below : 
	 * 1. Got the connection using host and port.
	 * 2. Authenticate can be both way either by private key or by password.
	 * 3. Reporting the environment.
	 * 4. Find the java installation location 
	 * 5. Execute the InitScript if not empty 
	 * 6. Copy the slave jar 
	 * 7. Create Root FS directory and give the permission 
	 * 8. Starting the slave jar and establishing the connection 
	 * 9. If something goes wrong close the connection
	 * 
	 */
	protected void launch(EC2Computer computer, PrintStream logger, Server server) throws IOException, InterruptedException, S3ServiceException
	{
		try
		{
			Connection connection = getConnection(computer, logger);

			authenticate(computer, logger, connection);

			reportEnvironment(logger, connection);

			String java = findJava(logger, connection);

			if (StringUtils.isNotEmpty(computer.getNode().initScript)) executeInitScript(computer, logger, connection);

			copySlaveJar(logger, connection);

			boolean hasCreatedRootFS = executeScript("/tmp", "tempCmd.sh", computer.getRootCommandPrefix(), rootFSinitializerCmd(computer.getRootCommandPrefix(), computer.getNode().getRemoteFS()),
					logger, connection);

			logger.println("Created root filesystem on remote ? " + hasCreatedRootFS);
			startSlave(computer, logger, java, connection);
		}
		catch (IOException ioException)
		{
			logger.println(ioException.getMessage());
			throw ioException;
		}

	}

	/**
	 * Preparing Root FS initialization command
	 * 
	 * @param rootCmdPrefix
	 * @param rootFS
	 * @return
	 */
	private String rootFSinitializerCmd(String rootCmdPrefix, String rootFS)
	{

		StringBuilder sb = new StringBuilder();
		sb.append(rootCmdPrefix);
		sb.append(" rm -rf ");
		sb.append(rootFS);
		sb.append(" && ");
		sb.append(rootCmdPrefix);
		sb.append(" mkdir ");
		sb.append(rootFS);
		sb.append(" && ");
		sb.append(rootCmdPrefix);
		sb.append(" chmod -R 777 ");
		sb.append(rootFS);

		return sb.toString();
	}

	/**
	 * Execute Initial Script on an established connection Returns false if not
	 * able to execute else true
	 * 
	 * @param computer
	 * @param logger
	 * @param connection
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException
	 */
	private void executeInitScript(EC2Computer computer, PrintStream logger, Connection connection) throws IOException, InterruptedException, UnsupportedEncodingException
	{
		logger.println("Started executing init script.");
		boolean hasExecutedInitScript = executeScript("/tmp", "init.sh", computer.getRootCommandPrefix(), computer.getNode().initScript, logger, connection);

		if (!hasExecutedInitScript)
		{
			logger.println("init script failed");
			return;
		}

		logger.println("Init script initialization done.");

	}

	/**
	 * This executes arbitrary script on the slave over ssh
	 * 
	 * @param scriptLocation
	 *            where the temp file is being placed
	 * @param scriptName
	 * @param rootCmdPrefix
	 *            sudo for example
	 * @param cmd
	 *            command to be executed
	 * @param logger
	 * @param connection
	 * @return Success (Truth) or Failure (False)
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private boolean executeScript(final String scriptLocation, final String scriptName, final String rootCmdPrefix, final String cmd, PrintStream logger, Connection connection) throws IOException,
			InterruptedException
	{

		SCPClient scp;
		// try {
		scp = connection.createSCPClient();
		logger.println("Exceuting command" + cmd);

		if (StringUtils.isNotBlank(cmd) && connection.exec("test -e /.hudson-run-init", logger) != 0)
		{
			logger.println("Create temporary file to host executable command");
			deleteFileIfFound(rootCmdPrefix, scriptLocation, scriptName, connection, logger);
			scp.put(cmd.getBytes("UTF-8"), scriptName, scriptLocation, "0700");
			Session sess = connection.openSession();
			sess.requestDumbPTY(); // so that the remote side bundles stdout and
									// stderr
			sess.execCommand(rootCmdPrefix + scriptLocation + "/" + scriptName);

			sess.getStdin().close(); // nothing to write here
			sess.getStderr().close(); // we are not supposed to get anything
										// from stderr
			IOUtils.copy(sess.getStdout(), logger);

			int exitStatus = waitCompletion(sess);

			sess.close();
			if (exitStatus != 0)
			{
				logger.println("Script failed: exit code=" + exitStatus);
				// Script execution failed
				throw new RuntimeException("Script execution failed with exit status : " + exitStatus);
			}
			logger.println("Script execution completed");
			// Success
			return Boolean.TRUE;

		}

		// We should not have reached here unless we faced an exceptional
		// scenario

		return Boolean.FALSE;

	}

	/**
	 * Delete the file which is in the specified file location.
	 * 
	 * @param rootCmdPrefix
	 *            -- like sudo
	 * @param fileLocation
	 * @param fileName
	 * @param connection
	 * @param logger
	 * @throws IOException
	 */
	private void deleteFileIfFound(String rootCmdPrefix, String fileLocation, String fileName, Connection connection, PrintStream logger) throws IOException
	{

		try
		{
			Session sess = connection.openSession();
			sess.requestDumbPTY(); // so that the remote side bundles stdout and
									// stderr
			sess.execCommand(rootCmdPrefix + " rm -rf " + fileLocation + "/" + fileName);
			sess.getStdin().close(); // nothing to write here
			sess.getStderr().close(); // we are not supposed to get anything
										// from stderr
			sess.close();
		}
		catch (IOException e)
		{
			logger.println(e.getMessage());
			throw e;
		}

	}

	/**
	 * In case exit status getting delayed wait for 1000ms.
	 * 
	 * @param session
	 * @return
	 * @throws InterruptedException
	 */
	private int waitCompletion(Session session) throws InterruptedException
	{
		// I noticed that the exit status delivery often gets delayed. Wait up
		// to 1 sec.
		for (int i = 0; i < 10; i++)
		{
			Integer r = session.getExitStatus();
			if (r != null) return r;
			Thread.sleep(100);
		}
		return -1;
	}

	/**
	 * Find java installation location using on a connection
	 * 
	 * @param logger
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	private String findJava(PrintStream logger, Connection connection) throws IOException
	{
		String java = null;
		outer: for (JavaProvider provider : javaProviders)
		{
			for (String javaCommand : provider.getJavas(logger, connection))
			{
				try
				{
					java = checkJavaVersion(logger, javaCommand, connection);
					if (java != null)
					{
						break outer;
					}
				}
				catch (IOException e)
				{
					logger.println(e.getMessage());
				}
			}
		}

		if (java == null)
		{
			throw new IOException("Could not find any known supported java version");
		}
		return java;
	}

	/**
	 * Starts the slave process.
	 * 
	 * @param computer
	 *            The computer.
	 * @param listener
	 *            The listener.
	 * @param java
	 *            The full path name of the java executable to use.
	 * @param workingDirectory
	 *            The working directory from which to start the java process.
	 * 
	 * @throws IOException
	 *             If something goes wrong.
	 */
	private void startSlave(SlaveComputer computer, final PrintStream logger, String java, final Connection connection) throws IOException
	{
		String workingDirectory = "/tmp";
		final Session session = connection.openSession();
		// TODO handle escaping fancy characters in paths
		session.execCommand("cd " + workingDirectory + " && " + java + " -jar slave.jar");
		final StreamGobbler out = new StreamGobbler(session.getStdout());
		final StreamGobbler err = new StreamGobbler(session.getStderr());

		// capture error information from stderr. this will terminate itself
		// when the process is killed.
		new StreamCopyThread("stderr copier for remote agent on " + computer.getDisplayName(), err, logger).start();

		try
		{
			computer.setChannel(out, session.getStdin(), logger, new Channel.Listener()
			{
				public void onClosed(Channel channel, IOException cause)
				{
					if (cause != null)
					{
						cause.printStackTrace();
					}
					try
					{
						session.close();
					}
					catch (Throwable t)
					{
						t.printStackTrace();
					}
					try
					{
						out.close();
					}
					catch (Throwable t)
					{
						t.printStackTrace();
					}
					try
					{
						err.close();
					}
					catch (Throwable t)
					{
						t.printStackTrace();
					}
					try
					{
						connection.close();
					}
					catch (Throwable t)
					{
						t.printStackTrace();
					}
				}
			});

		}
		catch (InterruptedException e)
		{
			session.close();
			connection.close();
			throw new IOException("Aborted during connection open");
		}
	}

	/**
	 * Copies the slave jar to the remote system.
	 * 
	 * @param listener
	 *            The listener.
	 * @param workingDirectory
	 *            The directory into whihc the slave jar will be copied.
	 * 
	 * @throws IOException
	 *             If something goes wrong.
	 */
	private void copySlaveJar(PrintStream logger, Connection connection) throws IOException
	{

		String workingDirectory = "/tmp";
		String fileName = workingDirectory + "/slave.jar";

		logger.println("Starting sftp client.");
		SFTPv3Client sftpClient = null;
		try
		{
			sftpClient = new SFTPv3Client(connection);

			try
			{
				SFTPv3FileAttributes fileAttributes;
				try
				{
					fileAttributes = sftpClient.stat(workingDirectory);
				}
				catch (SFTPException e)
				{
					fileAttributes = null;
				}
				if (fileAttributes == null)
				{
					logger.println("Remote FS does not exist.");
					// TODO mkdir -p mode
					sftpClient.mkdir(workingDirectory, 0700);
				}
				else if (fileAttributes.isRegularFile())
				{
					throw new IOException("remote FS is not a regular file.");
				}

				try
				{
					// try to delete the file in case the slave we are copying
					// is shorter than the slave
					// that is already there
					sftpClient.rm(fileName);
				}
				catch (IOException e)
				{
					// the file did not exist... so no need to delete it!
				}

				logger.println("Copying slave jar.");
				SFTPv3FileHandle fileHandle = sftpClient.createFile(fileName);

				InputStream is = null;
				try
				{
					is = new ByteArrayInputStream(Hudson.getInstance().getJnlpJars("slave.jar").readFully());
					// Hudson.getInstance().servletContext.getResourceAsStream("/WEB-INF/slave.jar");
					byte[] buf = new byte[2048];

					int count = 0;
					int len;
					try
					{
						while ((len = is.read(buf)) != -1)
						{
							sftpClient.write(fileHandle, (long) count, buf, 0, len);
							count += len;
						}
						logger.println("Copied " + count + " bytes.");
					}
					catch (Exception e)
					{
						throw new IOException("Error copying slave jar.");
					}
				}
				finally
				{
					if (is != null)
					{
						is.close();
					}
				}
			}
			catch (Exception e)
			{
				throw new IOException("Error copying slave jar.");
			}
		}
		finally
		{
			if (sftpClient != null)
			{
				sftpClient.close();
			}
		}
	}

	/**
	 * Check the java version using java -version command
	 * 
	 * @param logger
	 * @param javaCommand
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	private String checkJavaVersion(PrintStream logger, String javaCommand, Connection connection) throws IOException
	{
		String line = null;
		Session session = connection.openSession();
		try
		{
			session.execCommand(javaCommand + " -version");
			StreamGobbler out = new StreamGobbler(session.getStdout());
			StreamGobbler err = new StreamGobbler(session.getStderr());
			try
			{
				BufferedReader r1 = new BufferedReader(new InputStreamReader(out));
				BufferedReader r2 = new BufferedReader(new InputStreamReader(err));

				// TODO make sure this works with IBM JVM & JRocket

				outer: for (BufferedReader r : new BufferedReader[] { r1, r2 })
				{
					while (null != (line = r.readLine()))
					{
						if (line.startsWith("java version \""))
						{
							break outer;
						}
					}
				}
			}
			finally
			{
				out.close();
				err.close();
			}
		}
		finally
		{
			session.close();
		}

		if (line == null || !line.startsWith("java version \""))
		{
			throw new IOException("The default version of java is either unsupported version or unknown");
		}

		line = line.substring(line.indexOf('\"') + 1, line.lastIndexOf('\"'));

		return javaCommand;
	}

	private static final List<JavaProvider> javaProviders = Arrays.<JavaProvider> asList(new DefaultJavaProvider());

	private static interface JavaProvider
	{

		List<String> getJavas(PrintStream logger, Connection connection);
	}

	private static class DefaultJavaProvider implements JavaProvider
	{
		// TODO : Create constant for these
		public List<String> getJavas(PrintStream logger, Connection connection)
		{
			return Arrays.asList("java", "/usr/bin/java", "/usr/java/default/bin/java", "/usr/java/latest/bin/java", "/Install/jdk1.6.0_32/bin/java", "/EBS/Install/jdk1.6.0_32/bin/java");
		}
	}

	/**
	 * Reporting all the environment variables.
	 * 
	 * @param logger
	 * @param connection
	 * @throws IOException
	 */
	private void reportEnvironment(PrintStream logger, Connection connection) throws IOException
	{
		Session session = connection.openSession();
		try
		{
			session.execCommand("set");
			StreamGobbler out = new StreamGobbler(session.getStdout());
			StreamGobbler err = new StreamGobbler(session.getStderr());
			try
			{
				BufferedReader r1 = new BufferedReader(new InputStreamReader(out));
				BufferedReader r2 = new BufferedReader(new InputStreamReader(err));

				// TODO make sure this works with IBM JVM & JRocket

				String line;
				outer: for (BufferedReader r : new BufferedReader[] { r1, r2 })
				{
					while (null != (line = r.readLine()))
					{
						//logger.println(line);
					}
				}
			}
			finally
			{
				out.close();
				err.close();
			}
		}
		finally
		{
			session.close();
		}
	}

	/**
	 * Validate authentication for remote user.
	 * 
	 * @param computer
	 * @param logger
	 * @throws IOException
	 */
	private void authenticate(EC2Computer computer, PrintStream logger, Connection connection) throws IOException
	{
		int tryCount = 1;
		String retry_count_value = ServiceLocator.getInstance()
		.getEc2VirtualmachineIpDiscoveryTime();
		int retry_count =0;
		
		if(retry_count_value != null && !retry_count_value.isEmpty()){
			retry_count = Integer.parseInt(retry_count_value.trim());
		}else{
			retry_count = EC2Constant.RETRY_COUNT;
		}
		
		do
		{
			try
			{
				ConnectionInfo ci = connection.connect(new ServerHostKeyVerifier()
				{
					public boolean verifyServerHostKey(String hostname, int port, String serverHostKeyAlgorithm, byte[] serverHostKey) throws Exception
					{
						return true;
					}
				});
				//prittyPrintConnectionInfo(ci, logger);
				break;
			}
			catch (IOException ioex)
			{
				// Assuming that at times connection may not be established for
				// various reasons including temporary network issue
				// try RETRY_COUNT times before quieting
				logger.println("Exception occured while authenticating connection" + ioex.getMessage());
				ioex.printStackTrace();
				logger.println(" Retrying " + tryCount + " times ");
				tryCount++;
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		while (tryCount <= retry_count);

		// TODO if using a key file, use the key file instead of password
		boolean isAuthenticated = false;
		String privatekey = Secret.toString(computer.getNode().parentCloudPrivateKey.exposeSecret());
		if (privatekey.length() > 0)
		{
			logger.println("Trying to connect with private key.");
			isAuthenticated = connection.authenticateWithPublicKey(computer.getRemoteAdmin(), privatekey.toCharArray(), "");
		}
		if (!isAuthenticated)
		{
			logger.println("Trying to connect with password.");
			isAuthenticated = connection.authenticateWithPassword(computer.getRemoteAdmin(), computer.getRemoteAdminPassword());
		}
		if (isAuthenticated && connection.isAuthenticationComplete())
		{
			logger.println("User is authenticated.");
		}
		else
		{
			logger.println("Authentication failed.");
			connection.close();
			connection = null;
			logger.println("Connection closed.");
			throw new IOException("Connection failed due to authentication.");
		}
	}

	private void prittyPrintConnectionInfo(ConnectionInfo ci, PrintStream logger)
	{
		logger.println("ci " + ci.clientToServerCryptoAlgorithm);
		logger.println("ci " + ci.clientToServerMACAlgorithm);
		logger.println("ci " + ci.keyExchangeAlgorithm);
		logger.println("ci " + ci.keyExchangeCounter);
		logger.println("ci " + ci.serverHostKeyAlgorithm);
		logger.println("ci " + ci.serverToClientCryptoAlgorithm);
	}

	/**
	 * Creates a new SSH Connection to the newly launched VM.
	 * 
	 * @param computer
	 * @param logger
	 * @throws IOException
	 */
	private Connection getConnection(EC2Computer computer, PrintStream logger) throws IOException
	{
		boolean noIpFound = true;
		String host = null;
		Connection connection = null;
		while (noIpFound)
		{
			// Always look for public DNS Address if present
			// If not then look for private DNS Address
			String hostWithPrivateIp = computer.updateInstanceDescription().getPrivateDnsAddress();
			host = computer.updateInstanceDescription().getPublicDnsAddress();

			if (host == null)
			{
				if (hostWithPrivateIp != null && !hostWithPrivateIp.trim().isEmpty() && StringUtils.isNotBlank(hostWithPrivateIp))
				{
					host = hostWithPrivateIp;
					noIpFound = false;
				}
			}
			else
			{
				if ("0.0.0.0".equals(host))
				{
					// 0.0.0.0 is a false positive. We still haven't got the
					// correct IP. The while loop needs to continue
					noIpFound = true;
				}
				else
				{
					noIpFound = false;
				}
			}
		}
		int port = computer.getSshPort();

		if (host != null && port != -1)
		{
			connection = new Connection(host, port);
		}
		return connection;
	}

	public Descriptor<ComputerLauncher> getDescriptor()
	{
		throw new UnsupportedOperationException();
	}
}
