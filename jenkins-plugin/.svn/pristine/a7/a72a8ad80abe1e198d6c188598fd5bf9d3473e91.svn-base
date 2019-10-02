/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.exception;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * A generic (unchecked) exception when using the Preflight core API
 *
 * @author cognizant
 */
public class PreflightCoreException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PreflightCoreException(String message) {
		super(message);
	}

	public PreflightCoreException(String message, Throwable cause){
		super(message, cause);
	}
	
	/**
	 * exception during initial population of staging repository with dev workspace
	 */
	public static class CreateCommandException extends PreflightCoreException {

		public CreateCommandException(String message)   {
			super(message);
		}

		public CreateCommandException(String message, Throwable cause)  {
			super(message, cause);
		}
	}

	/**
	 * exception during subsequent syncing of staging repository with dev workspace
	 */
	public static class SyncCommandException extends PreflightCoreException {

		public SyncCommandException(String message)  {
			super(message);
		}

		public SyncCommandException(String message, Throwable cause)  {
			super(message, cause);
		}
	}

	/**
	 * exception during creation of Jenkins job
	 */
	public static class CreateJobException extends PreflightCoreException {

		public CreateJobException(String message)  {
			super(message);
		}

		public CreateJobException(String message, Throwable cause)  {
			super(message, cause);
		}
	}

	/**
	 * exception during building of Jenkins job
	 */
	public static class BuildJobException extends PreflightCoreException {

		public BuildJobException(String message)  {
			super(message);
		}

		public BuildJobException(String message, Throwable cause)  {
			super(message, cause);
		}
	}

	/**
	 * exception during viewing of Jenkins job
	 */
	public static class ViewJobException extends PreflightCoreException {

		public ViewJobException(String message)  {
			super(message);
		}

		public ViewJobException(String message, Throwable cause)  {
			super(message, cause);
		}
	}
	/**
	 * exception during viewing of Jenkins job
	 */
	public static class ShowJobException extends PreflightCoreException {

		public ShowJobException(String message)  {
			super(message);
		}

		public ShowJobException(String message, Throwable cause)  {
			super(message, cause);
		}
	}
	/**
	 * exception during viewing of Jenkins job
	 */
	public static class JenkinsLoginException extends PreflightCoreException {

		public JenkinsLoginException(String message)  {
			super(message);
		}

		public JenkinsLoginException(String message, Throwable cause)  {
			super(message, cause);
		}
	}
	/**
	 * exception during viewing of Jenkins job
	 */
	public static class JenkinsCLIException extends PreflightCoreException {

		public JenkinsCLIException(String message) {
			super(message);
		}

		public JenkinsCLIException(String message, Throwable cause)  {
			super(message, cause);
		}
	}
}
