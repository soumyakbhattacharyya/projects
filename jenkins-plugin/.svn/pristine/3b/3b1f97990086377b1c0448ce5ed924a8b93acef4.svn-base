/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command.file;

import com.cognizant.cloudset.framework.Constants;
import com.cognizant.cloudset.framework.command.cli.*;
import com.cognizant.cloudset.framework.command.CommandHandler;
import com.cognizant.cloudset.framework.exception.PreflightCoreException;
import com.cognizant.cloudset.framework.util.DateUtil;
import com.cognizant.cloudset.framework.util.FileUtil;
import com.cognizant.cloudset.framework.util.GitUtil;
import com.cognizant.cloudset.framework.util.LogUtil;
import hudson.cli.CLI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * command to sync developer's workspace and staging repository
 *
 * @author 239913
 */
public class SyncCommandHandler implements CommandHandler<SyncCommandParameters> {

	@Override
	public void execute(SyncCommandParameters params) throws PreflightCoreException {
		if (null != params) {

			final String devStagingArea = Constants.DEV_STAGING_AREA;
			final String workspaceAbsPath = params.getWorkspaceAbsPath();
			final String workspaceName = params.getWorkspaceName();
			final String destinationAbsPath = devStagingArea + java.io.File.separator + workspaceName;
			final FileUtil fileUtility = new FileUtil();
			final File destination = new File(destinationAbsPath);
			boolean haveAdditionOrDeletionTakenPlace = false;

			// this file is suppose to pre exists in the developer's workspace 
			// since this is not the first step
			File lastPreflightTimestamp = new File(workspaceAbsPath + java.io.File.separator + Constants.LAST_PREFLIGHT_TIMESTAMP + Constants.TEXT_FILE_EXTENTION);

			if (lastPreflightTimestamp.exists()) {

				long lastPreflightBuildTime;
				try {
					lastPreflightBuildTime = fileUtility.getLastBuildTimestamp(lastPreflightTimestamp);
				} catch (IOException ex) {
					throw new PreflightCoreException.SyncCommandException("exception while syncing workspace", ex);
				}
				LogUtil.log("pre - flight build has happened on :" + DateUtil.timestampToStringifiedDate(lastPreflightBuildTime));

				if (destination.exists()) {
					LogUtil.log("staging region on local machine exists. proceding to sync");
					String[] files = destination.list(HiddenFileFilter.VISIBLE);
					if (null != files && files.length != 0) {
						try {
							LogUtil.log("destination has files");
							// sync
							// find new / modified files
							fileUtility.setSrcPath(workspaceAbsPath);
							fileUtility.setDstPath(destinationAbsPath);

							// find all modified files
							List<FileUtil.Entry> newOrModifiedFiles = fileUtility.getNewOrModifiedFiles(lastPreflightBuildTime, true, true);

							// find all deleted files
							fileUtility.setIgnoreHidden(true);
							List<FileUtil.Entry> deletedFiles = fileUtility.getDeletedFiles(lastPreflightBuildTime, true, true);

							// sync modified / added files
							Iterator<FileUtil.Entry> itr = newOrModifiedFiles.iterator();
							while (itr.hasNext()) {
								String str = itr.next().getFilename();
								FileUtils.copyFile(new File(workspaceAbsPath + java.io.File.separator + str), new File(destinationAbsPath + java.io.File.separator + str));
							}
							// the staging must already have been initialized
							File file = new File(devStagingArea + java.io.File.separator + workspaceName + java.io.File.separator + ".git");
							if (file.exists()) {
								LogUtil.log("staging location :" + devStagingArea + java.io.File.separator + workspaceName + " is in initialized state");
								try {
									GitUtil.addPlusCommit(file);
								} catch (GitAPIException ex) {
									throw new PreflightCoreException.SyncCommandException("exception while syncing workspace", ex);
								}
								haveAdditionOrDeletionTakenPlace = true;
							} else {
								throw new PreflightCoreException.SyncCommandException("please initialize staging repository to proceed");
							}

							// find deleted files
							// apply change for deleted files
							List<String> deletedFilesAbsPathList = new ArrayList<String>();
							Iterator<FileUtil.Entry> deletedFileitr = deletedFiles.iterator();
							while (deletedFileitr.hasNext()) {
								String str = deletedFileitr.next().getFilename();
								// delete files
								String deletable = destinationAbsPath + java.io.File.separator + str;
								if (!deletable.contains(Constants.DOT_GIT_REPOSITORY)) {
									deletedFilesAbsPathList.add(str);
									boolean hasDeleted = (new File(deletable)).delete();
									if (!hasDeleted) {
										throw new PreflightCoreException.SyncCommandException("deletion has failed : syncing unsuccessful");
									}
								}
							}
							// deletion did have taken place
							if (file.exists() && deletedFilesAbsPathList.size() > 0) {
								LogUtil.log("staging location :" + devStagingArea + java.io.File.separator + workspaceName + " is in initialized state");
								try {
									GitUtil.removePlusCommit(file, deletedFilesAbsPathList);
								} catch (GitAPIException ex) {
									throw new PreflightCoreException.SyncCommandException("exception while syncing workspace", ex);
								}
								haveAdditionOrDeletionTakenPlace = true;
							}

							if (haveAdditionOrDeletionTakenPlace) {

								// finally push
								GitUtil.push(file);

								// recreate LAST-PREFLIGHT-TIMESTAMP.txt and insert timestamp							
								try {
									boolean hasDeleted = lastPreflightTimestamp.delete();
									if (hasDeleted) {
										boolean hasCreated = lastPreflightTimestamp.createNewFile();
										if (!hasCreated) {
											throw new PreflightCoreException.SyncCommandException("unable to recreate timestamp file");
										}
									} else {
										throw new PreflightCoreException.SyncCommandException("unable to delete timestamp file");
									}
								} catch (IOException ex) {
									throw new PreflightCoreException.SyncCommandException("unable to create timestamp file - message -> : " + ex.getMessage());
								}
								FileUtils.writeStringToFile(lastPreflightTimestamp, String.valueOf(Constants.LAST_PREFLIGHT_TIMESTAMP + "=" + System.currentTimeMillis()));
								LogUtil.log("updated last preflight timestamp file");
							}
						} catch (IOException ex) {
							throw new PreflightCoreException.SyncCommandException("exception while syncing workspace", ex);
						}
					} else {
						throw new PreflightCoreException.SyncCommandException("during initialization there should be nothing but .git folder");
					}
				} else {
					throw new PreflightCoreException.SyncCommandException("clone remote first");
				}


			} else {
				throw new PreflightCoreException.SyncCommandException("there has already been an initialization. no need to re - initialize");
			}
		} else {
			throw new PreflightCoreException.SyncCommandException("inadequet number of parameters");
		}
	}
}
