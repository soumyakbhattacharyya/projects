package hudson.plugins.ec2;

public class EC2Constant {
	
	//public static final String TERMINATE_WITH_DEREGISTER = "TerminateWithDeregister" ;
	
	// Constants to represent cloud providers
	public static final String AWS_CLOUD_PROVIDER = "AwsCloudProvider";
	public static final String EUCA_CLOUD_PROVIDER = "EucaCloudProvider";
	public static final String VMWARE_CLOUD_PROVIDER = "VmwareCloudProvider";
	
	// Third party constant required to put in system configuration page for third party vms
	public static final String THIRDPARTY = "EXT#";
	
	// Just in time vm policy constant required to put in system configuration page for JIT vms
	public static final String JIT = "JIT#";
	
	// Represents the retry count for Unix Launcher
	public static final int RETRY_COUNT = 40;
	
	// Represents the vmware cloud usable in Dasein-cloud-Plugin
	public static final String VMWareCloud = "03";
	
	// Represents the amazon cloud usable in Dasein-cloud-Plugin
	public static final String EC2Cloud = "02";
	
	// Represents the eucalyptus cloud usable in Dasein-cloud-Plugin
	public static final String EUCACloud = "01";
	
	// Represents VMware templates method used when we traverse 
	// whole cloud using reflection
	public static final String VMWARETEMPLATES = "getVmwareTemplates";
	
	// Represent cloud name for vmware
	public static final String VMWARECLOUDNAME = "vmware";
	
	// Represents cloud name for eucalyptus
	public static final String EUCACLOUDNAME = "eucalyptus";
	
	// Represents cloud name for amazon
	public static final String EC2CLOUDNAME = "amazon";
	
}
