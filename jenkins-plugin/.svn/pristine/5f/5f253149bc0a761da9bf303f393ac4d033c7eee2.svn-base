package com.cognizant.cloudset.framework.command.cli;

import hudson.cli.CLI;

import java.util.List;

import com.cognizant.cloudset.framework.Constants;
import com.cognizant.cloudset.framework.command.CommandParameters;
import com.cognizant.cloudset.framework.command.WithOperator;
import com.cognizant.cloudset.framework.exception.PreflightCoreException;
import com.cognizant.cloudset.framework.util.LogUtil;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC_ANON")
public class AbstractCommandHandler {
	public static final boolean HAS_LOGGED_IN = Boolean.TRUE;	

	public AbstractCommandHandler() {
		super();
	}

	/**
	 * log in to jenkins having cloudset authorization plugin enabled
	 * 
	 * @param args
	 *            : arguments required for loging in
	 * @return true if login has been successful
	 */
	protected boolean doLogin(String[] args) {
		try {
			if (!Constants.JENKINS_URL.isEmpty()) {
				LogUtil.log("Connecting jenkins @ " + Constants.JENKINS_URL
						+ " with credentials .... ");
				CLI._main(args);
				LogUtil.log("Connected to " + Constants.JENKINS_URL + " ....");
				return HAS_LOGGED_IN;
			} else {
				throw new AssertionError("JENKINS URL cannot be null");
			}
		} catch (Exception e) {
			LogUtil.log("Problem occurred during connecting to "
					+ Constants.JENKINS_URL);
			throw new PreflightCoreException.JenkinsLoginException(
					"problem happened while logging in", e);
		}
	}

	/**
	 * Log out method for Secured Jenkins
	 * 
	 * @param args
	 * @return
	 */
	protected void doLogOut(String[] args, final boolean hasLoggedIn) {
		try {
			if (!Constants.JENKINS_URL.isEmpty()) {
				if (hasLoggedIn) {
					LogUtil.log("Connecting jenkins @ " + Constants.JENKINS_URL
							+ " with credentials .... ");
					CLI._main(args);
					LogUtil.log("Successfully logged out from "
							+ Constants.JENKINS_URL + " ....");
					//return !HAS_LOGGED_IN;
				} else {
					throw new AssertionError("login first to log out");
				}
			} else {
				throw new AssertionError("JENKINS URL cannot be null");
			}
		} catch (Exception e) {
			LogUtil.log("Problem occurred during connecting to "
					+ Constants.JENKINS_URL);
			throw new PreflightCoreException.JenkinsLoginException(
					"problem happened while logging out", e);
		}
	}

	/**
	 * Creates the build Operator with arguments
	 * 
	 * @param params
	 * @return
	 */
	protected WithOperator[] getBuildJobOperator(
			final BuildJobCommandParameters params) {
		WithOperator[] buildOperators = new WithOperator[] { new WithOperator() {
			@Override
			public List<String> withOperation(List<String> argument) {
				argument.add(Constants.BUILD_COMMAND);
				argument.add(Constants.BUILD_OPTION_WAIT_FLAG);
				argument.add(Constants.BUILD_OPTION_VERBOSE_FLAG);
				argument.add(params.getJobName());
				return argument;
			}
		} };
		return buildOperators;
	}

	protected WithOperator[] getCreateJobOperator(
			final CreateJobCommandParameters params) {

		WithOperator[] createJobOperators = new WithOperator[] { new WithOperator() {
			@Override
			public List<String> withOperation(List<String> base) {
			
				base.add(Constants.BUILD_COMMAND);
				base.add(Constants.BUILD_OPTION_WAIT_FLAG);
				base.add(Constants.BUILD_OPTION_VERBOSE_FLAG);
				base.add(Constants.NAME_OF_JOB_GENERATOR);
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.WORKSPACE_PARAM + "="
						+ params.getCheckoutFrom());
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.BUILD_COMMAND_PARAM + "="
						+ params.getBuildCommand());
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.ENVIRONMENT_PARAM + "="
						+ params.getEnvironment());
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.REQUESTER_PARAM + "="
						+ params.getRequesterId());
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.TO_MAIL_PARAM + "="
						+ params.getRequesterMailId());
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.WORKSPACE_NAME_PARAM + "="
						+ params.getWorkspaceName());
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.IS_SONAR_ENABLE + "="
						+ params.getIsSonarEnable());
				base.add(Constants.BUILD_OPTION_PARAMETER);
				base.add(Constants.PROJECT_ID + "=" + params.getProjectId());
				return base;
			}
		} };

		return createJobOperators;
	}

	protected WithOperator[] getLoginParams(final CommandParameters params) {
		WithOperator[] securityOperators = new WithOperator[] { new WithOperator() {
			@Override
			public List<String> withOperation(List<String> argument) {
				argument.add(Constants.RSA_FILE_PARAMETER);
				argument.add(Constants.ID_RSA_FILE);
				argument.add(Constants.LOGIN_ARG);
				argument.add(Constants.PROJECT_ID_PARAMETER);
				argument.add(params.getProjectId());
				return argument;
			}
		} };
		return securityOperators;
	}

	protected WithOperator[] getViewJobOperator(
			final ViewJobCommandParameters params) {
		WithOperator[] viewJobOperators = new WithOperator[] { new WithOperator() {
			@Override
			public List<String> withOperation(List<String> argument) {
				argument.add(Constants.GET_JOB_COMMAND);
				argument.add(params.getJobName()+ Constants.GENERATED_JOB_POSTFIX);
				argument.add(Constants.PROJECT_ID_PARAMETER);
				argument.add(params.getProjectId());
				return argument;
			}
		} };
		return viewJobOperators;
	}

	protected WithOperator[] getShowJobOperator(
			final ShowBuildStatusCommandParameters params) {
		WithOperator[] showStatusOperators = new WithOperator[] { new WithOperator() {
			@Override
			public List<String> withOperation(List<String> argument) {
				argument.add(Constants.SHOW_JOB_LOG_COMMAND);
				argument.add(params.getJobName());
				argument.add(Constants.PROJECT_ID_PARAMETER);
				argument.add(params.getProjectId());
				return argument;
			}
		} };
		return showStatusOperators;
	}

	protected WithOperator[] getLogoutParams(final CommandParameters params) {
		WithOperator[] securityOperators = new WithOperator[] { new WithOperator() {
			@Override
			public List<String> withOperation(List<String> argument) {
				argument.add(Constants.RSA_FILE_PARAMETER);
				argument.add(Constants.ID_RSA_FILE);
				argument.add(Constants.LOGOUT_ARG);
				return argument;
			}
		} };
		return securityOperators;
	}

}