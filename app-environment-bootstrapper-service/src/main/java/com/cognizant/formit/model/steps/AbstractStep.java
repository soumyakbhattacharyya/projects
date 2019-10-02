/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model.steps;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.chain.ChainExecutionContextFactory;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.main.Executor;
import com.cognizant.formit.main.SequentialExecutor;
import com.cognizant.formit.main.ExecutorFactory;
import com.cognizant.formit.util.rundeck.RundeckHelper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * hosts utility operations that extending steps use
 *
 * @author Cognizant
 */
public abstract class AbstractStep extends Task implements Step {

	protected String id;
	protected String description;
	protected String type;
	protected String tag;
	protected String serialNumber;

	@Override
	public String toString() {
		return "AbstractStep{" + "id=" + id + ", description=" + description + ", type=" + type + ", tag=" + tag + '}';
	}

	/**
	 * finds all properties of a rundeck job identified by _id before invoking the same further these properties are being bonded to the
	 * actual argument passed per deployment profile
	 *
	 * @param id
	 */
	protected Properties prepareOptions(String _id) {
		// newExecutor id of the step ... should be same as that being provided in the blueprint
		String id = _id;
		// newExecutor corresponding executor
		ChainExecutionContext executionContext = getGoverningExecutor();
		// newExecutor corresponding rundeck instance
		return executionContext.getRundeckHelper().getJobOptionLoadedWithActualVal(id, getProject(), executionContext);
	}

	/**
	 * newExecutor the executor which is running these steps
	 *
	 * @return
	 */
	protected ChainExecutionContext getGoverningExecutor() {
		// newExecutor corresponding project
		Project _project = getProject();
		// newExecutor corresponding run id
		String runId = _project.getUserProperty(AppConstants.DRIVING_EXECUTOR_INSTANCE);
		// newExecutor corresponding executor
		ChainExecutionContext executionContext = ChainExecutionContextFactory.getExecutionContext(runId);
		return executionContext;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = super.getDescription();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
}
