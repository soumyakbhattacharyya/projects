package com.cognizant.formit.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("project")
public class Project {
	
	/*@XStreamAlias("node")
	private NodeResource nodeResource;*/
	
	 @XStreamImplicit
	private List<NodeResource> list = new ArrayList<NodeResource>(); 

	/**
	 * @return the list
	 */
	public List<NodeResource> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<NodeResource> list) {
		this.list = list;
	}

	/**
	 * @return the nodeResource
	 *//*
	public NodeResource getNodeResource() {
		return nodeResource;
	}

	*//**
	 * @param nodeResource the nodeResource to set
	 *//*
	public void setNodeResource(NodeResource nodeResource) {
		this.nodeResource = nodeResource;
	}*/
	

}
