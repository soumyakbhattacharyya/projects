/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.exception;

import com.cognizant.jpaas2.commons.expection.PlatformException;

/**
 *
 * @author Cognizant
 */
public class FormitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FormitException(String message) {
		super(message);
	}

	public FormitException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * custom exception for various stages
	 */
	public static class FailedToGetExecutorException extends FormitException {

		private static final long serialVersionUID = 1L;

		public FailedToGetExecutorException(String message) {
			super(message);
		}

		public FailedToGetExecutorException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * custom exception for various stages
	 */
	public static class ConfigInitializationException extends FormitException {

		private static final long serialVersionUID = 1L;

		public ConfigInitializationException(String message) {
			super(message);
		}

		public ConfigInitializationException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class NullConfigurationException extends FormitException {

		private static final long serialVersionUID = 1L;

		public NullConfigurationException(String message) {
			super(message);
		}

		public NullConfigurationException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class JPaaSPlatformException extends FormitException {

		private static final long serialVersionUID = 1L;

		public JPaaSPlatformException(String message) {
			super(message);
		}

		public JPaaSPlatformException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class FailedToUpdateNodeDefinitionInDBException extends FormitException {

		private static final long serialVersionUID = 1L;

		public FailedToUpdateNodeDefinitionInDBException(String message) {
			super(message);
		}

		public FailedToUpdateNodeDefinitionInDBException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class TaggingFailureException extends FormitException {

		private static final long serialVersionUID = 1L;

		public TaggingFailureException(String message) {
			super(message);
		}

		public TaggingFailureException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class CustomPropertyLoadingFailureException extends FormitException {

		private static final long serialVersionUID = 1L;

		public CustomPropertyLoadingFailureException(String message) {
			super(message);
		}

		public CustomPropertyLoadingFailureException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class ExecutionFailureException extends FormitException {

		private static final long serialVersionUID = 1L;

		public ExecutionFailureException(String message) {
			super(message);
		}

		public ExecutionFailureException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class FailedToInitializeException extends FormitException {

		private static final long serialVersionUID = 1L;

		public FailedToInitializeException(String message) {
			super(message);
		}

		public FailedToInitializeException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class FailedToLoadSequenceException extends FormitException {

		private static final long serialVersionUID = 1L;

		public FailedToLoadSequenceException(String message) {
			super(message);
		}

		public FailedToLoadSequenceException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class NoAppropriateSequenceFound extends FormitException {

		private static final long serialVersionUID = 1L;

		public NoAppropriateSequenceFound(String message) {
			super(message);
		}

		public NoAppropriateSequenceFound(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class NoExecutionContextFound extends FormitException {

		private static final long serialVersionUID = 1L;

		public NoExecutionContextFound(String message) {
			super(message);
		}

		public NoExecutionContextFound(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
