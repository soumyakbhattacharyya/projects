package com.cognizant.monitor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cognizant.nodes.ClusterNode;

public class NodeDB implements Serializable{
	
	private static final long serialVersionUID = 7526471155622776148L;
	
	private Map<String, ClusterNode> nodeStates;
	
	public NodeDB() {
		nodeStates = new HashMap<String, ClusterNode>();
	}
	
	public void clearDB() {
		if(nodeStates != null) {
			nodeStates.clear();
		}
	}
	
	public void setNodeState(ClusterNode node) {
		nodeStates.put(node.getName(),node);
	}
	
	public ClusterNode getNodeState(String nodeName) {
		return nodeStates.get(nodeName);
	}
	
	public Map<String, ClusterNode> getNodeStates() {
		return nodeStates;
	}
}
