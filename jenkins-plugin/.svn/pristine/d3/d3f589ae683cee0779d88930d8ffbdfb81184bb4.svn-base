/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command.cli;

import com.cognizant.cloudset.framework.Constants;
import com.cognizant.cloudset.framework.command.CommandHandler;
import com.cognizant.cloudset.framework.exception.PreflightCoreException;
import com.cognizant.cloudset.framework.util.LogUtil;
import hudson.cli.CLI;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 * builds a generated job
 *
 * @author cognizant
 */
public class ViewJobCommandHandler extends AbstractCommandHandler implements CommandHandler<ViewJobCommandParameters> {
	private static final Logger logger = Logger.getLogger(ViewJobCommandHandler.class.getName());
	@Override
	public void execute(ViewJobCommandParameters params) throws PreflightCoreException {
		// here we run the processing logic to create a job on behalf of the developer
		// System.out.println("creating developer job with following parameters : " + params);
		if (null != params) {
			String[] args = params.list2Array(super.getLoginParams(params));
			boolean hasLoggedIn = super.doLogin(args);
			if (hasLoggedIn) {
				try {
					// the response to the API call will dump the job config on console
					// we need to figure out if the job which is being searched for "does"
					// exists in the reposnse
					// for this we will dump the console in a temporary file under 
					// ${USER.HOME}/.buildbox named as get-job-response.txt
					// find the presence of the file and updated "found" flag
					// of the parameter to true
					// finally will delete the file and set the System.out back to console
					String[] viewJobArgs = params.list2Array(super.getViewJobOperator(params));
					logger.info("final parameters : " + Arrays.asList(viewJobArgs));
					File tempFile = new File(Constants.GET_JOB_RESPONSE_TXT);
					PrintStream original = System.out;
					PrintStream p = new PrintStream(new BufferedOutputStream(new FileOutputStream(tempFile.getAbsolutePath())), true, "UTF-8");
					System.setOut(p);
					int status =CLI._main(viewJobArgs);
					if(status==-1){
						throw new PreflightCoreException.JenkinsCLIException("Viewing a job has returned an error");
					}
					// find in file
					//boolean found =findInJobConfig(params.getJobName(), tempFile);
					
					if (findInJobConfig(params.getJobName(), tempFile)) {
						params.setPresent(true);
					}
					System.setOut(original);
					
					p.close();
					boolean hasDeleted=tempFile.delete();
					
					if (!hasDeleted) {
						throw new PreflightCoreException.ViewJobException("deletion has failed : next time view job will run into problem");
					}
					LogUtil.log("done with finding if a specific job exists on the server");
				} catch (Exception ex) {
					throw new PreflightCoreException.ViewJobException("exception thrown while viewing job", ex);
				}
			} else {
				throw new PreflightCoreException.JenkinsLoginException("Cannot log into Jenkins");
			}
			String[] logoutArgs = params.list2Array(super.getLogoutParams(params));
			super.doLogOut(logoutArgs,hasLoggedIn);
			
			
		} else {
			throw new PreflightCoreException.ViewJobException("null parameter is unacceptable while viewing job");
		}
	}
/*
 * 
 */
	private boolean findInJobConfig(String jobName, File tempFile) throws IOException {
		boolean found = false;
		List<String> lines = FileUtils.readLines(tempFile);
		for (String line : lines) {
			if (line.contains(jobName)) {
				found = true;
				break;
			}
		}		
		return found;
	}
}
