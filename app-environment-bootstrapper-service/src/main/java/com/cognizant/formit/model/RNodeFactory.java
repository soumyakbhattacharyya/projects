/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model;

import com.cognizant.formit.exception.FormitException;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.model.steps.MajorStep;
import com.cognizant.formit.util.ant.AntUtil;
import com.cognizant.formit.util.db.DBHelper;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.string.StringUtil;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

/**
 *
 * @author Cognizant
 */
public class RNodeFactory {

	private static final Logger l = Logger.getLogger(RNodeFactory.class);

	public static List<RNode> getDummyNodes() {
		RNode node2 = RNode._new();
		node2.setName("dummy-2");
		node2.setHostname("ip2:port");
		node2.setOsArch("dummy");
		node2.setOsFamily("dummy");
		node2.setOsName("abcd");
		node2.setOsVersion("dummy");
		node2.setUsername("dummy");
		node2.setTags("dummy-2");

		List<RNode> list = new ArrayList<RNode>();
		//list.add(node1);
		list.add(node2);
		return list;
	}

	private RNodeFactory() {
	}
	public static final RNodeFactory factory = new RNodeFactory();

	public static RNode newNode() {
		RNode node = RNode._new();
		return node;
	}

	/**
	 * produce RNode from cloud resource definition responsibility : (1) produce node (2) save RNodes against the tag in the vms (3) return
	 * a set representation of the same too many responsibilities have been clubbed to save parsing effort for the ant file
	 *
	 * @param spawnedVMs definition
	 * @param project having task definition
	 * @param vms updated
	 * @param uniqueRunId instance id as being supplied from portal
	 * @return set of nodes being produced
	 */
	public static Set<RNode> produceRNode(final List<VirtualMachineType> spawnedVMs, final Project project, final Map<String, RNode> vms, final String uniqueRunId,
			String userID,String osFamily) {
		List<VirtualMachineType> machines = spawnedVMs;
		Project p = project;
		Set<RNode> nodes = new HashSet<RNode>();
		String componentTag = "";
		String componentTagPlusInstanceId = "";
		if (null != p && !p.getTargets().isEmpty()) {
			Hashtable targets = AntUtil.getTargets(p);
			//Set<Map.Entry> set = targets.entrySet();
			Iterator<Map.Entry> iterator = AntUtil.produceFrom(targets);
			int numberOfContainer = 0;
			while (iterator.hasNext()) {
				Target target = (Target) targets.get(iterator.next().getKey());
				Task[] tasks = target.getTasks();
				// hardcoding ...
				if (null != tasks && 0 != tasks.length && tasks.length == 1) {
					Task task = tasks[0];
					// collect tag for a component and append an instance id to it
					if (StringUtil.translate(task.getTaskType()).equalsIgnoreCase(MajorStep.CONTAINER)) {
						String virtualMachineId = "";
						String dnsAddress = "";
						numberOfContainer++;
						VirtualMachineType machineType = null;
						RuntimeConfigurable configurable = task.getRuntimeConfigurableWrapper();
						Hashtable attributes = configurable.getAttributeMap();
						Iterator<Map.Entry> attributeIterator = AntUtil.produceFrom(attributes);
						Iterator<VirtualMachineType> machineIterator = machines.iterator();
						for (; attributeIterator.hasNext() && machineIterator.hasNext();) {
							String key = String.valueOf(attributeIterator.next().getKey());
							if (StringUtils.equals(key, AppConstants.TAG)) {
								// get the "value" against the "tag" attribute of the component
								componentTag = String.valueOf(attributes.get(key));
								// get a resource ... this is actually an oversimplified situation right now
								// pick a vm and associate it to container
								// TODO introduce some kind of vm selection logic that associates only a qualified
								// node to the component that it is going to host
								machineType = machineIterator.next();
								virtualMachineId = machineType.getProviderVirtualMachineId();
								dnsAddress = machineType.getPrivateDnsAddress();
								// construct tag of the node
								componentTagPlusInstanceId = (new StringBuffer()).append(componentTag).append(AppConstants.HASH).append(virtualMachineId).toString();
								//break;
								//String userName = "jpaas";
								RNode node = RNodeFactory.newNode().withTag(componentTagPlusInstanceId).withName(componentTagPlusInstanceId).withDescription("Node that facilitates deployment").withHostname(dnsAddress).forUniqueRunId(uniqueRunId).withUserName(userID).withOsFamily(osFamily);
								nodes.add(node);
								vms.put(componentTag, node);
								l.info("added following rnode to host a container " + node);
								l.info("State of VM_BANK :" + vms);
							}
						}
						// remove the node which was associated in previous loop
						boolean hasRemoved = machines.remove(machineType);
						l.info("Following machine " + machineType.getProviderVirtualMachineId() + " has been removed as it is used already " + " : " + hasRemoved);
					}
				}
			}
		}
		return nodes;
	}

	/**
	 * insert node definitions to database
	 *
	 * @param nodes
	 * @return
	 */
	public static int insertNodeToDB(Set<RNode> nodes, IConnectionPoolInitializer connectionPoolInitializer) {
		int numberOfInsertions = 0;
		try {
			// ok .. now we have all RNodes properly initialized and it means that we are ready to insert the resource
			// definition
			DBHelper dBHelper = DBHelper.newInstance(connectionPoolInitializer);
			// push resource definition to db
			List<RNode> insertables = new ArrayList<RNode>();
			Iterator<RNode> it = nodes.iterator();
			while (it.hasNext()) {
				insertables.add(it.next());
			}
			numberOfInsertions = dBHelper.create(insertables);
		} catch (SQLException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		} catch (ClassNotFoundException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		} catch (PropertyVetoException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		}
		return numberOfInsertions;
	}

	/**
	 * purge node from database having the name and corresponding to run id
	 *
	 * @param name
	 * @param uniqueRunId
	 * @param connectionPoolInitializer
	 * @return
	 */
	public static int purgeNodeFromDB(final String name, final String uniqueRunId, IConnectionPoolInitializer connectionPoolInitializer) {
		int numberOfDeletion = 0;
		try {
			DBHelper dBHelper = DBHelper.newInstance(connectionPoolInitializer);
			// delete resource definition from DB			
			numberOfDeletion = dBHelper.delete(name, uniqueRunId);
		} catch (SQLException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		} catch (ClassNotFoundException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		} catch (PropertyVetoException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		}
		return numberOfDeletion;
	}

	/**
	 * find nodes corresponding to an instance id
	 *
	 * @param instanceId
	 * @param connectionPoolInitializer
	 * @return
	 */
	public static List<RNode> findNodeForInstanceId(String instanceId, IConnectionPoolInitializer connectionPoolInitializer) {
		List<RNode> insertables = new ArrayList<RNode>();
		try {
			// ok .. now we have all RNodes properly initialized and it means that we are ready to insert the resource
			// definition
			DBHelper dBHelper = DBHelper.newInstance(connectionPoolInitializer);
			// push resource definition to db
			insertables = dBHelper.find(instanceId);
		} catch (SQLException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		} catch (ClassNotFoundException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		} catch (PropertyVetoException ex) {
			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
		}
		return insertables;
	}

	/**
	 * Get a VM whose tag "starts with" the tag that of a task TODO : incorporate a better design to add more flexibility to choose a node
	 *
	 * @param tag
	 * @param insertables
	 * @return
	 */
	static public RNode findFittingNode(String tag, final Map<String, RNode> vms) {
		Map<String, RNode> map = vms;
		if (null != map) {
			for (Map.Entry<String, RNode> entry : map.entrySet()) {
				if (null != entry.getValue().getTags() && entry.getValue().getTags().startsWith(tag)) {
					return entry.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * find IP address of the node (value) that maps to the tag (key) from within the collection vms
	 *
	 * @param tag
	 * @param vms
	 * @return IP address of the fitting node
	 */
	static public String findIPFromFittingNode(String tag, final Map<String, RNode> vms) {
		String ipPlusPort = "";
		try {
			for (Iterator<Map.Entry<String, RNode>> itr = vms.entrySet().iterator();; itr.hasNext()) {
				Map.Entry<String, RNode> entry = itr.next();
				l.info(entry.getKey());
				if (StringUtils.isNotBlank(tag) && tag.matches("(.*)" + entry.getKey() + "(.*)")) {
					ipPlusPort = entry.getValue().getHostname();
					l.info("received following ip plus port from the matching node :" + ipPlusPort);
					break;
				}
			}
		} catch (NoSuchElementException ex) {
			l.error(ex.getMessage());
			// do nothing
		}
		String onlyIpPortion = ipPlusPort.trim().substring(0, ipPlusPort.indexOf(":"));
		l.info(onlyIpPortion);
		return onlyIpPortion;
	}
}
