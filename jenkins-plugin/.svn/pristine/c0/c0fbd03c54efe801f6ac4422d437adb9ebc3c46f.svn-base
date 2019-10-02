package com.cognizant.plugins;

import hudson.Plugin;
import hudson.model.Item;
import hudson.model.TopLevelItem;
import hudson.model.Descriptor.FormException;
import hudson.model.Job;
import hudson.model.Run;
import hudson.model.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import jenkins.model.Jenkins;

import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import com.cognizant.monitor.PropertiesManager;

import hudson.plugins.cloudset.rbac.constants.CloudSetRbacConstants;

public class AbortedJobsNotifierView extends View{
	
	private static final String ABORTED_JOB_FOLDERNAME = "abortedjobs";
	private static String jenkinsHome;
	private static String abortedJobsFolder;
	
	protected AbortedJobsNotifierView(String name) {
		super(name);
	}

	@Override
	public boolean contains(TopLevelItem arg0) {
		return false;
	}

	@Override
	public Item doCreateItem(StaplerRequest arg0, StaplerResponse arg1)
			throws IOException, ServletException {
		return null;
	}

	@Override
	public Collection<TopLevelItem> getItems() {
		return null;
	}

	@Override
	public void onJobRenamed(Item arg0, String arg1, String arg2) {
		
	}

	@Override
	protected void submit(StaplerRequest arg0) throws IOException,
			ServletException, FormException {
		
	}
	
	public List getAbortedJobs() {
		PropertiesManager manager = new PropertiesManager();
		FilenameFilter filter = null;
		
		List abortedJobsList = new ArrayList<String>();
		String[] list = null;
		
		if(manager.isUsingCloudSetAuthorization()) {
			final String projectID = getProjectIDForUser();
			filter = new FilenameFilter() {
				
				public boolean accept(File dir, String name) {
					if(name.startsWith(projectID)) {
						return true;
					}
					return false;
				}
			};			
		}
		
		try {
			jenkinsHome = System.getProperty("JENKINS_HOME");
			if(jenkinsHome == null) {
				throw new Exception("Jenkins home not found. Could not read aborted jobs directory.");
			} else {
				abortedJobsFolder = jenkinsHome+ABORTED_JOB_FOLDERNAME;
				File file = new File(abortedJobsFolder);
				if(file.exists() && file.isDirectory()) {
					if(manager.isUsingCloudSetAuthorization()) {
						list = file.list(filter);
					} else {
						list = file.list();
					}
				}
			}
		} catch(Exception ex) {
		}
		if(list != null && list.length>0) {
			for(int i=0;i<list.length;i++) {
				
				//checking if job is currently running
				if(manager.isUsingCloudSetAuthorization()) {
					int indexOfJobName = list[i].indexOf("_")+1;
					String jobName = list[i].substring(indexOfJobName);
					if(!isRunning(jobName)) {
						abortedJobsList.add(jobName);					
					}					
				} else {
					if(!isRunning(list[i])) {
						abortedJobsList.add(list[i]);					
					}
				}
			}
		}
		return abortedJobsList;
	}
	
	private String getProjectIDForUser() {
        String default_Project_Id = CloudSetRbacConstants.DEFAULT_PROJECT_ID;
        if (Stapler.getCurrentRequest() != null
                     && Stapler.getCurrentRequest().getSession() != null) {
               HttpSession sess = Stapler.getCurrentRequest().getSession();
               if (sess != null
                            && sess.getAttribute(CloudSetRbacConstants.PROJECT_ID) != null) {
                     default_Project_Id = (String) sess.getAttribute(CloudSetRbacConstants.PROJECT_ID);
               } else {
               }
        }
        return default_Project_Id;
	}

	
	private boolean isRunning(String job) {
		boolean isRunning = false;
		List<Job> jobs = Jenkins.getInstance().getAllItems(Job.class);
		for(Job eachJob:jobs) {
			if(eachJob.getName().equalsIgnoreCase(job)) {
				isRunning = eachJob.isBuilding();
				break;
			}
		}
		
		return isRunning;
	}
	
	/*private String getRESTResponse(String urlString) {
		String output = null;
		try {
			 
			URL url = new URL(Jenkins.getInstance().getRootUrl()+urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
	 
			if (conn.getResponseCode() != 200) {
				
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			output = br.readLine();
			conn.disconnect();
	 
		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		  
		  return output;
	}*/
	
	public boolean isAbortedJobsPresent() {
		List aborted = getAbortedJobs();
		if(aborted.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isAbortedJobsAbsent() {
		List aborted = getAbortedJobs();
		if(aborted.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public String getAbortedJobUrl(String job) {
		String url = "job/"+job;
		return url;
	}
	
	public String getJobBuildUrl(String job) {
		String url = "job/"+job+"/build";
		return url;
	}
	
	public String getLastBuildNumber(String job) {
		List<Job> jobs = Jenkins.getInstance().getAllItems(Job.class);
		for(Job eachJob:jobs) {
			if(eachJob.getName().equalsIgnoreCase(job)) {
				Run lastCompletedBuild = eachJob.getLastCompletedBuild();
				if(lastCompletedBuild != null) {
					return lastCompletedBuild.getDisplayName();
				}
			}
		}
		
		return "-";
	}
	
	public String getLastBuildDate(String job) {
		List<Job> jobs = Jenkins.getInstance().getAllItems(Job.class);
		for(Job eachJob:jobs) {
			if(eachJob.getName().equalsIgnoreCase(job)) {
				Run lastCompletedBuild = eachJob.getLastCompletedBuild();
				if(lastCompletedBuild != null && lastCompletedBuild.getId() != null) {
					return lastCompletedBuild.getId().split("_")[0];
				}
			}
		}
		
		return "-";
	}
	
	public String getLastBuildTime(String job) {
		List<Job> jobs = Jenkins.getInstance().getAllItems(Job.class);
		for(Job eachJob:jobs) {
			if(eachJob.getName().equalsIgnoreCase(job)) {
				Run lastCompletedBuild = eachJob.getLastCompletedBuild();
				if(lastCompletedBuild != null && lastCompletedBuild.getId() != null) {
					return lastCompletedBuild.getId().split("_")[1];
				}
			}
		}
		
		return "-";
	}
	
	public String getLastBuildResult(String job) {
		List<Job> jobs = Jenkins.getInstance().getAllItems(Job.class);
		for(Job eachJob:jobs) {
			if(eachJob.getName().equalsIgnoreCase(job)) {
				Run lastCompletedBuild = eachJob.getLastCompletedBuild();
				if(lastCompletedBuild != null && lastCompletedBuild.getResult() != null) {
					return eachJob.getLastCompletedBuild().getResult().toString();
				}
			}
		}
		
		return "-";
	}
	
	/*private String getJobBuildStatusUrl(String job) {
		String url = getAbortedJobUrl(job)+"/lastBuild/api/xml?xpath=//building";
		return url;
	}*/
	
	/*private String getJobBuildResultUrl(String job) {
		String url = getAbortedJobUrl(job)+"/lastBuild/api/xml?xpath=//result";
		return url;
	}*/
	
	/*private String getJobBuildDateUrl(String job) {
		String url = getAbortedJobUrl(job)+"/lastBuild/api/xml?xpath=//id";
		return url;
	}*/

}
