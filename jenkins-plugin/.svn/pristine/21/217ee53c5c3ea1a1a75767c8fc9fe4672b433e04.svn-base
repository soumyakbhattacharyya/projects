/**
 * 
 */
package hudson.plugins.projectstats.data;

import hudson.Util;
import hudson.plugins.projectstats.NumBuildsStats;
import hudson.plugins.projectstats.resource.ServiceLocator;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cognizant.buildservcore.datafederation.ProjectInfrastructureUsage;
import com.cognizant.buildservcore.datafederation.ProjectJob;

/**
 * @author 239913 This is a placeholder for the data collector API which will
 *         reflect BaaS usage detail to the Project people
 */
public final class DataCollector {

	final static Logger LOGGER = Logger.getLogger(DataCollector.class);
	private static DataCollector me = new DataCollector();

	public static DataCollector getInstance() {
		return me;
	}

	public final void getJobTxn(int projectId, String jobName,
			NumBuildsStats stat) {

		LOGGER.debug("Inside getJobTxn projectId " + projectId);
		LOGGER.info("Inside getJobTxn jobName " + jobName);

		String dataFederationURL = ServiceLocator.getInstance()
				.getDataFederationURL();
		LOGGER.debug("In plugin datafederation URL " + dataFederationURL);

		try {
			ProjectInfrastructureUsage usage = ProjectStrategyUsageCollector
					.getProjectUsageDetails();
			/*
			 * ProjectInfrastructureUsage usage = dataFederationSupport
			 * .getProjectUsage(new Long(projectId));
			 */
			LOGGER.info("Jit Builder " + usage.getJitBuildJobList());
			LOGGER.info("pooled list " + usage.getPooledBuildJobList());
			LOGGER.info("Third party list " + usage.getThirdPartyJobList());
			loadStatData(usage, stat, jobName);

		} catch (Exception e) {
			LOGGER.error("Error " + e.getMessage());
			//e.printStackTrace();
		}

		// Initialize
		/*
		 * String sql = QueryConstant.QUERY_BAAS_BUILD_LOG; PreparedStatement
		 * prest = null; ResultSet rs = null; Connection con = null; try { con =
		 * ServiceLocator.getInstance().getConnection(); prest =
		 * parameterizeQuery(projectId, jobName, con, sql); loadStatData(rs,
		 * prest, stat); } catch (SQLException e) { e.printStackTrace(); } catch
		 * (ClassNotFoundException e) { e.printStackTrace(); } finally { if
		 * (prest != null) try { prest.close(); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } if (rs !=
		 * null) try { rs.close(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */

	}

	/**
	 * Gets the result from the Datafederation ws call and populates the
	 * statistics
	 * 
	 * @param usage
	 * @param stat
	 * @throws SQLException
	 */
	private void loadStatData(ProjectInfrastructureUsage usage,
			NumBuildsStats stat, String jobName) {

		if (null != usage) {
			if (usage.getJitBuildJobList().size() > 0) {
				long totalTimeTaken = 0;
				long totalJobExecuted = 0;
				for (ProjectJob job : usage.getJitBuildJobList()) {
					if (job.getJobName().equals(jobName)) {
						totalTimeTaken = totalTimeTaken
								+ job.getTotalTimeSpent();
						totalJobExecuted = totalJobExecuted
								+ job.getTotalNumberofBuild();

					}
				}
				stat.setTimeUsedOnDemand(Util.getTimeSpanString(totalTimeTaken));
				stat.setChargeComputedOnDemand(toString(computeCharge(totalTimeTaken)));
				stat.setTotalNumberOfBuildOnDemand(String
						.valueOf(totalJobExecuted));
			}
			if (usage.getPooledBuildJobList().size() > 0) {
				long totalTimeTaken = 0;
				long totalJobExecuted = 0;
				for (ProjectJob job : usage.getPooledBuildJobList()) {
					if (job.getJobName().equals(jobName)) {
						totalTimeTaken = totalTimeTaken
								+ job.getTotalTimeSpent();
						totalJobExecuted = totalJobExecuted
								+ job.getTotalNumberofBuild();

					}
				}
				stat.setTimeUsedOnPooled(Util.getTimeSpanString(totalTimeTaken));
				stat.setChargeComputedOnPooled(toString(computeCharge(totalTimeTaken)));
				stat.setTotalNumberOfBuildOnPooled(String
						.valueOf(totalJobExecuted));
			}
			if (usage.getThirdPartyJobList().size() > 0) {
				long totalTimeTaken = 0;
				long totalJobExecuted = 0;
				for (ProjectJob job : usage.getThirdPartyJobList()) {
					if (job.getJobName().equals(jobName)) {
						totalTimeTaken = totalTimeTaken
								+ job.getTotalTimeSpent();
						totalJobExecuted = totalJobExecuted
								+ job.getTotalNumberofBuild();

					}
				}
				stat.setTimeUsedOnThirdParty(Util
						.getTimeSpanString(totalTimeTaken));
				stat.setChargeComputedOnThirdParty(toString(computeCharge(totalTimeTaken)));
				stat.setTotalNumberOfBuildOnThirdParty(String
						.valueOf(totalJobExecuted));
			}
			if (usage.getTotalValues().size() > 0) {
				for (ProjectJob job : usage.getTotalValues()) {
					if (job.getJobName().equals(jobName)) {
						stat.setTimeUsedTotal(Util.getTimeSpanString(job
								.getTotalTimeSpent()));
						stat.setChargeComputedTotal(toString(computeCharge(job
								.getTotalTimeSpent())));
						stat.setTotalNumberOfBuildTotal(String.valueOf(job
								.getTotalNumberofBuild()));
					}
				}
			}
		}
	}
	
	
	/*
	 * private void loadStatData(ResultSet rs, PreparedStatement prest,
	 * NumBuildsStats stat) throws SQLException { if (!ObjectUtils.equals(prest,
	 * null) && !ObjectUtils.equals(stat, null)) { rs = prest.executeQuery();
	 * String label = ""; while (rs.next()) { label = rs.getString(1); if
	 * (StringUtils.equals(label, QueryConstant.ON_DEMAND)) {
	 * stat.setTimeUsedOnDemand(Util.getTimeSpanString(rs .getLong(2)));
	 * stat.setTotalNumberOfBuildOnDemand(String.valueOf(rs .getInt(3)));
	 * stat.setChargeComputedOnDemand(toString(computeCharge(rs .getLong(2))));
	 * } if (StringUtils.equals(label, QueryConstant.ON_POOLED)) {
	 * stat.setTimeUsedOnPooled(Util.getTimeSpanString(rs .getLong(2)));
	 * stat.setTotalNumberOfBuildOnPooled(String.valueOf(rs .getInt(3)));
	 * stat.setChargeComputedOnPooled(toString(computeCharge(rs .getLong(2))));
	 * } if (StringUtils.equals(label, QueryConstant.ON_THIRD_PARTY)) {
	 * stat.setTimeUsedOnThirdParty(Util.getTimeSpanString(rs .getLong(2)));
	 * stat.setTotalNumberOfBuildOnThirdParty(String.valueOf(rs .getInt(3)));
	 * stat.setChargeComputedOnThirdParty(toString(computeCharge(rs
	 * .getLong(2)))); } if (StringUtils.equals(label, QueryConstant.TOTAL)) {
	 * stat.setTimeUsedTotal(Util.getTimeSpanString(rs.getLong(2)));
	 * stat.setTotalNumberOfBuildTotal(String.valueOf(rs.getInt(3)));
	 * stat.setChargeComputedTotal(toString(computeCharge(rs .getLong(2)))); } }
	 * 
	 * } }
	 */

	/*private PreparedStatement parameterizeQuery(int projectId, String jobName,
			Connection con, String sql) throws SQLException {
		if (projectId != 0 && StringUtils.isNotBlank(jobName)
				&& !ObjectUtils.equals(con, null)
				&& StringUtils.isNotBlank(sql)) {
			PreparedStatement prest = con.prepareStatement(sql); //NOPMD not being used
			prest.setInt(1, projectId);
			prest.setString(2, jobName);
			prest.setInt(3, projectId);
			prest.setString(4, jobName);
			prest.setInt(5, projectId);
			prest.setString(6, jobName);
			prest.setInt(7, projectId);
			prest.setString(8, jobName);
			return prest;
		} else
			return null;

	}
	*/
	/**
	 * String representation of the charge computed to be used by UI
	 * 
	 * @param computedCharge
	 * @return
	 */
	public static String toString(float computedCharge) {
		return String.valueOf(computedCharge);
	}

	/**
	 * This is a dummy charge computation block
	 * 
	 * @param period
	 * @return
	 */
	public static float computeCharge(long period) {
		if (period != 0)
			return Math.round(period * 0.00001f);
		else
			return 0;
	}

}
