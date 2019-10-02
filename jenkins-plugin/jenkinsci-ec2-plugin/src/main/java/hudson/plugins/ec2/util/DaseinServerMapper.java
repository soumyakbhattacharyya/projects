package hudson.plugins.ec2.util;

import org.dasein.cloud.services.server.Platform;
import org.dasein.cloud.services.server.Server;
import org.dasein.cloud.services.server.ServerState;

import com.cognizant.jpaas2.resource.ArchitectureType;
import com.cognizant.jpaas2.resource.VirtualMachineType;

public class DaseinServerMapper {
	
	
	public static Server convertToServerFromVirtualMachineType(VirtualMachineType response){
		
		Server server = new Server();		
		if(response.getArchitecture()!= null){
			server.setArchitecture(getServerArchitecture(response));			
		}
		server.setCreateTime(response.getCreationTimestamp());
		if(response.getCurrentState()!= null){
			server.setCurrentState(ServerState.valueOf(response.getCurrentState().name()));
		}
		server.setDataCenterId(response.getProviderDataCenterId());
		server.setDescription(response.getDescription());
		server.setImageId(response.getProviderMachineImageId());
		server.setName(response.getName());
		server.setPersistent(response.getPersistent());
		if(response.getPlatform()!= null){
			server.setPlatform(Platform.valueOf(response.getPlatform().name()));
		}
		server.setPrivateDnsAddress(response.getPrivateDnsAddress());
		if(response.getPrivateIpAddresses()!= null && response.getPrivateIpAddresses().size()>0){
			server.setPrivateIpAddresses(response.getPrivateIpAddresses().toArray(new String[response.getPrivateIpAddresses().size()]));
		}
		server.setPublicDnsAddress(response.getPublicDnsAddress());
		server.setRegionId(response.getProviderRegionId());
		server.setRootPassword(response.getRootPassword());		
		server.setTerminationTime(response.getTerminationTimestamp());
		server.setStartTime(response.getLastBootTimestamp());
		server.setStopTime(response.getLastPauseTimestamp());
		
		return server;
	}

	public static org.dasein.cloud.services.server.Architecture getServerArchitecture(VirtualMachineType response){
		org.dasein.cloud.services.server.Architecture architecure = org.dasein.cloud.services.server.Architecture.I32;
		if(response.getArchitecture().equals(ArchitectureType.X_86_64)){
			architecure = org.dasein.cloud.services.server.Architecture.I64;
		}
		return architecure;
	}
	
}
