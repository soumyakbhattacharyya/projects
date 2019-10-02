/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.ant;

import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.util.db.DBHelper;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.string.StringUtil;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.rundeck.api.domain.RundeckProject;

/**
 *
 * @author cognizant
 */
public class AntRundeckAdapter {

	private static final Logger l = Logger.getLogger(AntRundeckAdapter.class);

	/**
	 * This method will perform an important operation 1. Get all target for the given ANT project 2. Get the ID from the target definition
	 * 3. Find corresponding job rundeck 4. Update the node value to the job definition
	 *
	 * @param p
	 * @param rundeckProject
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void tagJobs(Project p, RundeckProject rundeckProject, final Map<String, RNode> vms, IConnectionPoolInitializer connectionPoolInitializer) throws SQLException, ClassNotFoundException, PropertyVetoException {
		String componentTag = "";
		if (null != p && !p.getTargets().isEmpty()) {
			Hashtable targets = AntUtil.getTargets(p);
			//Set<Map.Entry> set = targets.entrySet();
			Iterator<Map.Entry> iterator = AntUtil.produceFrom(targets);
			while (iterator.hasNext()) {
				String nodeTag = "";
				String jobId = "";
				Target target = (Target) targets.get(iterator.next().getKey());
				Task[] tasks = target.getTasks();
				// hardcoding ...
				if (null != tasks && 0 != tasks.length && tasks.length == 1) {
					Task task = tasks[0];
					// collect tag for a component and append an instance id to it
					RuntimeConfigurable configurable = task.getRuntimeConfigurableWrapper();
					Hashtable attributes = configurable.getAttributeMap();
					Iterator<Map.Entry> attributeIterator = AntUtil.produceFrom(attributes);
					for (; attributeIterator.hasNext();) {
						String key = String.valueOf(attributeIterator.next().getKey());
						if (StringUtils.equals(key, "tag")) {
							// get the "value" against the "tag" attribute of the component
							componentTag = String.valueOf(attributes.get(key));
						}
						if (StringUtils.equals(key, "id")) {
							// get the "value" against the "tag" attribute of the component
							jobId = String.valueOf(attributes.get(key));
						}
					}
					if (AppConstants._DRIVER.equals(jobId)) {
						continue;
					}
					// find a node with the tag
					RNode node = RNodeFactory.findFittingNode(componentTag, vms);
					// get the tag for the node
					if (null != node) {
						nodeTag = node.getTags();
						// update the database
						DBHelper dBHelper = DBHelper.newInstance(connectionPoolInitializer);
						dBHelper.updateJobDefn(jobId, rundeckProject.getName(), nodeTag);
						l.info("updated job with node value");
					}
				}
			}
		}
	}

	static public void loadRuntimeProperties(Project p, Map<String, String> profile, String instanceId) {
		l.info("profile :" + profile);
		try {
			for (Map.Entry<String, String> entry : profile.entrySet()) {
				l.info(entry);
				p.setUserProperty(StringUtil.getKeyNameMinusJobnamePrefix(entry.getKey().trim()), entry.getValue().trim());
			}
		} catch (NoSuchElementException ex) {
			l.error(ex.getMessage());
			// do nothing
		} finally {
			// at this level all profile properties have been loaded
			// now we set this executor's instance for future use
			p.setUserProperty(AppConstants.DRIVING_EXECUTOR_INSTANCE, instanceId);
		}
	}
}
