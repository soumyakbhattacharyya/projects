package hudson.plugins.cloud.util;

import javax.servlet.http.HttpSession;

import org.kohsuke.stapler.Stapler;

public class SessionUtil {
	
	public String getSessionValues(String keyName){
		
		String value = "";
		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			HttpSession sess = Stapler.getCurrentRequest().getSession();
			if (sess != null
					&& sess.getAttribute(keyName) != null) {
				value = (String) sess
						.getAttribute(keyName);
			}
		}
		return value;
	}

}
