/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.ant;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.exception.FormitException;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.main.SequentialExecutor;
import com.cognizant.formit.model.AuditReport;
import com.cognizant.formit.model.steps.MajorStep;
import com.cognizant.formit.util.file.FileHelper;
import com.cognizant.formit.util.string.StringUtil;
import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

/**
 *
 * @author cognizant
 */
public final class AntUtil {

	private static final Logger l = Logger.getLogger(AntUtil.class);

	/**
	 * parse a build file and initiate project
	 *
	 * @param buildFile
	 * @return
	 */
	static public Project initiateProject(final File buildFile) {
		if (FileHelper.isValid(buildFile)) {
			// initiate project and parse corresponding xml file
			Project p = new Project();
			parse(buildFile, p);
			l.info("parsed blueprint file");
			DefaultLogger defaultLogger = getLogger();
			p.addBuildListener(defaultLogger);
			l.info("added default log listener");
			// associate custom build listener
			BuildListener buildListener = getCustomBuildListener();
			p.addBuildListener(buildListener);
			l.info("added blueprint execution event listener");
			return p;
		} else {
			throw new IllegalArgumentException("blueprint non - instantiable");
		}
	}

	/**
	 * parse a build file
	 *
	 * @param buildFile
	 * @param p
	 */
	static public void parse(final File buildFile, final Project p) {
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());
		p.init();
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		p.addReference("ant.projectHelper", helper);
		helper.parse(p, buildFile);
	}

	/**
	 * get custom build listener
	 *
	 * @return
	 */
	static public BuildListener getCustomBuildListener() {
		return AuditReport.newInstance();
	}

	/**
	 * get default logger for deployment sequence execution
	 *
	 * @return
	 */
	static public DefaultLogger getLogger() {
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_DEBUG);
		return consoleLogger;
	}

	static public int getNumberOfRequiredVMs(final Project p) {
		int numberOfRequiredVMs = 0;
		if (null != p && !p.getTargets().isEmpty()) {
			l.info("project : " + p.getName() + " has targets ");
			Hashtable targets = getTargets(p);
			//Set<Map.Entry> set = targets.entrySet();
			Iterator<Map.Entry> iterator = produceFrom(targets);
			while (iterator.hasNext()) {
				Target target = (Target) targets.get(iterator.next().getKey());
				l.info("got target :" + target.getName());
				Task[] tasks = target.getTasks();
				// hardcoding ...
				if (null != tasks && 0 != tasks.length && tasks.length == 1) {
					Task task = tasks[0];
					l.info("got task with name :" + task.getTaskName());
					l.info("got task with type :" + task.getTaskType());
					// assume inidividual component requires inidividual VM
					if (StringUtil.translate(task.getTaskType()).equalsIgnoreCase(MajorStep.CONTAINER)) {
						l.info("task type is of container hence will be requiring a VM");
						numberOfRequiredVMs++;
						l.info("numberOfRequiredVMs :" + numberOfRequiredVMs);
					}
				}
			}
		}

		return numberOfRequiredVMs;
	}

	/*
	 * get targets from a project
	 */
	static public Hashtable getTargets(final Project p) {
		return (null != p) ? p.getTargets() : null;
	}

	/*
	 * produce an iterator from a hashtable
	 */
	static public Iterator<Map.Entry> produceFrom(final Hashtable hashtable) {
		l.info("creating iterator for traversing through the list of targets");
		Set<Map.Entry> set = hashtable.entrySet();
		Iterator<Map.Entry> iterator = set.iterator();
		return iterator;
	}

	/**
	 * returns a project from the execution context
	 *
	 * @param context
	 * @return
	 */
	static public Project getProject(final Context context) {
		Project project = context.get(AppConstants.PROJECT) instanceof Project ? (Project) context.get(AppConstants.PROJECT) : null;
		return project;
	}

	static public void execute(final Project project) {
		try {
			project.executeTarget(project.getDefaultTarget());
		} catch (Exception ex) {
			throw new FormitException.ExecutionFailureException("Execution have failed", ex);
		}
	}
}
