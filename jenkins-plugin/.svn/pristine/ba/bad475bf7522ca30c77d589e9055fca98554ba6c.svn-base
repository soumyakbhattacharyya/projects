package com.cognizant.monitor;

import java.util.Set;

import com.cognizant.nodes.ClusterNode;

public class NodeManager {
	
	private NodeDB nodeDB;
	
	private static NodeManager nodeMgr = new NodeManager();
	
	private NodeManager() {
	}
	
	public static NodeManager getInstance() {
		return nodeMgr;
	}
	
	public void setNodeDB(NodeDB db) {
		nodeDB = db;
	}
	
	public NodeDB getNodeDB() {
		return nodeDB;
	}
	
	public void setNodeStatus(ClusterNode node) {
		nodeDB.setNodeState(node);
	}
	
	public ClusterNode getNodeStatus(ClusterNode node) {
		return nodeDB.getNodeState(node.getName());
	}
	
	public boolean findActive() {
		if(nodeDB.getNodeStates().size() < 1) {
			return false;
		}
		Set<String> nodeNames = nodeDB.getNodeStates().keySet();
		for(String nodeName:nodeNames) {
			if(nodeDB.getNodeState(nodeName).getState().equals(State.ACTIVE)) {
				return true;
			}
		}
		return false;
	}
	
	public ClusterNode findNextInactive() {
		if(nodeDB.getNodeStates().size() < 1) {
			return null;
		}
		Set<String> nodeNames = nodeDB.getNodeStates().keySet();
		for(String nodeName:nodeNames) {
			if(nodeDB.getNodeState(nodeName).getState().equals(State.INACTIVE)) {
				return nodeDB.getNodeState(nodeName);
			}
		}
		return null;
	}
	
	public ClusterNode findBootingNode() {
		if(nodeDB.getNodeStates().size() < 1) {
			return null;
		}
		Set<String> nodeNames = nodeDB.getNodeStates().keySet();
		for(String nodeName:nodeNames) {
			if(nodeDB.getNodeState(nodeName).getState().equals(State.BOOTING)) {
				return nodeDB.getNodeState(nodeName);
			}
		}
		return null;
	}
	
	public ClusterNode findShuttingDownNode() {
		if(nodeDB.getNodeStates().size() < 1) {
			return null;
		}
		Set<String> nodeNames = nodeDB.getNodeStates().keySet();
		for(String nodeName:nodeNames) {
			if(nodeDB.getNodeState(nodeName).getState().equals(State.SHUTDOWN)) {
				return nodeDB.getNodeState(nodeName);
			}
		}
		return null;
	}
	
	public ClusterNode findNode(String nodeName) {
		if(nodeDB.getNodeStates().size() < 1) {
			return null;
		}
		return nodeDB.getNodeState(nodeName);
	}
	
	public boolean containsShuttingDownNode() {
		if(nodeDB.getNodeStates().size() < 1) {
			return false;
		}
		Set<String> nodeNames = nodeDB.getNodeStates().keySet();
		for(String nodeName:nodeNames) {
			if(nodeDB.getNodeState(nodeName).getState().equals(State.SHUTDOWN)) {
				return true;
			}
		}
		return false;
	}
	
	public ClusterNode getActiveNode() {
		if(nodeDB.getNodeStates().size() < 1) {
			return null;
		}
		Set<String> nodeNames = nodeDB.getNodeStates().keySet();
		for(String nodeName:nodeNames) {
			if(nodeDB.getNodeState(nodeName).getState().equals(State.ACTIVE)) {
				return nodeDB.getNodeState(nodeName);
			}
		}
		return null;
	}
	
	public void clearNodes() {
		nodeDB.clearDB();
	}

}
