package hudson.plugins.cloud;

import hudson.Extension;
import hudson.model.Hudson;
import hudson.plugins.cloud.constant.ClientConstants;
import hudson.util.FormValidation;
import hudson.util.StreamTaskListener;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public class VMWareCloud extends PrivateCloud {
   
    
    
    /*
     * Security key, group and vm_admin user id that is persisted while defining the cloud
     * 
     */
    private final String accountId;    
    private final String vmOwnerId;    
    private final List<VMWareSlaveTemplate> vmwareTemplates;
    private static final Logger LOGGER = Logger.getLogger(VMWareCloud.class.getName());
    
       
    @Override
    public String getAccountId() {
		return accountId;
	}
	
    @Override
	public String getVmOwnerId() {
		return vmOwnerId;
	}

	
	@DataBoundConstructor
	public VMWareCloud(String accountId, 
					   String vmOwnerId, String accessId, String securityCredential ,String privateKey, List<VMWareSlaveTemplate> vmwareTemplates) throws IOException {
		
			
		super( VMwareConstant.VMWARECLOUDNAME, accessId, securityCredential, privateKey);
	
        // Setting them while saving        
        this.vmOwnerId=vmOwnerId;
        this.accountId = accountId;       
        System.out.println(vmwareTemplates);
        if(vmwareTemplates==null)     vmwareTemplates=Collections.emptyList();
        this.vmwareTemplates = vmwareTemplates;       
        readResolve(); // set parents

    }

	protected Object readResolve() {
        for (VMWareSlaveTemplate t : vmwareTemplates)
            t.parent = this;
        return this;
    }
	
	
	public List<VMWareSlaveTemplate> getVmwareTemplates() {

		return Collections.unmodifiableList(vmwareTemplates);
	}
	
   
    @Extension
    public static class DescriptorImpl extends PrivateCloud.DescriptorImpl {
        @Override
		public String getDisplayName() {
            return "VMWare Cloud";
        }
        
        public FormValidation doSecurityGroup(@QueryParameter String value) throws IOException, ServletException {
        	if(null == value || "".equals(value))
            return FormValidation.error("Enter security group");
        	else
        	return FormValidation.ok();	
        }

        @Override
		public FormValidation doTestConnection(
                @QueryParameter URL url,
                @QueryParameter String accessId,
                @QueryParameter String securityCredential,
                @QueryParameter String privateKey,
                @QueryParameter String accountId,                
                @QueryParameter String vmOwnerId,
                @QueryParameter String projectUserID,
                @QueryParameter String projectID) throws IOException, ServletException {
        	
        	HttpSession sess = Stapler.getCurrentRequest().getSession(false);
           	
           	if(sess!= null && sess.getAttribute(ClientConstants.USER_ID)!= null && sess.getAttribute(ClientConstants.PROJECT_ID)!= null){
        		try{    			
        			projectUserID = (String)sess.getAttribute(ClientConstants.USER_ID);
        			projectID =(String)sess.getAttribute(ClientConstants.PROJECT_ID);
        		}catch(Exception e){
        			LOGGER.severe("EXCEPTION OCCURED WHILE FETCHING SESSION DATA "+e.getMessage());
        		}
        	}
        	sess.setAttribute(VMwareConstant.VMWARE_CLOUD_PROVIDER, "Y");       	
            return super.doTestConnection(new URL("https://vmware.jpaas.com"),accessId,securityCredential,
            		privateKey,accountId,vmOwnerId,
            		projectUserID,projectID);
        }

        
    }
	 /**
     * Debug command to attach to a running instance.
     */
    public void doAttach(StaplerRequest req, StaplerResponse rsp, @QueryParameter String id) throws ServletException, IOException {
        checkPermission(PROVISION);
        VMWareSlaveTemplate t = getVmwareTemplates().get(0);

        StringWriter sw = new StringWriter();
        StreamTaskListener listener = new StreamTaskListener(sw);
        VMwareSlave node = t.attach(id,listener);
        Hudson.getInstance().addNode(node);

        rsp.sendRedirect2(req.getContextPath()+"/computer/"+node.getNodeName());
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());		
		result = prime * result
				+ ((vmOwnerId == null) ? 0 : vmOwnerId.hashCode());
		result = prime * result
				+ ((vmwareTemplates == null) ? 0 : vmwareTemplates.hashCode());
		return result;
	}

	
	public boolean equals(PrivateCloud other) {
		
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		if (accountId == null) {
			if (other.getAccountId() != null)
				return false;
		} else if (!accountId.equals(other.getAccountId()))
			return false;
		if (this.getAccessId() == null) {
			if (other.getAccessId() != null)
				return false;
		} else if (!this.getAccessId().equals(other.getAccessId()))
			return false;
		if (this.getVmOwnerId() == null) {
			if (other.getVmOwnerId() != null)
				return false;
		} else if (!this.getVmOwnerId().equals(other.getVmOwnerId()))
			return false;		
		if (vmOwnerId == null) {
			if (other.getVmOwnerId() != null)
				return false;
		} else if (!vmOwnerId.equals(other.getVmOwnerId()))
			return false;
		
		return true;
	}
	
	
}
