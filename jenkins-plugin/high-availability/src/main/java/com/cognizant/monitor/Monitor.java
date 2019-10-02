package com.cognizant.monitor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import com.cognizant.nodes.ClusterNode;
import com.cognizant.plugins.HAEmailNotifier;

/**
 * @author 378046
 * Monitor class implementing JGroups to manage a cluster
 */
public class Monitor extends ReceiverAdapter {
	
	private Logger LOGGER = Logger.getLogger("Monitor");
	
	private JChannel channel;
	private String user_name = System.getProperty("user.name", "n/a");
	
	private List<Address> clusterList = null;
	
	private Address currentNodeAddress = null;

	private boolean isConnected = false;
	private boolean selfStarted = false;
	
	private MoniterService service;
	
	private NodeManager nodeManager;
	
	private PropertiesManager propertiesManager;
	
	private ClusterNode thisNode;
	
	public MoniterService getService() {
		return service;
	}

	public void setService(MoniterService service) {
		this.service = service;
	}

	public JChannel getChannel() {
		return channel;
	}

	public String getUser_name() {
		return user_name;
	}

	public Monitor() {
		
	}
	
	/**
	 * Start/Join a Cluster with name supplied by the param
	 * @param cluster
	 */
	public void start(String cluster) {
		
		try {
			propertiesManager = new PropertiesManager();
			
			// jgroups.tcpping.initial_hosts: required for a member to look up initial members of the cluster
			// jgroups.bind_addr: jgroups binds this IP to the cluster
			System.setProperty("jgroups.tcpping.initial_hosts", propertiesManager.getInitialTCPHosts());
			System.setProperty("jgroups.bind_addr", InetAddress.getLocalHost().toString().split("/")[1]);
			
			channel = new JChannel("tcp.xml");
//			channel = new JChannel();
			channel.setReceiver(this);
			
			isConnected = true;
			selfStarted = true;
			
			nodeManager = NodeManager.getInstance();
			NodeDB db = new NodeDB();
			nodeManager.setNodeDB(db);
			nodeManager.clearNodes();
			
			channel.connect(cluster);
			channel.getState(null, 10000);
		} catch (Exception e) {
			isConnected = false;
			selfStarted = false;
			e.printStackTrace();
		}
	}
	
	/**
	 * Stop/Leave a Cluster
	 * @throws Exception
	 */
	public void stop() throws Exception {
		if(channel != null && channel.isOpen()) {
			LOGGER.info("Stopping HA Channel");
			channel.close();
			isConnected = false;
		}
	}
	
	/**
	 * Broadcast this object to nodes across the cluster
	 * @param obj
	 * @throws Exception
	 */
	public void broadcastMessage(NodeDB db) throws Exception {
		Message msg = new Message(null, null, db);
		channel.send(msg);
	}
	
	public void viewAccepted(View new_view) {
		LOGGER.info("Number of nodes  "+new_view.getMembers());
        if(selfStarted) {
        	
        	clusterList = new_view.getMembers();
        	currentNodeAddress = clusterList.get(clusterList.size()-1);
        	
        	LOGGER.info("Current node address "+currentNodeAddress);
        	
        	ClusterNode currentNode = getVMDetails();
        	LOGGER.info("Current node object "+currentNode.toString());
    	    if(currentNodeAddress.equals(clusterList.get(0))) {
        		currentNode.setState(State.ACTIVE);
        		nodeManager.setNodeStatus(currentNode);
        		service.setActive(true);
        	}
    	    
    	    thisNode = currentNode;
        	
			selfStarted = false;
        } else {
        	
        	if(new_view.size() < clusterList.size()) {
        		
        		Address missing = findMissingNode(clusterList, new_view.getMembers());
        		NodeDB db = removeMissingNodeDetails(missing);
        		clusterList = new_view.getMembers();
        		service.onLeavingCluster(db);
        		
        	} else if(new_view.size() > clusterList.size()){
        		clusterList = new_view.getMembers();
        		if(propertiesManager.isPrimaryNodeRevertMode()) {
        			LOGGER.info("Revert Mode On: New node has joined .");
        			setActiveNodeToInactive(clusterList);
        		}
        	}
        }
    }

    public void receive(Message msg) {
    	NodeDB db = (NodeDB) msg.getObject();
        synchronized(nodeManager.getNodeDB()) {
        	nodeManager.setNodeDB(db);
        	service.onJoiningCluster(nodeManager.getNodeDB());
        }
        
        if(propertiesManager.isPrimaryNodeRevertMode()) {
        	LOGGER.info("Revert Mode On: Received Message .");
        	if(nodeManager.containsShuttingDownNode()) {
        		if(thisNode.getName().equals(propertiesManager.getPrimaryNodeName())) {
        			
        			//If cluster is started with slave, then Master node will not be present in NodeManager.
        			// We need to add Master node object into NodeMap
        			if(nodeManager.findNode(thisNode.getName()) == null) {
        				LOGGER.info("Revert Mode On: Booting original primary node .");
             			
             			ClusterNode currentNode = getVMDetails();
             			currentNode.setState(State.BOOTING);
             			nodeManager.setNodeStatus(currentNode);
             			try {
             				broadcastMessage(nodeManager.getNodeDB());
             				return;
             			} catch (Exception e) {
             				e.printStackTrace();
             			}
         			} else if(nodeManager.findNode(thisNode.getName()).getState().equals(State.DEAD)) {
         				LOGGER.info("Revert Mode On: Booting original primary node .");
            			
            			ClusterNode currentNode = nodeManager.findNode(thisNode.getName());
            			currentNode.setState(State.BOOTING);
            			nodeManager.setNodeStatus(currentNode);
            			try {
            				broadcastMessage(nodeManager.getNodeDB());
            				return;
            			} catch (Exception e) {
            				e.printStackTrace();
            			}
        			}
        		}
        		
        		//code for 'if not primary node'
        		if(!thisNode.getName().equals(propertiesManager.getPrimaryNodeName())
        				&& nodeManager.findNode(thisNode.getName()).getState().equals(State.SHUTDOWN)
        				&& nodeManager.findActive()) {
        			
        			LOGGER.info("Revert Mode On: Rebooting secondary node as primary is up .");
        			
        			service.onBooting();
        		}
        	}
        }
        
        //check for booting
        if(nodeManager.findBootingNode() != null && nodeManager.findBootingNode().getName().equals(thisNode.getName())) {
        	
        	service.onBooting();
        }
    }
    
    // to revert node to ACTIVE state
    private void setActiveNodeToInactive(List<Address> clusterList) {
		Address newNodeAddress = clusterList.get(clusterList.size()-1);
		String nodeName = newNodeAddress.toString().split("-")[0];
		
		if(nodeName.equals(propertiesManager.getPrimaryNodeName())) {
			LOGGER.info("Revert Mode On: Set active node to inactive .");
			ClusterNode currentNode = nodeManager.findNode(thisNode.getName());
			if(currentNode.getState().equals(State.ACTIVE)) {
				LOGGER.info("Revert Mode On: This node is active. Set to inactive .");
				currentNode.setState(State.SHUTDOWN);
				nodeManager.setNodeStatus(currentNode);
				try {
					broadcastMessage(nodeManager.getNodeDB());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    public NodeDB getCurrentState() {
    	return nodeManager.getNodeDB();
    }
    
    public void getState(OutputStream output) throws Exception {
	    synchronized(nodeManager.getNodeDB()) {
	        Util.objectToStream(nodeManager.getNodeDB(), new DataOutputStream(output));
	    }
	}
	 
	public void setState(InputStream input) throws Exception {
	    Object currentState = Util.objectFromStream(new DataInputStream(input));
	    synchronized(nodeManager.getNodeDB()) {
	    	nodeManager.setNodeDB((NodeDB) currentState);
	    }
	    
	    ClusterNode currentNode = getVMDetails();
	    if(propertiesManager.isPrimaryNodeRevertMode()) {
	    	
	    	LOGGER.info("Revert Mode On: Setting state.");
	    	
	    	if(!currentNode.getName().equals(propertiesManager.getPrimaryNodeName())) {
	    		LOGGER.info("Current node object "+currentNode.toString());
	        	assignMyRole(currentNode);
	        	
	        	try {
	    			broadcastMessage(nodeManager.getNodeDB());
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    	} else {
	    		if(nodeManager.containsShuttingDownNode()) {
	    			ClusterNode node = nodeManager.findNode(thisNode.getName());
	    			
	    			// When slave is on but master is yet to join the Cluster List.
	    			// Master node object is not added to the Map, so nodeManager will return null
	    			if(node != null) {
	    				node.setState(State.ACTIVE);
		        		nodeManager.setNodeStatus(node);
		        		try {
			    			broadcastMessage(nodeManager.getNodeDB());
			    			service.setActive(true);
			    		} catch (Exception e) {
			    			e.printStackTrace();
			    		}
	    			}
	    		}
	    	}
	    } else {
	    	assignMyRole(currentNode);
        	
        	try {
    			broadcastMessage(nodeManager.getNodeDB());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
	    }
	}
	
	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	private Address findMissingNode(List<Address> currentlist, List<Address> newlist) {
		Address missing = null;
		
		for(Address item:currentlist) {
			if(!newlist.contains(item)) {
				missing = item;
				break;
			}
		}
		return missing;
	}
	
	private NodeDB removeMissingNodeDetails(Address address) {
		ClusterNode missingNode = null;
		String nodeName = address.toString().split("-")[0];
		
		missingNode = nodeManager.findNode(nodeName);
		
		//toggle for primary node only if missing node is not in BOOTING state
		if(!missingNode.getState().equals(State.BOOTING)) {
			if(missingNode.getState().equals(State.INACTIVE)) {
				LOGGER.info("Just updating node as Dead..");
				missingNode.setState(State.DEAD);
				nodeManager.setNodeStatus(missingNode);
			} else if(missingNode.getState().equals(State.ACTIVE)) {
				LOGGER.info("Tryng to set node as Booting..");
				missingNode.setState(State.DEAD);
				nodeManager.setNodeStatus(missingNode);
				assignNewActiveNode(missingNode);				
			}
		}
		
		return nodeManager.getNodeDB();
	}
	
	private void assignNewActiveNode(ClusterNode deadNode) {
		ClusterNode newActive = nodeManager.findNextInactive();
		
		if(newActive != null && newActive.getName().equals(thisNode.getName())) {
			LOGGER.info("Found an Inactive node...");
			LOGGER.info("Inactive node is "+newActive+" ...");
			newActive.setState(State.BOOTING);
			nodeManager.setNodeStatus(newActive);
			try {
				
				// sending email
				boolean isSent = HAEmailNotifier.sendMessage(deadNode.getName(), deadNode.getIpAddress(), propertiesManager.getjenkinsUrl());
				
				broadcastMessage(nodeManager.getNodeDB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void assignMyRole(ClusterNode node) {
    	if(node.getState() != null) {	
    		
    		if(node.getState().equals(State.BOOTING)) {
    			
    		}
    	}
    		
		//set status in DB
    	if(node.getState() == null) {
    		if(!nodeManager.findActive() && nodeManager.findBootingNode() != null) {
    			LOGGER.info("Not found any active node..");
    			node.setState(State.ACTIVE);
    			
    			service.setActive(true);
    		} else {
    			LOGGER.info("Found an active node..");
    			node.setState(State.INACTIVE);
    		}
    		nodeManager.setNodeStatus(node);
    	}
	}
	
	private ClusterNode getVMDetails() {
		ClusterNode me = null;
		try {
			String name = InetAddress.getLocalHost().toString().split("/")[0];//address.toString();
			
			LOGGER.info("Inet Address "+InetAddress.getLocalHost());
			String ipAddress = InetAddress.getLocalHost().toString().split("/")[1];
			long totalMem = Runtime.getRuntime().totalMemory();
			long freeMem = Runtime.getRuntime().freeMemory();
			me = new ClusterNode(name, ipAddress, totalMem, freeMem);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			return me;
		}
	}
	
	public NodeManager getNodeManager() {
		return nodeManager;
	}
	
	public PropertiesManager getPropertiesManager() {
		return propertiesManager;
	}

}
