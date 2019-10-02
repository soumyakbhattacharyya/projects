/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.cloud;

/**
 *
 * @author Cognizant
 */
public class SecurityContextBuilder {

	public String getKeyStoreFilePath() {
		return keyStoreFilePath;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}
	private String keyStoreFilePath;
	private String keyStorePassword;

	private SecurityContextBuilder(Builder builder) {
		this.keyStoreFilePath = builder.keyStoreFilePath;
		this.keyStorePassword = builder.keyStorePassword;
	}

	public static class Builder {

		//required
		private final String keyStoreFilePath;
		private final String keyStorePassword;

		public Builder(final String keyStoreFilePath, final String keyStorePassword) {
			this.keyStoreFilePath = keyStoreFilePath;
			this.keyStorePassword = keyStorePassword;
		}

		public SecurityContextBuilder build() {
			return new SecurityContextBuilder(this);
		}
	}
}
