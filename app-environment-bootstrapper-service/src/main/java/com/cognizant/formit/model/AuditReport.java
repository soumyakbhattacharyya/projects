/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

/**
 *
 * Audit report detailing a deployment on an environment this class will push audit data to db
 */
public class AuditReport implements BuildListener {

	private static final Logger l = Logger.getLogger(AuditReport.class);

	public static AuditReport newInstance() {
		return new AuditReport();
	}

	@Override
	public void buildStarted(BuildEvent event) {
		l.info(event.getMessage());
	}

	@Override
	public void buildFinished(BuildEvent event) {
		l.info(event.getMessage());
	}

	@Override
	public void targetStarted(BuildEvent event) {
		l.info(event.getMessage());
	}

	@Override
	public void targetFinished(BuildEvent event) {
		l.info(event.getMessage());
	}

	@Override
	public void taskStarted(BuildEvent event) {
		l.info(event.getMessage());
	}

	@Override
	public void taskFinished(BuildEvent event) {
		l.info(event.getMessage());
	}

	@Override
	public void messageLogged(BuildEvent event) {
		l.info(event.getMessage());
	}
}
