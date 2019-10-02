package hudson.plugins.ec2;

import hudson.Extension;
import hudson.model.Hudson;
import hudson.slaves.Cloud;
import hudson.util.FormValidation;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * The original implementation of {@link EC2Cloud}.
 * 
 * @author Kohsuke Kawaguchi
 */
public class AmazonEC2Cloud extends EC2Cloud {
	/**
	 * Represents the region. Can be null for backward compatibility reasons.
	 */
	private AwsRegion region;
	private final String securityGroup;
	private final String securityKeyFileName;
	private final String vmOwnerId;
	private final String accountId;

	/*
	 * Security key, group and vm_admin user id that is persisted while defining
	 * the cloud
	 */

	public String getAccountId() {
		return accountId;
	}

	public String getSecurityGroup() {
		return securityGroup;
	}

	public String getSecurityKeyFileName() {
		return securityKeyFileName;
	}

	public String getVmOwnerId() {
		return vmOwnerId;
	}
	

	public void setRegion(AwsRegion region) {
		this.region = region;
	}

	@DataBoundConstructor
	public AmazonEC2Cloud(AwsRegion region, String accessId, String secretKey,
			String privateKey, String instanceCapStr,
			List<SlaveTemplate> templates, String accountId,
			String securityGroup, String securityKeyFileName, String vmOwnerId) {
		// super("ec2-"+region.name(), accessId, secretKey, privateKey,
		// instanceCapStr, templates);
		// Lookup existing AWS name ... append + 1 + 2
		// For same multiple amazon cloud we should a different mechanism
		// altogether
		// The name should not be same for all amazon clouds.
		// If first is going to be Amazon then Amazon1 then Amazon2 and so on
		super(EC2Constant.EC2CLOUDNAME, accessId, secretKey, privateKey,
				instanceCapStr, templates);
		this.region = region;
		// Setting them while saving
		this.securityGroup = securityGroup;
		this.securityKeyFileName = securityKeyFileName;
		this.vmOwnerId = vmOwnerId;
		this.accountId = accountId;
	}

	public AwsRegion getRegion() {
		if (region == null)
			region = AwsRegion.US_EAST_1; // backward data compatibility with
											// earlier versions
		return region;
	}

	@Override
	public URL getEc2EndpointUrl() {
		return getRegion().ec2Endpoint;
	}

	@Override
	public URL getS3EndpointUrl() {
		return getRegion().s3Endpoint;
	}

	@Extension
	public static class DescriptorImpl extends EC2Cloud.DescriptorImpl {
		public String getDisplayName() {
			return "Amazon EC2";
		}

		public FormValidation doTestConnection(
				@QueryParameter AwsRegion region,
				@QueryParameter String accessId,
				@QueryParameter String accountId,
				@QueryParameter String securityGroup,
				@QueryParameter String securityKeyFileName,
				@QueryParameter String vmOwnerId,
				@QueryParameter String secretKey,
				@QueryParameter String privateKey,
				@QueryParameter String projectUserID,
				@QueryParameter String projectID) throws IOException,
				ServletException {

			HttpSession sess = Stapler.getCurrentRequest().getSession(false);
			// Setting up AWS_CLOUD_PROVIDER flag in session as Y and removing
			// EUCA_CLOUD_PROVIDER flag
			sess.setAttribute(EC2Constant.AWS_CLOUD_PROVIDER, "Y");
			
			return super.doTestConnection(region.ec2Endpoint, accessId,
					secretKey, privateKey, accountId, securityGroup,
					securityKeyFileName, vmOwnerId, projectUserID, projectID);
		}

		public FormValidation doGenerateKey(StaplerResponse rsp,
				@QueryParameter AwsRegion region,
				@QueryParameter String accessId,
				@QueryParameter String secretKey) throws IOException,
				ServletException {
			return super.doGenerateKey(rsp, region.ec2Endpoint, accessId,
					secretKey);
		}
	}

}
