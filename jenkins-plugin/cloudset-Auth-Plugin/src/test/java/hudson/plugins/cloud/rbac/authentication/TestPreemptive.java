package hudson.plugins.cloud.rbac.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

/**
 * Simple class to launch a jenkins build on run@Cloud platform, should also
 * work on every jenkins instance (not tested)
 * 
 */
public class TestPreemptive {

	//@Test
	public void testPreemptiveAuthentication() throws Exception {

		// Credentials
		String username = "272959";			
	    String token = "3ff718870073ac5561c39a97245f1028";	
	    String jenkinsUrl = "http://localhost:8080/jenkins";	
		String jobName = "TestJob-26874";
		String projectName = "P-26874";

		// Create your httpclient
		DefaultHttpClient client = new DefaultHttpClient();
				
		// Then provide the right credentials
		client.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(username, token));		
		
		// Generate BASIC scheme object and stick it to the execution context
		BasicScheme basicAuth = new BasicScheme();
		BasicHttpContext context = new BasicHttpContext();
		context.setAttribute("preemptive-auth", basicAuth);
		
		// Add as the first (because of the zero) request interceptor
		// It will first intercept the request and preemptively initialize the
		// authentication scheme if there is not
		client.addRequestInterceptor(new PreemptiveAuth(), 0);
		client.getParams().setParameter("name", 
				 "UTF-8");

		
		String getUrl = jenkinsUrl + "/job/"+jobName+"/lastBuild/api/xml?depth=0&projectSelect="+projectName;		
		System.out.println("getUrl is " + getUrl);

		try {
			HttpGet get = new HttpGet(getUrl);					
			HttpResponse response = client.execute(get,context);			
			System.out.println(getContent(response));

		} catch (IOException e) {			
			e.printStackTrace();
			System.out.println("Exception " + e);
		}
	}

	/**
	 * Preemptive authentication interceptor
	 * 
	 */
	static class PreemptiveAuth implements HttpRequestInterceptor {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.apache.http.HttpRequestInterceptor#process(org.apache.http.
		 * HttpRequest, org.apache.http.protocol.HttpContext)
		 */
		public void process(HttpRequest request, HttpContext context)
				throws HttpException, IOException {
			// Get the AuthState
			AuthState authState = (AuthState) context
					.getAttribute(ClientContext.TARGET_AUTH_STATE);

			// If no auth scheme available yet, try to initialize it
			// preemptively
			if (authState.getAuthScheme() == null) {
				AuthScheme authScheme = (AuthScheme) context
						.getAttribute("preemptive-auth");
				CredentialsProvider credsProvider = (CredentialsProvider) context
						.getAttribute(ClientContext.CREDS_PROVIDER);
				HttpHost targetHost = (HttpHost) context
						.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				if (authScheme != null) {
					Credentials creds = credsProvider
							.getCredentials(new AuthScope(targetHost
									.getHostName(), targetHost.getPort()));
					if (creds == null) {
						throw new HttpException(
								"No credentials for preemptive authentication");
					}
					authState.setAuthScheme(authScheme);
					authState.setCredentials(creds);
				}
			}

		}

	}

	public static String getContent(HttpResponse response) throws IllegalStateException, IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		StringBuilder total = new StringBuilder();
		String line = null;
		while ((line = r.readLine()) != null) {
			total.append(line);
		}	
		return total.toString();
	}
}