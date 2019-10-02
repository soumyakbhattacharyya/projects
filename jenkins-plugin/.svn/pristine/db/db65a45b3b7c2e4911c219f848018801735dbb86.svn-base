package hudson.plugins.ec2;


/*import com.xerox.amazonws.ec2.EC2Exception;
import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.Jec2;*/
//import com.xerox.amazonws.ec2.EC2Exception;
//import com.xerox.amazonws.ec2.InstanceType;
//import com.xerox.amazonws.ec2.Jec2;
import hudson.model.Computer;
import hudson.model.Descriptor.FormException;
import hudson.model.Hudson;
import hudson.model.Slave;

import hudson.plugins.ec2.ssh.EC2UnixLauncher;
import hudson.plugins.ec2.util.PlatformInfraUtil;
import hudson.slaves.NodeProperty;
import hudson.Extension;
import hudson.Util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Slave running on EC2.
 * 
 * @author Kohsuke Kawaguchi
 */
public final class EC2Slave extends Slave {
    /**
     * Comes from {@link SlaveTemplate#initScript}.
     */
    public final String initScript;
    public final String remoteAdmin; // e.g. 'ubuntu'
    public final String remoteAdminPassword; //e.g. password for ubuntu
    public final String rootCommandPrefix; // e.g. 'sudo'
    public final String jvmopts; //e.g. -Xmx1g
    public final String secretKey;
    public final String accessId;
    public final String cloudType;
    public final String accountNumber;
    public final String userId;
    public final EC2PrivateKey parentCloudPrivateKey; 
    public final boolean isExistingInstance;
    public final String projectUserID;
    public final String projectID;
    
    /**
     * For data read from old Hudson, this is 0, so we use that to indicate 22.
     */
    private final int sshPort;

    public EC2Slave(String remoteAdminPassword, boolean isExistingInstance, String accountNumber, 
    		String userId, String secretKey,String accessId,String cloudType,
    		String instanceId, String description, String remoteFS, int sshPort, 
    		int numExecutors, String labelString, String initScript, 
    		String remoteAdmin, String rootCommandPrefix, 
    		String jvmopts, EC2PrivateKey parentCloudPrivateKey,String projectUserID,String projectID) throws FormException, IOException {
        this(remoteAdminPassword,isExistingInstance,accountNumber,userId,secretKey,accessId,
        		cloudType,instanceId, description, remoteFS, sshPort,
        		numExecutors, Mode.NORMAL, labelString, initScript, 
        		Collections.<NodeProperty<?>>emptyList(), remoteAdmin, 
        		rootCommandPrefix, jvmopts, parentCloudPrivateKey,projectUserID,projectID);
    }

    @DataBoundConstructor
    public EC2Slave(String remoteAdminPassword, boolean isExistingInstance,String accountNumber,
    		String userId, String secretKey,String accessId,String cloudType,String instanceId,
    		String description, String remoteFS, int sshPort, int numExecutors,
    		Mode mode, String labelString, String initScript, 
    		List<? extends NodeProperty<?>> nodeProperties, String remoteAdmin,
    				String rootCommandPrefix, String jvmopts,
    				EC2PrivateKey parentCloudPrivateKey,String projectUserID,String projectID) throws FormException, IOException {
        super(instanceId, description, remoteFS, numExecutors, mode, labelString, new EC2UnixLauncher(), new EC2RetentionStrategy(), nodeProperties);
        this.initScript  = initScript;
        this.remoteAdmin = remoteAdmin;
        this.rootCommandPrefix = rootCommandPrefix;
        this.jvmopts = jvmopts;
        this.sshPort = sshPort;
        this.secretKey = secretKey;
        this.accessId = accessId;
        this.cloudType = cloudType;
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.isExistingInstance = isExistingInstance;
        this.parentCloudPrivateKey = parentCloudPrivateKey;
        this.remoteAdminPassword = remoteAdminPassword;
        this.projectUserID = projectUserID;
        this.projectID = projectID;
    }

    /**
     * Constructor for debugging.
     */
    public EC2Slave(String remoteAdminPassword, boolean isExistingInstance, String accountNumber,
    		String userId, String secretKey,String accessId,String cloudType,String instanceId
    		,String projectUserID,String projectID) throws FormException, IOException {
        this(remoteAdminPassword, isExistingInstance, accountNumber, userId, secretKey,
        		accessId,cloudType,instanceId,"debug","/tmp/hudson", 22, 1, 
        		Mode.NORMAL, "debug", "", Collections.<NodeProperty<?>>emptyList(),
        		null, null, null, null,projectUserID,projectID);
    }

    /**
     * See http://aws.amazon.com/ec2/instance-types/
     */
    /*package*/ static int toNumExecutors(JPaaSInstanceType it) {
        switch (it) {
        case DEFAULT:       return 1;
        case MICRO: 		return 1;
        case HIGHCPUMEDIUM: return 5;
        case LARGE: 		return 4;
        case XLARGE: 		return 8;
        case HIGHCPULARGE:  return 20;
        default:            throw new AssertionError();
        }
    }
    
    
    static int toNumExecutorsForVMWare(VMWareCpuType cpuType) {
        switch (cpuType) {
        case OnevCpu:       return 1;
        case TwovCpu: 		return 2;
        case ThreevCpu: 	return 3;
        default:            throw new AssertionError();
        }
    }
    
    

    /**
     * EC2 instance ID.
     */
    public String getInstanceId() {
        return getNodeName();
    }

    @Override
    public Computer createComputer() {
        return new EC2Computer(this);
    }

    
    /**
     * CTSJPAASBAAS-5 - Terminates the instance in Eucalyptus with additional parameters
     * 
     * This method is responsible for terminating the instance except two cases.
     * 	1.Delete the instance from UI
     * 	2.Delete Vm due to retention Strategy 
     * 
     * LOGIC --->
     * 	a. if VM is going to be a Type of J (JIT)
     * 		It will terminate and deregister from Jenkins Tree.
     * 
     * 	b. if VM is going to be a Type of P (POLLED)
     * 		It will not going to do anything
     * 
     * 	c. if VM is going to be a Type of T (Third Party)
     * 		It will be only deregistered from Jenkins tree
     * 
     *  d. if VM is going to be a Type of N (Anything else)
     *  	It will not going to do anything
     *  
     */
    public void terminateInstance(Slave s,String...argToTerminateInstance) throws Exception {
		
    	String accountNumber = this.accountNumber;
    	String userId = this.userId;
    	String instanceId = ((EC2Slave)s).getInstanceId();
    	LOGGER.info(" Terminating instance with following information "+accountNumber+" accountNumber "+userId+" userId "+instanceId + " instanceId ");
    	//TerminateInstance ti = new TerminateInstance();
    	
    	
    	if(null != argToTerminateInstance && argToTerminateInstance.length==1){
    		
    		String vmType = argToTerminateInstance[0];
    		LOGGER.info("Vm Type :: "+vmType);
    		
    		if(vmType.equalsIgnoreCase("J")){
    			LOGGER.info("terminateWithDeregister is true");	
        		//ti.terminateInstance(accountNumber, userId, instanceId, this.secretKey, this.accessId, this.cloudType);
    			PlatformInfraUtil.terminateVirtualMachine(accountNumber, instanceId, userId, 
    					this.secretKey, this.accessId, this.cloudType,projectUserID,projectID);
    			LOGGER.info("Removing the node from Jenkins registered nodes : "+getInstanceId());
                Hudson.getInstance().removeNode(this);
        		
    		}else if(vmType.equalsIgnoreCase("P")){
    			LOGGER.info("As Vm type is P, Vm is not being terminated or deregistered.");
    		}else if(vmType.equalsIgnoreCase("T")){
    			LOGGER.info(" deregister node  : "+getInstanceId());
                Hudson.getInstance().removeNode(this);
    		}else{
    			LOGGER.info("As Vm type is N, Vm is not being terminated or deregistered.");
    		}
    		
    	}
    	
    /*	String terminateWithDeregister = "";
    	terminateWithDeregister = System.getProperty(EC2Constant.TERMINATE_WITH_DEREGISTER);
    	if( null==terminateWithDeregister || terminateWithDeregister.equalsIgnoreCase("TRUE")){
    		LOGGER.info("terminateWithDeregister is true");	
    		ti.terminateInstance(accountNumber, userId, instanceId, this.secretKey, this.accessId, this.cloudType);
    	}
    	LOGGER.info("Removing the node from Jenkins registered nodes : "+getInstanceId());
        Hudson.getInstance().removeNode(this);*/
    }
    
    /**
     * CTSJPAASBAAS-5 - Terminates the instance in Eucalyptus with additional parameters
     * 
     * This method is responsible for terminating the Instance in below two cases ::
     * 	1. If the Instance is going to be deleted from UI
     * 	2. If the Instance is going to be deleted due to Retention Strategy
     * 
     *  Both the cases Instance will be deleted and Unregistered.
     * 
     */
    public void terminateInstance(String instanceIdToBeTerminated,String projectUserID,String projectID, String...argToTerminateInstance) throws Exception {
		
    	String accountNumber = this.accountNumber;
    	String userId = this.userId;
    	String instanceId = instanceIdToBeTerminated;
    	LOGGER.info(" Terminating instance with following information "+accountNumber+" accountNumber "+userId+" userId "+instanceId + " instanceId ");
    	//TerminateInstance ti = new TerminateInstance();
    	
    	//String terminateWithDeregister = "";
    	//terminateWithDeregister = System.getProperty(EC2Constant.TERMINATE_WITH_DEREGISTER);
    	//if( null==terminateWithDeregister || terminateWithDeregister.equalsIgnoreCase("TRUE")){
    		LOGGER.info("terminateWithDeregister is true with additional params");
    		//ti.terminateInstance(accountNumber, userId, instanceId, this.secretKey, this.accessId, this.cloudType);
    		
    		PlatformInfraUtil.terminateVirtualMachine(accountNumber, instanceId, userId, 
    				this.secretKey, this.accessId, this.cloudType,projectUserID,projectID);
    		    		
    	//}
		LOGGER.info("Removing the node from Jenkins registered nodes : "+getInstanceId());
        Hudson.getInstance().removeNode(this);
    }
    

    String getRemoteAdmin() {
        if (remoteAdmin == null || remoteAdmin.length() == 0)
            return "root";
        return remoteAdmin;
    }
    
    String getRemoteAdminPassword() {
        if (remoteAdminPassword == null || remoteAdminPassword.length() == 0)
            return "";
        return remoteAdminPassword;
    }    

    String getRootCommandPrefix() {
        if (rootCommandPrefix == null || rootCommandPrefix.length() == 0)
            return "";
        return rootCommandPrefix + " ";
    }

    String getJvmopts() {
        return Util.fixNull(jvmopts);
    }

    public int getSshPort() {
        return sshPort!=0 ? sshPort : 22;
    }

    @Extension
    public static final class DescriptorImpl extends SlaveDescriptor {
        public String getDisplayName() {
            return "Amazon EC2";
        }

        @Override
        public boolean isInstantiable() {
            return false;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(EC2Slave.class.getName());

	public String getProjectUserID() {
		return projectUserID;
	}

	public String getProjectID() {
		return projectID;
	}
    
    
}
