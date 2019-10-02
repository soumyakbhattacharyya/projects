package hudson.plugins.cloud.rbac.authentication;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.tasks.Shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jenkins.model.Jenkins;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class AppTest {
//	@Rule
//	public JenkinsRule j = new JenkinsRule();

//	@Test
//	public void first() throws Exception {
//		FreeStyleProject project = j.createFreeStyleProject();
//		project.getBuildersList().add(new Shell("echo hello"));
//		FreeStyleBuild build = project.scheduleBuild2(0).get();
//		System.out.println(build.getDisplayName() + " completed");
//		// TODO: change this to use HtmlUnit
//		String s = FileUtils.readFileToString(build.getLogFile());
//		//assertThat(s, contains("+ echo hello"));
//	}
	
	//@Test
	public void testTokenAuthentication(){
		try {
            
			String urlString = "http://localhost:8080/jenkins/job/Job-26860/lastBuild/api/xml?xpath=//building?token=3ff718870073ac5561c39a97245f1028";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");

            if (conn.getResponseCode() != 200) {
                  
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();
            System.out.println(output);
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
}