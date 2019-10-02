package hudson.plugins.ec2.resource;

import com.pholser.util.properties.BoundProperty;

public interface PluginProperties {


	@BoundProperty("ec2.aws_url")
	String awsUrl();
		
	@BoundProperty("ec2.sslkeypath")
	String ec2Sslkeypath();
	
	@BoundProperty("ec2.sslpassword")
	String ec2Sslpassword();
	
	@BoundProperty("ec2.addressing")
	String ec2Addressing();
	
	@BoundProperty("ec2.machineimage")
	String ec2Machineimage();
	
	@BoundProperty("ec2.virtualmachine")
	String ec2Virtualmachine();
	
	@BoundProperty("ec2.virtualmachine.ip.discovery.time")
	String ec2VirtualmachineIpDiscoveryTime();
	
	@BoundProperty("ec2.pooled.time")
	String ec2PooledTime();
}
