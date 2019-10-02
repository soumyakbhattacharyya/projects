package hudson.plugins;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.dasein.cloud.CloudException;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import com.cognizant.cloudset.client.RestClient;
import com.cognizant.cloudset.constants.Cloud360Constants;
import com.cognizant.cloudset.message.Cloud360Response;
import com.cognizant.cloudset.model.Cloud;

import hudson.Extension;
import hudson.cognizant.jpaas.pojo.JPaaSUser;
import hudson.model.Computer;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.Label;
import hudson.model.Node;
import hudson.plugins.constant.Cloud360PluginConstants;
import hudson.plugins.constant.Messages;
import hudson.slaves.NodeProvisioner.PlannedNode;
import hudson.util.FormValidation;
import hudson.util.StreamTaskListener;

public class Cloud360 extends hudson.slaves.Cloud{
	
	private final String cloud360RestUrl;
	private final String cloud360UserId;
	private final String cloud360Password;
	
	private final List<Cloud360MachineTemplates> cloud360templates;
	
	private static final Logger LOGGER = Logger.getLogger(Cloud360.class.getName());
	
	@DataBoundConstructor
	public Cloud360(String cloud360RestUrl, String cloud360UserId, String cloud360Password, List<Cloud360MachineTemplates> cloud360templates) {
		super("Cloud360");
		this.cloud360RestUrl = cloud360RestUrl;
		this.cloud360UserId = cloud360UserId;
		this.cloud360Password = cloud360Password;
		if (cloud360templates == null)
			cloud360templates = Collections.emptyList();
		this.cloud360templates = cloud360templates;
		
		setParents();
	}

	private void setParents() {
		for(Cloud360MachineTemplates template:cloud360templates) {
			template.setParent(this);
		}
	}
	
	
	/**
     * Debug command to attach to a running instance.
     */
    public void doAttach(StaplerRequest req, StaplerResponse rsp, @QueryParameter String id) throws ServletException, IOException {
        checkPermission(PROVISION);
        Cloud360MachineTemplates t = getTemplates().get(0);

        StringWriter sw = new StringWriter();
        StreamTaskListener listener = new StreamTaskListener(sw);
        Cloud360Slave node = t.attach(id,listener);
        Hudson.getInstance().addNode(node);

        rsp.sendRedirect2(req.getContextPath()+"/computer/"+node.getNodeName());
    }
    
    /**
     * Filter and return templates which are not JIT or existing instance type for Amazon and Eucalyptus only
     * @return List of templates that can be used by Jenkins admin to spawn a VM manually from UI
     */
	public List<Cloud360MachineTemplates> getTemplates() {
		return Collections.unmodifiableList(cloud360templates);
	}
	
	/**
     * Provision a new VM for an ami
     * 
     * @param req
     * @param rsp
     * @param computeProfileId
     * @throws ServletException
     * @throws IOException
     */
    public void doProvision(StaplerRequest req, StaplerResponse rsp, @QueryParameter String computeProfileId) throws ServletException, IOException {

    	LOGGER.info("Do provision method started with Compute Profile id :: "+computeProfileId);
    	int projectUserID=0;
    	String projectID = "";
    	
    	if(req != null && req.getSession()!= null && req.getSession().getAttribute(Cloud360PluginConstants.JPAAS_USER)!= null){
    		try{
    			JPaaSUser juser = (JPaaSUser) req.getSession().getAttribute(Cloud360PluginConstants.JPAAS_USER);
    			LOGGER.info("the juser data is "+juser.getUserId());
    			projectUserID = Integer.parseInt(juser.getUserId());
    			projectID = juser.getProjectId();
    		}catch(Exception e){
    			LOGGER.severe("EXCEPTION OCCURED WHILE FETCHING SESSION DATA "+e.getMessage());
    		}
    	}
    	
    	checkPermission(PROVISION);
        if(computeProfileId==null) {
        	LOGGER.info("computeProfileId is null");
            sendError("The 'computeProfileId' query parameter is missing",req,rsp);
            return;
        }
        
        
        Object t = getCloud360MachineTemplate(computeProfileId);

        if(t==null) {
        	LOGGER.info("Slave template is null");
            sendError("No such Compute Profile: "+computeProfileId, req, rsp);
            return;
        }
        StringWriter sw = new StringWriter();
        StreamTaskListener listener = new StreamTaskListener(sw);
        try {
        	//String label = t.labels;
            Cloud360Slave node = getSlaveFromUI(listener, t, projectUserID, projectID);
            Hudson.getInstance().addNode(node);
            LOGGER.info("Node created successfully... Do provision method end");
            rsp.sendRedirect2(req.getContextPath()+"/computer/"+node.getNodeName());
        } catch (IOException e) {
            e.printStackTrace(listener.error(e.getMessage()));
            sendError(sw.toString(),req,rsp);
        }
        catch (Exception e) {
            sendError(e.getMessage(),req,rsp);
            return;
        }

    }
	
    private Cloud360Slave getSlaveFromUI(StreamTaskListener listener, Object obj, int projectUserID, String projectID) throws IOException, SecurityException, NoSuchMethodException {

    	LOGGER.info("Going to launch a new VM as triggered by UI");
    	Cloud360Slave slave = null;
    	
    	if(obj instanceof Cloud360MachineTemplates) {
    		Cloud360MachineTemplates template = (Cloud360MachineTemplates)obj;
    		int minInstance = 1;
    		int maxInstance = 1;
    		
    		slave = template.provisionFromUI(listener, template.getComputeProfileId(), template.getRemoteUserId(),
    				template.getSecretPrivateKey(), template.getRemoteFSRoot(), template.getRemoteSSHPort(),
    				minInstance, maxInstance, projectUserID, projectID);
    	}
    	
    	 return slave;
	}
	
	@Override
	public Collection<PlannedNode> provision(Label label, int excessWorkload) {
		try {
			Object obj = getCloud360MachineTemplate(label);
			List<PlannedNode> nodes = new ArrayList<PlannedNode>();
			if(obj instanceof Cloud360MachineTemplates) {
				final Cloud360MachineTemplates template = (Cloud360MachineTemplates) obj;
				for (; excessWorkload > 0; excessWorkload--) {
					
					nodes.add(new PlannedNode(template.getLabel(),
							Computer.threadPoolForRemoting
									.submit(new Callable<Node>() {
										public Node call() throws Exception {
											
											Cloud360Slave s = template.provision(new StreamTaskListener(System.out));
											Hudson.getInstance().addNode(s);
											// EC2 instances may have a long init script. If we declare
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
									}
								), template.getNumOfExecutors()
							)
					);
				}
			}
			
			return nodes;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	/**
     * Provision a new slave based on VM policy
     *  
     * @param label
     * @param excessWorkload
     * @param projectSpecificParameter
     * @return
     */
    public Collection<PlannedNode> provisionForProject(final Label label, int excessWorkload, 
    		String...projectSpecificParameter) {
        try {
        	int projectUserID = 0;
        	String projectID = "";

        	if(null != projectSpecificParameter && projectSpecificParameter.length > 0){
        		
        		int count =0;
        		for(String s:projectSpecificParameter){
        			LOGGER.info(count+ " --- s is  "+s);
        			count++;
        		}
        		
        		LOGGER.info("projectSpecificParameter account id" + projectSpecificParameter[0]);
        		try{
        			projectUserID = Integer.parseInt(projectSpecificParameter[5]);
        			projectID = projectSpecificParameter[4];
        			LOGGER.info("projectUserid and projectId is "+projectUserID + " && "+projectID);
        		}catch(Exception e){
        			LOGGER.severe("Error occured as "+e);
        		}        		
        	}
        	       	
        	
        	// Get the build that is pending in the queue for the given label
             Object obj = getCloud360MachineTemplate(label);
             List<PlannedNode> r = new ArrayList<PlannedNode>();
             
             if(obj instanceof Cloud360MachineTemplates){
            	 final Cloud360MachineTemplates t = (Cloud360MachineTemplates)obj;
                 getVMPolicy(label, excessWorkload, r, t, projectUserID, projectID);
            	 
             }
          
            return r;
        } catch (Exception  e) {
            LOGGER.log(Level.WARNING,"Failed to count the # of live instances on cloud360",e);
            return Collections.emptyList();
        }
    }
    
    private void getVMPolicy(final Label label, int excessWorkload, List<PlannedNode> r,
			final Cloud360MachineTemplates t, final int projectUserID, final String projectID)
    throws CloudException {
    	final String computeProfileId = t.getComputeProfileId();
    	final String remoteUserId = t.getRemoteUserId();
    	final String secretPrivateKey = t.getSecretPrivateKey();
    	final String remoteFSRoot = t.getRemoteFSRoot();
    	final String remoteSSHPort = t.getRemoteSSHPort();
    	final int minInstance = 1;
    	final int maxInstance = 1;
    	
    	for (; excessWorkload > 0; excessWorkload--) {
    		r.add(new PlannedNode(t.getLabel(),
		             Computer.threadPoolForRemoting.submit(new Callable<Node>() {
		                 public Node call() throws Exception {
		                	 Cloud360Slave slave = null;
		                	 LOGGER.info(" Label is "+t.getLabel());
		                	 if(StringUtils.startsWithIgnoreCase(label.getDisplayName(),Cloud360PluginConstants.JIT)) {
		                		 t.provision(new StreamTaskListener(System.out), computeProfileId, 
		                				 remoteUserId, secretPrivateKey, remoteFSRoot, remoteSSHPort, 
		                				 minInstance, maxInstance, t.getLabel(), projectUserID, projectID, false);
		                	 } else {
		                		 t.findOrSpawnInstance(new StreamTaskListener(System.out), computeProfileId,
		                				 remoteUserId, secretPrivateKey, remoteFSRoot,
		                				 remoteSSHPort, minInstance, maxInstance,
		                				 t.getLabel(), projectUserID, projectID);
		                	 }
		                	 Hudson.getInstance().addNode(slave);
		                     // EC2 instances may have a long init script. If we declare
		                     // the provisioning complete by returning without the connect
		                     // operation, NodeProvisioner may decide that it still wants
		                     // one more instance, because it sees that (1) all the slaves
		                     // are offline (because it's still being launched) and
		                     // (2) there's no capacity provisioned yet.
		                     //
		                     // deferring the completion of provisioning until the launch
		                     // goes successful prevents this problem.
		                     slave.toComputer().connect(false).get();
		                     return slave;
		                 }
		             }),t.getNumOfExecutors()
		         )
    		);
    	}
    	
	}

	@Override
	public boolean canProvision(Label label) {
		return getCloud360MachineTemplate(label) != null;
	}
	
	public boolean canProvision(Label label,hudson.slaves.Cloud c) {
        return getCloud360MachineTemplate(label,c)!=null;
    }
	
	public Object getCloud360MachineTemplate(String computeProfileid) {
		for (Cloud360MachineTemplates t : cloud360templates) {
            if(t.getComputeProfileId().equals(computeProfileid) && (!t.getLabel().contains(Cloud360PluginConstants.JIT))) {
                return t;                
            }
		}
        return null;
	}
	
	public Object getCloud360MachineTemplate(Label label,hudson.slaves.Cloud c) {
		if(c instanceof Cloud360) {
			for (Cloud360MachineTemplates t : cloud360templates) {
				if (label == null || label.getName().matches(t.getLabel())) {
					return t;
				}
			}	
		}
		return null;
	}
	
	public Object getCloud360MachineTemplate(Label label) {
		for (Cloud360MachineTemplates t : cloud360templates) {
			if (label == null || label.getName().matches(t.getLabel())) {
				return t;
			}
		}
		return null;
	}
	
	public List<Cloud360MachineTemplates> getCloud360MachineTemplatesForProvisionFromUI() {
		LOGGER.info("Searching Cloud360 Machine Templates for Provision From UI");
//		List<Cloud360MachineTemplates> cloud360Templates = extractVMWareTemplates();
		List<Cloud360MachineTemplates> pooledCloud360MachineTemplates = new ArrayList<Cloud360MachineTemplates>();

		if (cloud360templates != null) {

			for (Cloud360MachineTemplates cloud360Template : cloud360templates) {
				String labelString = cloud360Template.getLabel();
				if (!(labelString.contains(Cloud360PluginConstants.THIRDPARTY) || labelString
						.contains(Cloud360PluginConstants.JIT))) {
					pooledCloud360MachineTemplates.add(cloud360Template);
				}
			}
		}
		return Collections.unmodifiableList(pooledCloud360MachineTemplates);
	}
	
	public String getCloud360Dsiplay() {
		return "Cloud360";
	}
	
	/**
	 * @return the cloud360RestUrl
	 */
	public String getCloud360RestUrl() {
		return cloud360RestUrl;
	}

	/**
	 * @return the cloud360UserId
	 */
	public String getCloud360UserId() {
		return cloud360UserId;
	}

	/**
	 * @return the cloud360Password
	 */
	public String getCloud360Password() {
		return cloud360Password;
	}
	
	/**
	 * @return the cloud360templates
	 */
	public List<Cloud360MachineTemplates> getCloud360templates() {
		return cloud360templates;
	}
	
	@Extension
	public static class DescriptorImpl extends Descriptor<hudson.slaves.Cloud> {

		@Override
		public String getDisplayName() {
			return "Cloud360";
		}
		
		public FormValidation doTestConnection(
				@QueryParameter String cloud360RestUrl, 
				@QueryParameter String cloud360UserId, 
				@QueryParameter String cloud360Password) {
			
			try {
				RestClient client = new RestClient(cloud360RestUrl, cloud360UserId, cloud360Password);
				Cloud360Response resp = client.getAllClouds();
				
				if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
					List<Cloud> list = (List<Cloud>) resp.getResponseResult();
					if(list != null && !list.isEmpty()) {
						Cloud cl = list.get(0);
						if(cl != null) {
							return FormValidation.ok(Messages.CLOUD360_CONNECTED_SUCCESS);
						}
					}
					return FormValidation.error(Messages.NO_CLOUD_FOUND);
				} else if(resp.getResponseCode() == Cloud360Constants.ERROR){
					return FormValidation.error(Messages.CLOUD360_CONNECTED_FAILURE.
							concat(resp.getErrorObject().getTraceableErrorInfo().getErrorDescription()));
				} else {
					return FormValidation.error(Messages.CLOUD360_CONNECTED_FAILURE);
				}
			} catch(Exception e) {
				return FormValidation.error(Messages.CLOUD360_CONNECTED_FAILURE);
			}
		}
		
	}

}
