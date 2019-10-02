package com.cognizant.cloudset.framework.command.cli;

import hudson.cli.CLI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.cognizant.cloudset.framework.Constants;
import com.cognizant.cloudset.framework.command.CommandHandler;
import com.cognizant.cloudset.framework.command.CommandParameters;
import com.cognizant.cloudset.framework.exception.PreflightCoreException;
import com.cognizant.cloudset.framework.util.LogUtil;

public class ShowBuildStatusCommandHandler extends AbstractCommandHandler
		implements CommandHandler<ShowBuildStatusCommandParameters> {

	
	@Override
	public void execute(ShowBuildStatusCommandParameters params)
			throws PreflightCoreException {
		if (params != null) {
			String[] args = params.list2Array(super.getLoginParams(params));
			boolean hasLoggedIn = super.doLogin(args);
			LogUtil.log("Login status :: ---- "+hasLoggedIn);
			boolean hasDeleted = false;
			
			File tempFile = new File(Constants.SHOW_JOB_STATUS_TXT);
			if (hasLoggedIn) {
				try {
					String[] showJobArgs = params.list2Array(super.getShowJobOperator(params));
					System.out.println("Final parameters : " + Arrays.asList(showJobArgs));
					PrintStream original = System.out;
					PrintStream prntStrm = new PrintStream(new BufferedOutputStream(
							new FileOutputStream(tempFile.getAbsolutePath())),true, "UTF-8");
					
					System.setOut(prntStrm);
					
					CLI._main(showJobArgs);
					
					boolean isErrorenous = false;
					
					List<String> lines = FileUtils.readLines(tempFile);
				
					for (String line : lines) {
						if (line.contains(Constants.FAILURE)) {
							isErrorenous = true;
							break;
						}
					}
					System.setOut(original);
				
					prntStrm.close();
				
					if (isErrorenous) {			
					
						String[] logoutArgs = params.list2Array(super.getLogoutParams(params));
						
						super.doLogOut(logoutArgs, hasLoggedIn);
						hasLoggedIn=false;
						throw new PreflightCoreException.ShowJobException("ERROR: Job build has faild");
					}

				} catch (Exception ex) {
					throw new PreflightCoreException.JenkinsLoginException(
							"Jenkins is having some problem", ex);
				} finally {
					hasDeleted = tempFile.delete();
					if (!hasDeleted) {
						throw new PreflightCoreException.ViewJobException(
								"deletion has failed :job will run into problem");
					}
				}
			}
			super.doLogOut(params.list2Array(getLogoutParams(params)),hasLoggedIn);
		} else {
			throw new PreflightCoreException.ShowJobException("parameter should not be null");
		}
	}

}
