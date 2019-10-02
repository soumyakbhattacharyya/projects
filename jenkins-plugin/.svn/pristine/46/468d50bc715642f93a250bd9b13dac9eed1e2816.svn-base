package hudson.plugins.cloud;

import hudson.model.Computer;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.Label;
import hudson.model.Node;
import hudson.plugins.cloud.constant.ClientConstants;
import hudson.plugins.cloud.util.PlatformInfraUtil;
import hudson.slaves.Cloud;
import hudson.slaves.NodeProvisioner.PlannedNode;
import hudson.util.FormValidation;
import hudson.util.Secret;
import hudson.util.StreamTaskListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import jenkins.model.Jenkins;

import org.acegisecurity.Authentication;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

//import com.cts.cloudcoe.cloudplugin.action.InstanceAction;
//import com.cts.cloudcoe.cloudplugin.action.IpAddressAction;


/**
 * Hudson's view of VMware Cloud. 
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class PrivateCloud extends Cloud {

    private final String accessId;
    private final Secret securityCredential;
    private final VMwarePrivateKey privateKey;
     
      
    protected PrivateCloud(String id, String accessId, String securityCredential, String privateKey) {
        super(id);
        this.accessId = accessId.trim();
        this.securityCredential = Secret.fromString(securityCredential.trim());
        this.privateKey = new VMwarePrivateKey(privateKey);
    }

   
    /**
     * CTSJPAASBAAS-8
     * These abstract methods, implemented by subclasses help this to discover these information from cloud specific pages
     */  
	public abstract String getVmOwnerId();
	public abstract String getAccountId();

    protected Object readResolve() {       
        return this;
    }

    public String getAccessId() {
        return accessId;
    }
    
    public String getSecurityCredential() {
        return securityCredential.getEncryptedValue();
    }

    public VMwarePrivateKey getPrivateKey() {
        return privateKey;
    }
    
    public Secret getSecret() {
        return privateKey.exposeSecret();
    }

       
    	
		
	/**
	 * Extract the VMWare specific templates
	 * We first get the list of the cloud available in the 
	 * Jenkins instance and then cloud objects and then return the 
	 * list templates
	 * 
	 * @return List of VMWare Templates
	 */
	private List<VMWareSlaveTemplate> extractVMWareTemplates(){
		Hudson h = Hudson.getInstance();
		Hudson.CloudList cl = h.clouds;
		for (Cloud c : cl)
		{
			if(c != null && c instanceof VMWareCloud){
							
				VMWareCloud cloud = (VMWareCloud)c;				
				
					if(cloud.equals(this)){
						return cloud.getVmwareTemplates();
					}
				
			}
		}
		return null;
	}
	
	/**
     * Filter and return templates which are not JIT or existing instance type for VMWare only
     * @return List of templates that can be used by Jenkins admin to spawn a VM manually from UI
     */
	public List<VMWareSlaveTemplate> getVMWareTemplatesForProvisionFromUI()
	{

		LOGGER.info("Searching VMWare Templates for Provision From UI");
		List<VMWareSlaveTemplate> vmwareTemplates = extractVMWareTemplates();
		List<VMWareSlaveTemplate> pooledVMWareTemplates = new ArrayList<VMWareSlaveTemplate>();
		
		if(vmwareTemplates != null){
			
			for (VMWareSlaveTemplate vmWareSlaveTemplate : vmwareTemplates)
			{
				String labelString = vmWareSlaveTemplate.getLabelString(); 
				if (!(labelString.contains(VMwareConstant.THIRDPARTY) ||
				  labelString.contains(VMwareConstant.JIT))) {
					pooledVMWareTemplates.add(vmWareSlaveTemplate);
				}
			}
		}
		return Collections.unmodifiableList(pooledVMWareTemplates);
		
	}

	
	
	/**
	 * Return a Template corresponding to an AMI when the AMI label does not contain JIT# and EXT# tags
	 * @param ami
	 * @return
	 */
    public Object getTemplate(String ami) {
               
        List<VMWareSlaveTemplate> vmwareTemplateList = extractVMWareTemplates();
        
        if(vmwareTemplateList != null){
        	for (VMWareSlaveTemplate vmWareSlaveTemplate : vmwareTemplateList)
			{
				String templateAmi = vmWareSlaveTemplate.ami; 
				String labelString = vmWareSlaveTemplate.getLabelString();
				if (templateAmi.equals(ami) && (!(labelString.contains(VMwareConstant.THIRDPARTY)) &&
				  (!labelString.contains(VMwareConstant.JIT)))) {
					return vmWareSlaveTemplate;
				}
			}
        }
        return null;
    }
    

    /**
	 * Return a Template corresponding to a label
	 * @param ami
	 * @return
	 */
    public Object getTemplate(Label label) {
                
        List<VMWareSlaveTemplate> vmwareTemplateList = extractVMWareTemplates();
        
        if(vmwareTemplateList != null){
        	for (VMWareSlaveTemplate vmWareSlaveTemplate : vmwareTemplateList)
			{
				if (label == null || label.matches(vmWareSlaveTemplate.getLabelSet())) {
					return vmWareSlaveTemplate;
				}
			}
        }
        return null;
    }
    
    /**
	 * Return a Template corresponding to a label specific to a cloud
	 * We are adding overloaded version of this method to make sure
	 * that we send the label specific to a cloud
	 * @param ami
	 * @return
	 */
    public Object getTemplate(Label label,Cloud c) {
      if(c instanceof VMWareCloud){
    	   List<VMWareSlaveTemplate> vmwareTemplateList = extractVMWareTemplates();
           
           if(vmwareTemplateList != null){
           	for (VMWareSlaveTemplate vmWareSlaveTemplate : vmwareTemplateList)
   			{
   				if (label == null || label.matches(vmWareSlaveTemplate.getLabelSet())) {
   					return vmWareSlaveTemplate;
   				}
   			}
           }
       } 
        return null;
    }

    
    /**
     * Counts the number of instances in cloud which are currently running
     * <p>
     * This includes those instances that may be started outside Hudson.
     */
    public int countCurrentVMwareSlaves(String accountNumber, String userId, String providerId,String projectUsrID,
    		String projectId) throws Exception  {           	
    	    	
        return PlatformInfraUtil.getVirtualMachineListCount(accountNumber, userId,
        		securityCredential.getPlainText(), accessId, providerId,projectUsrID,projectId);
    	
    }

     
    
    /**
     * Provision a new VM for a template when called from 
     * Jenkins UI using Admin UI
     * 
     * @param req
     * @param rsp
     * @param ami
     * @throws ServletException
     * @throws IOException
     */
    public void doProvision(StaplerRequest req, StaplerResponse rsp, @QueryParameter String ami) throws ServletException, IOException {

    	LOGGER.info("Do provision method started with image id :: "+ami);
    	String projectUserID="";
    	String projectID = "";

    	HttpSession sess = Stapler.getCurrentRequest().getSession(false);
       	
       	if(sess!= null && sess.getAttribute(ClientConstants.USER_ID)!= null && sess.getAttribute(ClientConstants.PROJECT_ID)!= null){
    		try{    			
    			projectUserID = (String)sess.getAttribute(ClientConstants.USER_ID);
    			projectID =(String)sess.getAttribute(ClientConstants.PROJECT_ID);
    		}catch(Exception e){
    			LOGGER.severe("EXCEPTION OCCURED WHILE FETCHING SESSION DATA "+e.getMessage());
    		}
    	}     
    	
    	
    	checkPermission(PROVISION);
        if(ami==null) {
        	LOGGER.info("ami is null");
            sendError("The 'ami' query parameter is missing",req,rsp);
            return;
        }
        
        
        Object t = getTemplate(ami);
     	        
        if(t==null) {
        	LOGGER.info("Slave template is null");
            sendError("No such AMI: "+ami,req,rsp);
            return;
        }
        StringWriter sw = new StringWriter();
        StreamTaskListener listener = new StreamTaskListener(sw);
        try {
        	//String label = t.labels;
			LOGGER.info("Account :: "+ getAccountId()+" :: user "+ getVmOwnerId());
            VMwareSlave node = getSlaveFromUI(listener,t,getAccountId(),getVmOwnerId(),projectUserID,projectID);
            Hudson.getInstance().addNode(node);
            LOGGER.info("Node created successfully... Do provision method end");
            rsp.sendRedirect2(req.getContextPath()+"/computer/"+node.getNodeName());
        } catch (IOException e) {
            e.printStackTrace(listener.error(e.getMessage()));
            sendError(sw.toString(),req,rsp);
        }
        catch (Exception e) {
            //e.printStackTrace(listener.error(e.getMessage()));
            sendError(e.getMessage(),req,rsp);
            return;
        }

    }
     
    /**
     * Get the cloud type based on cloud name
     * @param cloudName
     * @return e.g 01, 02 ,..
     */
	private String getCloudType(String cloudName){	
		
		switch(CloudName.valueOf(cloudName)){		
		case vmware:       return VMwareConstant.VMWareCloud;
		default:           throw new AssertionError();
		
		}
		
		
    }
    /**
     * Get a new slave based on template and  account
     * 
     * @param listener
     * @param label
     * @param t
     * @param account
     * @param keyForUser
     * @param groupForUser
     * @param userIdForUser
     * @return
     * @throws IOException
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */
    private VMwareSlave getSlaveFromUI(StreamTaskListener listener, Object obj, String account,
			 String userIdForUser,String projectUserID,String projectID) throws IOException, SecurityException, NoSuchMethodException {

    	LOGGER.info("Going to launch a new VM as triggered by UI");
    	VMwareSlave s = null;    	
    	if (obj instanceof VMWareSlaveTemplate){
         	 
        	VMWareSlaveTemplate t = (VMWareSlaveTemplate)obj;
        	// Get project specific information
     		String accountNumber = getAccountId();
     		String userId = getVmOwnerId(); 
     		String imageId = t.ami;
     		String kernelId = null;
     		final String imageType = t.cpuType.getTypeId()+":"+t.ramType.getTypeId();
     		//String imageType = t.type.getTypeId();		
     		int minInstance = 1;
     		int maxInstance = 1;
     		String cloudType = getCloudType(t.parent.name);
     		
     		// String cloudName = t.parent.name;
     		// LOGGER.info(" Cloud type is " + cloudName);    	
     	    s = t.provisionFromUI(listener, accountNumber,
     					userId, imageId, kernelId, imageType, minInstance,
     					maxInstance, securityCredential.getPlainText(),
     					accessId, cloudType,privateKey, t.labels,projectUserID,projectID);
     		
         }
    	
    	 return s;
		
	}
    
    /**
     * Spawning a new VM based on label and workload
     * 
     * @param Label
     * @param excessWorkload in int
     * 
     *//*
    
    public Collection<PlannedNode> provision(Label label, int excessWorkload) {
        try {

        	// Get the build that is pending in the queue for the given label
            Object obj = getTemplate(label);

            List<PlannedNode> r = new ArrayList<PlannedNode>();
            
            if(obj instanceof VMWareSlaveTemplate){
            	final VMWareSlaveTemplate t = (VMWareSlaveTemplate)obj; 
            	for( ; excessWorkload>0; excessWorkload-- ) {                    
                     r.add(new PlannedNode(t.getDisplayName(),
                             Computer.threadPoolForRemoting.submit(new Callable<Node>() {
                                 public Node call() throws Exception {
                                     // TODO: record the output somewhere
                                     VMwareSlave s = t.provision(new StreamTaskListener(System.out));
                                     Hudson.getInstance().addNode(s);
                                     // VMware instances may have a long init script. If we declare
                                     // the provisioning complete by returning without the connect
                                     // operation, NodeProvisioner may decide that it still wants
                                     // one more instance, because it sees that (1) all the slaves
                                     // are offline (because it's still being launched) and
                                     // (2) there's no capacity provisioned yet.
                                     //
                                     // deferring the completion of provisioning until the launch
                                     // goes successful prevents this problem.
                                     s.toComputer().connect(false).get();
                                     return s;
                                 }
                             })
                             ,t.getNumExecutors()));
                 }
            }
            
            
            
            return r;
        } catch (Exception  e) {
            LOGGER.log(Level.WARNING,"Failed to count the # of live instances on VMware",e);
            return Collections.emptyList();
        }
    }*/

	
    /**
     * Provision a new slave based on VM policy
     *  
     * @param label
     * @param excessWorkload
     * @param projectSpecificParameter
     * @return
     */
    public Collection<PlannedNode> provision(final Label label, int excessWorkload) {
        try {
	        	String projectUserID = "";	        	
	        	String projectID = "";       		
        		
	        	Authentication auth = Jenkins.getInstance().getAuthentication();
	        	LOGGER.info("auth information "+auth.getDetails());
	        	
	        	/*if(Stapler.getCurrent()!= null){
	    	    	HttpSession sess = Stapler.getCurrent().getCurrentRequest().getSession(false);       	
	    	       	if(sess!= null && sess.getAttribute(ClientConstants.USER_ID)!= null && sess.getAttribute(ClientConstants.PROJECT_ID)!= null){
	    	    		try{    			
	    	    			projectUserID = Integer.parseInt((String)sess.getAttribute(ClientConstants.USER_ID));
	    	    			projectID =(String)sess.getAttribute(ClientConstants.PROJECT_ID);
	    	    		}catch(Exception e){
	    	    			LOGGER.severe("EXCEPTION OCCURED WHILE FETCHING SESSION DATA "+e.getMessage());
	    	    		}
	    	       	}
	        	}*/
	        	 		
        	
        	// Get the build that is pending in the queue for the given label
             Object obj = getTemplate(label);
             List<PlannedNode> r = new ArrayList<PlannedNode>();
            if(obj instanceof VMWareSlaveTemplate){
            	 final VMWareSlaveTemplate t = (VMWareSlaveTemplate)obj;
            	 getVMWareVMPolicy(label, excessWorkload, r, t,projectUserID,projectID);
            	 
             }
          
            return r;
        } catch (Exception  e) {
            LOGGER.log(Level.WARNING,"Failed to count the # of live instances on VMware",e);
            return Collections.emptyList();
        }
    }
	
	/**
	 * Based on VM policy get a slave against the provided label in Vmware 
	 * 
	 * Policy as below ::
	 * 	1. Third Party - 
	 * 			It is a pre-existing vm. Therefore find it and register.
	 * 	2. Pooled VM ::
	 * 			If a slave present against the label don't spawn else spawn a vm and register against the label
	 * 	3. Just In Time ::
	 * 			Spawn a new VM , build the job and terminate it 
	 * 
	 * @param label
	 * @param excessWorkload
	 * @param r
	 * @param t
	 * @throws CloudException
	 */
	private void getVMWareVMPolicy(final Label label, int excessWorkload, List<PlannedNode> r,
			final VMWareSlaveTemplate t,final String projectUserID,String projectID) throws Exception
	{
				 
		 // Get project specific information
		 final String accountNumber = getAccountId();
		 final String userId = getVmOwnerId();
		 final String imageId = t.ami;
		 projectID = t.projectId;
		 final String kernelId = null;
		 final String imageType = t.cpuType.getTypeId()+":"+t.ramType.getTypeId();
		 //final String imageType = t.type.getTypeId();
		 final int minInstance = 1;
		 final int maxInstance = 1;
		 
		 final String cloudType = getCloudType(t.parent.name);
		 
		 for( ; excessWorkload>0; excessWorkload-- ) {		     

		     r.add(new PlannedNode(t.getDisplayName(),
		             Computer.threadPoolForRemoting.submit(new Callable<Node>() {
		                 public Node call() throws Exception {
		                     // TODO: record the output somewhere
		                 	// If the label starts with EXT# assume this is an pre - existing instance and therefore try finding 
		                 	// it ... instead of spawning a new one (Type- Third Party)
		                 	//
		                 	// If the label starts with POOLED assume that it exists if not spawn a new one and keep it in pool
		                 	//
		                 	// If the label starts with JIT assume a new VM will be spawned and will be killed after the operation
		                 	
		                
		                 	
		                 	 /** 			############## VM Policy ######################
		                 	 *  
		                 	 *  
		                 	 *  	JIT-->Spawned-->Registered-->Deregistered-->Terminated
		                 	 * 		Pooled-->Spawned if not found already being registered-->Registered if newly spawned-->
		                 	Deregistered conditionally*-->Terminated conditionally*
		                 	 *		Instance Id	Found or else exception is thrown-->Registered-->Deregistered-->Not Terminated
		                 	 **/
		                 	 
		                 	
		                 	VMwareSlave s = null;
		                 	LOGGER.info(" Label is "+label.getDisplayName());
		                 	if(StringUtils.startsWithIgnoreCase(label.getDisplayName(),VMwareConstant.THIRDPARTY)){
		                 		
		                 		//String[] labelStrings = label.getDisplayName().split("#");
		                 		s = t.findToRegisterExistingInstance(new StreamTaskListener(System.out),
		                 				label.getDisplayName(),
										accountNumber,
										userId,
										imageId,
										kernelId,
										imageType,
										minInstance,
										maxInstance,										
										securityCredential.getPlainText(),
										accessId,
										cloudType,
										privateKey
										,projectUserID,t.projectId);
		                 		
		                 	}else if(StringUtils.startsWithIgnoreCase(label.getDisplayName(),VMwareConstant.JIT)){
		                      s = t.provision(new StreamTaskListener(System.out),
												accountNumber,
												userId,
												imageId,
												kernelId,
												imageType,
												minInstance,
												maxInstance,												
												securityCredential.getPlainText(),
												accessId,
												cloudType,
												privateKey, false,
												label.getDisplayName(),projectUserID,t.projectId);
		                 	}else /*if(StringUtils.startsWithIgnoreCase(label.getDisplayName(),"POOLED"))*/{
		                 		//String[] labelStrings = label.getDisplayName().split("#");
		                 		
		                 		s = t.findOrSpawnInstance(new StreamTaskListener(System.out),
		                 				label.getDisplayName(),
		                 				accountNumber,
		                 				userId,
		                 				imageId,
		                 				kernelId,
		                 				imageType,
		                 				minInstance,
		                 				maxInstance,
		                 				securityCredential.getPlainText(),
		                 				accessId,
		                 				cloudType,
		                 				privateKey,projectUserID,t.projectId);
		                 		
		                 	}
		                     Hudson.getInstance().addNode(s);
		                     // VMware instances may have a long init script. If we declare
		                     // the provisioning complete by returning without the connect
		                     // operation, NodeProvisioner may decide that it still wants
		                     // one more instance, because it sees that (1) all the slaves
		                     // are offline (because it's still being launched) and
		                     // (2) there's no capacity provisioned yet.
		                     //
		                     // deferring the completion of provisioning until the launch
		                     // goes successful prevents this problem.
		                     s.toComputer().connect(false).get();
		                     return s;
		                 }
		             })
		             ,t.getNumExecutors()));
		 }
	}
    
    /** 
     * Checks whether able to spawn a new slave against the label 
     */
    public boolean canProvision(Label label) {
        return getTemplate(label)!=null;
    }

    
    
    /** 
     * Checks whether able to spawn a new slave against the label,
     * Creating an overloaded version of this method to send the 
     * cloud details, We need to get the template specific to the 
     * cloud 
     */
    public boolean canProvision(Label label,Cloud c) {
        return getTemplate(label,c)!=null;
    }

    
    /**
     * Gets the first {@link PrivateCloud} instance configured in the current Hudson, or null if no such thing exists.
     */
    public static PrivateCloud get() {
        return Hudson.getInstance().clouds.get(PrivateCloud.class);
    }

       
    /***
     * Convert a user entered string into a port number
     * "" -> -1 to indicate default based on SSL setting
     */
    public static Integer convertPort(String vmwarePort) {
        if (vmwarePort == null || vmwarePort.length() == 0)
            return -1;
        else
            return Integer.parseInt(vmwarePort);
    }
  
    /* Parse a url or return a sensible error */
    public static URL checkEndPoint(String url) throws FormValidation {
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            throw FormValidation.error("Endpoint URL is not a valid URL");
        }
    }


    public static abstract class DescriptorImpl extends Descriptor<Cloud> {
        public JPaaSInstanceType[] getInstanceTypes() {
            return JPaaSInstanceType.values();
        }

        /**
         * TODO: once 1.304 is released, revert to FormValidation.validateBase64
         */
        private FormValidation validateBase64(String value, boolean allowWhitespace, boolean allowEmpty, String errorMessage) {
            try {
                String v = value;
                if(!allowWhitespace) {
                    if(v.indexOf(' ')>=0 || v.indexOf('\n')>=0)
                        return FormValidation.error(errorMessage);
                }
                v=v.trim();
                if(!allowEmpty && v.length()==0)
                    return FormValidation.error(errorMessage);

                com.trilead.ssh2.crypto.Base64.decode(v.toCharArray());
                return FormValidation.ok();
            } catch (IOException e) {
                return FormValidation.error(errorMessage);
            }
        }

        public FormValidation doCheckAccessId(@QueryParameter String value) throws IOException, ServletException {
            return validateBase64(value,false,false,Messages.VMwareCloud_InvalidAccessId());
        }

        public FormValidation doCheckSecurityCredential(@QueryParameter String value) throws IOException, ServletException {
            return validateBase64(value,false,false,Messages.VMwareCloud_InvalidSecretKey());
        }

        public FormValidation doCheckPrivateKey(@QueryParameter String value) throws IOException, ServletException {
            boolean hasStart=false,hasEnd=false;
            BufferedReader br = new BufferedReader(new StringReader(value));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("-----BEGIN RSA PRIVATE KEY-----"))
                    hasStart=true;
                if (line.equals("-----END RSA PRIVATE KEY-----"))
                    hasEnd=true;
            }
            if(!hasStart)
                return FormValidation.error("This doesn't look like a private key at all");
            if(!hasEnd)
                return FormValidation.error("The private key is missing the trailing 'END RSA PRIVATE KEY' marker. Copy&paste error?");
            return FormValidation.ok();
        }
        /**
         * Test the connection using user credential
         * 
         * @param vmwareEndpoint
         * @param accessId
         * @param securityCredential
         * @param privateKey
         * @param accountId
         * @param securityGroup
         * @param securityKeyFileName
         * @param vmOwnerId
         * @return
         * @throws IOException
         * @throws ServletException
         */
        protected FormValidation doTestConnection( URL vmwarEndpoint,
                                     String accessId, String securityCredential, 
                                     String privateKey, String accountId,String vmOwnerId,String projectUserID,String projectID) throws IOException, ServletException {
            try {
               	LOGGER.info(" Executing doTestConnection ");
               	HttpSession sess = Stapler.getCurrentRequest().getSession(false);
               	
               	if(sess!= null && sess.getAttribute(ClientConstants.USER_ID)!= null && sess.getAttribute(ClientConstants.PROJECT_ID)!= null){
            		try{    			
            			projectUserID = (String)sess.getAttribute(ClientConstants.USER_ID);
            			projectID =(String)sess.getAttribute(ClientConstants.PROJECT_ID);
            		}catch(Exception e){
            			LOGGER.severe("EXCEPTION OCCURED WHILE FETCHING SESSION DATA "+e.getMessage());
            		}
            	}
               	
            	String providerCode = null;           	
            	providerCode = VMwareConstant.VMWareCloud;
            	
            	String decryptedSecretKey = "";
            	try{
            		decryptedSecretKey = Secret.decrypt(securityCredential).getPlainText();
            		
            	}catch(NullPointerException npe){
            		decryptedSecretKey = securityCredential;
            	}
        		
            	int cntRunningInst =PlatformInfraUtil.getVirtualMachineListCount(accountId,
            			vmOwnerId, decryptedSecretKey, accessId, providerCode,projectUserID,projectID);
            	//int cntRunningInst = insac.runningInstancesCount(accountId, vmOwnerId, decryptedSecretKey, accessId, providerCode);
        		LOGGER.info(" Dtl ==> "+accountId+"  v "+vmOwnerId+" s "+securityCredential+" a "+accessId);
        		LOGGER.info( " Number of running instances "+cntRunningInst);
        		if(cntRunningInst >= 0){
        			return FormValidation.ok(Messages.VMwareCloud_Success());
        		}else{
        			return FormValidation.error("Not able to connect to Amazon Account.Please check the entries");
        		}
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to check VMware credential",e);
                return FormValidation.error(e.getMessage());
            }
        }

        public FormValidation doGenerateKey(StaplerResponse rsp, URL vmwareEndpointUrl, String accessId, String securityCredential
        ) throws IOException, ServletException {
            try {
               
                return FormValidation.ok(Messages.VMwareCloud_Success());
            } catch ( Exception e) {
                LOGGER.log(Level.WARNING, "Failed to check VMware credential",e);
                return FormValidation.error(e.getMessage());
            }
        }
    }

    private static final Logger LOGGER = Logger.getLogger(PrivateCloud.class.getName());

    private static boolean isSSL(URL endpoint) {
        return endpoint.getProtocol().equals("https");
    }

    private static int portFromURL(URL endpoint) {
        int vmwarePort = endpoint.getPort();
        if (vmwarePort == -1) {
        	vmwarePort = endpoint.getDefaultPort();
        }
        return vmwarePort;
    }
    
}
