	/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command.file;

import com.cognizant.cloudset.framework.Constants;
import com.cognizant.cloudset.framework.command.CommandHandler;
import com.cognizant.cloudset.framework.exception.PreflightCoreException;
import com.cognizant.cloudset.framework.util.GitUtil;
import com.cognizant.cloudset.framework.util.LogUtil;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * CreateCommandHandler
 *
 * CreateCommandHandler syncs developer workspace with the staging. Typically bootstrap process initializes an emtry git repository on
 * ${USER.HOME}/.buildbox. This command updates the directory with the source code from the developer workspace. Further it adds, commits
 * and pushes to remote master
 *
 * @author cognizant
 */
public class CreateCommandHandler implements CommandHandler<CreateCommandParameters> {
	
	@Override
	public void execute(CreateCommandParameters params) throws PreflightCoreException {
		if (null != params) {
			boolean hasThereBeenAnyChangeToFileSystem = false;
			String devStagingArea = Constants.DEV_STAGING_AREA;
			String workspaceAbsPath = params.getWorkspaceAbsPath();
			String workspaceName = params.getWorkspaceName();
			File lastPreflightTimestamp = new File(workspaceAbsPath + java.io.File.separator + Constants.LAST_PREFLIGHT_TIMESTAMP + Constants.TEXT_FILE_EXTENTION);

			if (!lastPreflightTimestamp.exists()) {

				File destination = new File(devStagingArea + java.io.File.separator + workspaceName);
				File source = new File(workspaceAbsPath);

				if (destination.exists()) {
					// if the directory only consists of .git hidden folder
					// copy the content
					LogUtil.log("staging region on local machine exists");
					String[] files = destination.list(HiddenFileFilter.VISIBLE);
					if (null != files && files.length == 0) {
						try {
							LogUtil.log("copying workspace to staging");
							FileUtils.copyDirectory(source, destination);
							// create LAST-PREFLIGHT-TIMESTAMP.txt and insert timestamp
							boolean hasCreated = lastPreflightTimestamp.createNewFile();
							if (hasCreated) {
								FileUtils.writeStringToFile(lastPreflightTimestamp, String.valueOf(Constants.LAST_PREFLIGHT_TIMESTAMP + "=" + System.currentTimeMillis()));
								LogUtil.log("created last preflight timestamp file");
								hasThereBeenAnyChangeToFileSystem = true;
							} else {
								LogUtil.log("timestamp file exists which should not, in case you are running the utility first time?");
							}
						} catch (IOException ex) {
							throw new PreflightCoreException.CreateCommandException("exception while creating initial workspace", ex);
						}
					} else {
						throw new PreflightCoreException.CreateCommandException("during initialization there should be nothing but .git folder");
					}
				} else {
					throw new PreflightCoreException.CreateCommandException("clone remote first");
				}
				// the staging must already have been initialized
				File file = new File(devStagingArea + java.io.File.separator + workspaceName + java.io.File.separator + ".git");
				if (file.exists() && hasThereBeenAnyChangeToFileSystem) {
					LogUtil.log("staging location :" + devStagingArea + java.io.File.separator + workspaceName + " is in initialized state");
					try {
						GitUtil.addPlusCommit(file);
						GitUtil.push(file);
					} catch (IOException ex) {
						throw new PreflightCoreException.CreateCommandException("exception while creating initial workspace", ex);
					} catch (GitAPIException ex) {
						throw new PreflightCoreException.CreateCommandException("exception while creating initial workspace", ex);
					}

				} else {
					throw new PreflightCoreException.CreateCommandException("please initialize staging repository to proceed");
				}

			} else {
				LogUtil.log("there has already been an initialization. no need to re - initialize");
			}
		} else {
			throw new PreflightCoreException.CreateCommandException("inadequet number of parameters");
		}
	}
}
