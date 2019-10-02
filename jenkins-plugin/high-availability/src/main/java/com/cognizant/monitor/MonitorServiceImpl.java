package com.cognizant.monitor;

import hudson.security.ACL;

import java.util.logging.Level;
import java.util.logging.Logger;

import jenkins.model.Jenkins;

import com.cognizant.nodes.ClusterNode;

public class MonitorServiceImpl implements MoniterService {
	
	private Logger logger = Logger.getLogger("MonitorServiceImpl");
	
	private boolean stopMonitoring = false;
	
	private boolean isNodeActive = false;
	
	private Monitor monitor;
	
	private static MonitorServiceImpl mServiceImpl = new MonitorServiceImpl();
	
	private MonitorServiceImpl() {
		
	}
	
	public static MonitorServiceImpl getInstance() {
		return mServiceImpl;
	}
	
	@Override
	public void startService() {
		monitor = new Monitor();
		monitor.setService(this);
		monitor.start("CLOUDSET-BAAS");
		
		try {
			while(!isNodeActive) {
			}
		} catch (Exception e) {
			System.out.println("Could not stop service.");
		}
		
	}

	@Override
	public void stopService() {
		stopMonitoring = true;
		isNodeActive = true;
		System.out.println("[INFO] STOPPING Jenkins HA SERVICE");
		try {
		monitor.stop();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Monitor getMonitor() {
		return monitor;
	}
	

	@Override
	public void onJoiningCluster(NodeDB db) {
		System.out.println("[INFO] Nodes are: ");
		System.out.println(db.getNodeStates().values());
	}

	@Override
	public void onLeavingCluster(NodeDB db) {
		System.out.println("[INFO] Nodes are: ");
		System.out.println(db.getNodeStates().values());
	}
	
	@Override
	public void onBooting() {
		System.out.println("[INFO] Booting up jenkins...");
		
		isNodeActive = true;
		
		try {
			Jenkins jenkins = Jenkins.getInstance();
			ACL.impersonate(ACL.SYSTEM);
			jenkins.doSafeRestart(null);
		} catch(Exception ex) {
			
		}
	}

	public void setActive(boolean activeFlag) {
		 isNodeActive = activeFlag;
	}

	public boolean isActive() {
		return isNodeActive;
	}

	@Override
	public NodeManager getNodeManager() {
		return monitor.getNodeManager();
	}

	@Override
	public PropertiesManager getPropertiesManager() {
		return monitor.getPropertiesManager();
	}

}
