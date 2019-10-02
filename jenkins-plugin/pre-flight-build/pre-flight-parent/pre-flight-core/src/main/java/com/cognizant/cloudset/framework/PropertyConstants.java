/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework;

import com.cognizant.cloudset.framework.util.LogUtil;
import com.pholser.util.properties.BoundProperty;
import com.pholser.util.properties.PropertyBinder;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * reads proeprties from preflight.properties file available @
 * ${USER.HOME}/.buildbox
 * 
 * @author cognizant
 */
public final class PropertyConstants {

	public static final ConstantsReader constantsReader;

	static {
		constantsReader = readProperies();
	}

	private static ConstantsReader readProperies() {
		ConstantsReader constantsReader = null;
		try {
			constantsReader = PropertyBinder.forType(ConstantsReader.class)
					.bind(new File(System.getProperty("user.home")
							+ java.io.File.separator + ".buildbox"
							+ java.io.File.separator + "preflight.properties"));
		} catch (IOException ex) {
			LogUtil.log("error : unable to find preflight properties file");
		}
		return constantsReader;
	}

	public static void setProperty(String key, String value) {

	}

	public interface ConstantsReader {

		@BoundProperty("build.server.url")
		String buildServerUrl();

		@BoundProperty("user.id")
		String uid();

		@BoundProperty("user.ssh.keyfile.loc")
		String userSSHKeyfileLocation();

		@BoundProperty("internal.retry.time")
		String retryTime();

		@BoundProperty("internal.job.generator")
		String jobGeneratorName();

		@BoundProperty("internal.retry.count")
		String retryCount();

	}
}
