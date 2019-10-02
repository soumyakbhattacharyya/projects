package com.cognizant.plugins;
import hudson.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.cognizant.monitor.MoniterService;
import com.cognizant.monitor.MonitorServiceImpl;
import com.cognizant.monitor.NodeManager;
import com.cognizant.monitor.PropertiesManager;
import com.cognizant.monitor.State;
import com.cognizant.nodes.ClusterNode;

public class JenkinsHA extends Plugin {
	
	private Logger LOGGER = Logger.getLogger("JenkinsHA");

	private MoniterService mService = null;
	
	private static final String YES = "YES";
	private static final String NO = "NO";
	private static final String REVERT_MODE = "Revert To Primary";
	private static final String NORMAL_MODE = "Normal";
	
	@Override
	public void start() throws Exception {
		LOGGER.info("STARTING Jenkins HA Monitoring");
		try{
			mService = MonitorServiceImpl.getInstance();
			mService.startService();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		LOGGER.info("STOPPING Jenkins HA Monitoring");
		try{
			if(mService != null) {
				mService.stopService();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public List getNodeList() {
		List<ClusterNode> nodelist = new ArrayList<ClusterNode>();
		if(mService != null) {
			NodeManager mgr = mService.getNodeManager();
			if(mgr != null) {
				Map<String, ClusterNode> nodes = mgr.getNodeDB().getNodeStates();
				Set<String> keys = nodes.keySet();
				for(String key:keys) {
					ClusterNode node = nodes.get(key);
					nodelist.add(node);
				}
			}
		}
		return nodelist;
	}
	
	public String checkPrimaryNode(ClusterNode node) {
		String status = "";
		PropertiesManager prop = mService.getPropertiesManager();
		if(prop != null) {
			if(prop.isPrimaryNodeRevertMode()) {
				if(node.getName().equalsIgnoreCase(prop.getPrimaryNodeName())) {
					status = YES;
				} else {
					status = NO;
				}
			} else {
				if(node.getState().equals(State.ACTIVE)) {
					status = YES;
				} else {
					status = NO;
				}
			}
		}
		return status;
	}
	
	public String getOperatingMode() {
		String mode = "";
		PropertiesManager prop = mService.getPropertiesManager();
		if(prop != null) {
			if(prop.isPrimaryNodeRevertMode()) {
				mode = REVERT_MODE;
			} else {
				mode = NORMAL_MODE;
			}
		}
		return mode;
	}
}

