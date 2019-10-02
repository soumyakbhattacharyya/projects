package hudson.plugins.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dasein.cloud.services.server.Server;

import com.cognizant.cloudset.model.Instance;

public class Cloud360ServerMapper {

	public static Server convertToServerFromComputeInstance(Instance vm) {
		
		Server server = new Server();
		
		server.setPublicIpAddresses(new String[]{vm.getIpAddress()});
		server.setName(vm.getName());
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
			if(vm.getLaunchTime().equals("N/A")) {
				server.setStartTime(0);
			} else {
				date = sdf.parse(vm.getLaunchTime());
				server.setStartTime(date.getTime());				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return server;
	}
	
}
