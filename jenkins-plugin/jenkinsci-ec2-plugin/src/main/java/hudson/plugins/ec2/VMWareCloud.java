package hudson.plugins.ec2;

import hudson.Extension;
import hudson.model.Hudson;
import hudson.slaves.Cloud;
import hudson.util.FormValidation;
import hudson.util.StreamTaskListener;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.jets3t.service.Jets3tProperties;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public class VMWareCloud extends EC2Cloud {
   
    
    
    /*
     * Security key, group and vm_admin user id that is persisted while defining the cloud
     * */
    private final String accountId;    
    private final String vmOwnerId;
    private final String securityGroup;
    private final String securityKeyFileName;
    private final List<VMWareSlaveTemplate> vmwareTemplates;
    
       
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
					   String vmOwnerId, String accessId, String secretKey ,String privateKey, String instanceCapStr, List<VMWareSlaveTemplate> vmwareTemplates,String securityGroup,
					    String securityKeyFileName
    
    ) throws IOException {
		
			
		super( EC2Constant.VMWARECLOUDNAME, accessId, secretKey, privateKey, instanceCapStr);
	
        // Setting them while saving        
        this.vmOwnerId=vmOwnerId;
        this.accountId = accountId;
        this.securityGroup=securityGroup;
        this.securityKeyFileName=securityKeyFileName;
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
	
    @Override
    protected Jets3tProperties buildJets3tProperties(URL s3) {
        Jets3tProperties props = super.buildJets3tProperties(s3);
        
        /* For eucalyptus as of 1.6.0 */
        props.setProperty("s3service.disable-dns-buckets", "true");

        return props;
    }

    @Extension
    public static class DescriptorImpl extends EC2Cloud.DescriptorImpl {
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
                @QueryParameter String secretKey,
                @QueryParameter String privateKey,
                @QueryParameter String accountId, 
                @QueryParameter String securityGroup, 
                @QueryParameter String securityKeyFileName,
                @QueryParameter String vmOwnerId,
                @QueryParameter String projectUserID,
                @QueryParameter String projectID) throws IOException, ServletException {
        	HttpSession sess = Stapler.getCurrentRequest().getSession(false);
        	//Map<String,String> map = new HashMap<String,String>();        	
        	//map.put("accountId", accountId);
        	//map.put("vmOwnerId", vmOwnerId);
        	//map.put("region", region.ec2Endpoint.toString());
        	sess.setAttribute(EC2Constant.VMWARE_CLOUD_PROVIDER, "Y");        	
        	sess.removeAttribute(EC2Constant.AWS_CLOUD_PROVIDER);
        	sess.removeAttribute(EC2Constant.EUCA_CLOUD_PROVIDER);
            return super.doTestConnection(new URL("https://vmware.jpaas.com"),accessId,secretKey,
            		privateKey,accountId,securityGroup,securityKeyFileName,vmOwnerId,
            		projectUserID,projectID);
        }

        
    }

    
	@Override
	public String getSecurityGroup() {
		return securityGroup;
	}

	@Override
	public String getSecurityKeyFileName() {
		return securityKeyFileName;
	}

	@Override
	public URL getEc2EndpointUrl() throws IOException {
		return null;
	}

	@Override
	public URL getS3EndpointUrl() throws IOException {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result
				+ ((securityGroup == null) ? 0 : securityGroup.hashCode());
		result = prime
				* result
				+ ((securityKeyFileName == null) ? 0 : securityKeyFileName
						.hashCode());
		result = prime * result
				+ ((vmOwnerId == null) ? 0 : vmOwnerId.hashCode());
		result = prime * result
				+ ((vmwareTemplates == null) ? 0 : vmwareTemplates.hashCode());
		return result;
	}

	
	public boolean equals(EC2Cloud other) {
		
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
		if (securityKeyFileName == null) {
			if (other.getSecurityKeyFileName() != null)
				return false;
		} else if (!securityKeyFileName.equals(other.getSecurityKeyFileName()))
			return false;
		if (vmOwnerId == null) {
			if (other.getVmOwnerId() != null)
				return false;
		} else if (!vmOwnerId.equals(other.getVmOwnerId()))
			return false;
		
		return true;
	}
	
	
}
