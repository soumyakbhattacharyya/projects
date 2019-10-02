package hudson.plugins.cloudset.rbac.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class SecurityUtil {

	public static void applySSLInformation(String securityKeyfilePath,
			String rbacPassword) {
		
		if (securityKeyfilePath != null && rbacPassword != null) {

			System.setProperty("javax.net.ssl.trustStore", securityKeyfilePath);
			System.setProperty("javax.net.ssl.trustStorePassword", rbacPassword);

			HttpsURLConnection
					.setDefaultHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String string, SSLSession ssls) {
							return true;
						}
					});			

		} else {
			throw new RuntimeException(
					"Exception occured while setting SSL info");
		}

	}
}
