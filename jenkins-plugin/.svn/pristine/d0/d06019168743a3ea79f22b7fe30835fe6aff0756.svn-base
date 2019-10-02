/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

import com.cognizant.cloudset.framework.Constants;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * 
 * @author cognizant
 */
@SuppressFBWarnings(value = "BC_UNCONFIRMED_CAST_OF_RETURN_VALUE")
public final class GitUtil {

	private static final int RETRY_ATTEMPTS = Constants.RETRY_COUNT;

	public static void addPlusCommit(File gitFolder) throws IOException,
			GitAPIException {
		Git git = Git.open(gitFolder);
		PropertyUtil.getProperty().put("origin", getOrigin(gitFolder));
		// addPlusCommit all files
		git.add().addFilepattern(".").call();
		// commit all files
		git.commit()
				.setMessage(
						"adding changes : auto commit done by pre-flight utility @ :"
								+ DateUtil.timestampToStringifiedDate()).call();
	}

	public static void removePlusCommit(File gitFolder,
			List<String> deletableFiles) throws IOException, GitAPIException {
		Git git = Git.open(gitFolder);
		// addPlusCommit all files
		Iterator<String> listOfDeletableFiles = deletableFiles.iterator();
		while (listOfDeletableFiles.hasNext()) {
			String filePath = listOfDeletableFiles.next().replace("\\", "/");
			git.rm().addFilepattern(filePath).call();
		}
		// commit all files
		git.commit()
				.setMessage(
						"removing changes : auto commit done by pre-flight utility @ :"
								+ DateUtil.timestampToStringifiedDate()).call();
	}

	public static void push(final File gitFolder) throws IOException {
		Git git = Git.open(gitFolder);
		int loopCounter = GitUtil.RETRY_ATTEMPTS;
		boolean hasPushBeenSuccess = false;
		do {
			try {
				LogUtil.log("pushing changes to remote git server");
				git.push().setPushAll().setRemote("origin")
						.setProgressMonitor(new GitTaskProgressMonitor())
						.call();
				hasPushBeenSuccess = true;
				break;
			} catch (GitAPIException ex) {
				LogUtil.log(ex);
				try {
					// wait before retrying
					Thread.sleep(Constants.RETRY_TIME);
					loopCounter--;
				} catch (InterruptedException exception) {
					throw new RuntimeException(exception);
				}
			}
		} while (loopCounter > 0);
		if (!hasPushBeenSuccess) {
			throw new AssertionError("push has been unsuccessful even after :"
					+ Constants.RETRY_COUNT + " attempts");
		}
	}

	private static String getOrigin(File gitFolder) throws IOException {
		String origin = StringUtils.EMPTY;
		Repository repository = new RepositoryBuilder().findGitDir(gitFolder)
				.build();
		Config config = repository.getConfig();
		Set<String> remotes = config.getSubsections("remote");
		for (String remoteName : remotes) {
			origin = config.getString("remote", remoteName, "url");
		}

		return origin;
	}

}
