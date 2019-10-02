package hudson.plugins.cloudset.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHUtil {

	private static final Logger l = Logger.getLogger(SSHUtil.class);

	static int checkAck(InputStream in) throws IOException {

		int b = in.read();
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}

	public static void main(String[] args) {

		try {
			sendFile("D:/Backup/VirtualMachineSoapServiceService.xml","/EBS/MyFiles/","root","root",null,
					"10.242.138.100", 22, 0);
		} catch (Exception e1) {
			System.out.println("Error occured while copying file " + e1);
		}

	}

	public static boolean sendFile(String sourceFilename, String destFilename,
			String userId,String password,String keyFileLoc, String hostName, int portNumber,
			int maxwait) throws Exception{

		FileInputStream fis = null;

		try {
			JSch jsch = new JSch();
			// adding the private key to the identity
			Session session = jsch.getSession(userId, hostName, portNumber);
			session.setTimeout((int) maxwait);
			if (null != keyFileLoc) {
				jsch.addIdentity(keyFileLoc);
			}else{
				session.setPassword(password);
			}
			

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			l.info("Set timeout to " + maxwait);
			l.info("Connecting to " + hostName + ":" + portNumber);
			session.connect();

			String command = "scp -p -t " + destFilename;
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			if (checkAck(in) != 0) {
				return false;
			}

			// send "C0644 filesize filename" where filename doesn't contain a /
			long filesize = (new File(sourceFilename)).length();
			command = "C0644 " + filesize + " ";
			if (sourceFilename.lastIndexOf('/') > 0) {
				command += sourceFilename.substring(sourceFilename
						.lastIndexOf('/') + 1);
			} else {
				command += sourceFilename;
			}
			command += "\n";

			out.write(command.getBytes());
			out.flush();

			if (checkAck(in) != 0) {
				return false;
			}

			// send the contents of the source file
			fis = new FileInputStream(sourceFilename);
			byte[] buf = new byte[1024];
			while (true) {
				int len = fis.read(buf, 0, buf.length);

				if (len <= 0) {
					break;
				}

				out.write(buf, 0, len);
			}

			fis.close();
			fis = null;

			// send '\0' to end it
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();

			if (checkAck(in) != 0) {
				return false;
			}

			out.close();
			channel.disconnect();
			session.disconnect();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			throw e;
		}		
	}

}
