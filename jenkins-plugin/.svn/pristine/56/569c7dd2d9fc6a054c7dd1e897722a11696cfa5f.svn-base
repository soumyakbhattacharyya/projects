/*
 * The MIT License
 *
 * Copyright (c) 2011, Marco Ambu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package hudson.plugins.projectstats.portlet;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.plugins.projectstats.NumBuildsStats;
import hudson.plugins.view.dashboard.DashboardPortlet;

import java.text.DecimalFormat;
import java.util.ResourceBundle;

import java.util.logging.Logger;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * 
 * @author Marco Ambu
 */
public class NumBuildsPortlet extends DashboardPortlet {
	private static final ResourceBundle NUMBUILDSPORTLETS = ResourceBundle
			.getBundle("hudson/plugins/projectstats/portlet/NumBuildsPortlet/numbuildsportlet"); // NOPMD

	final static Logger LOGGER = Logger.getLogger(NumBuildsPortlet.class.getName());

	@DataBoundConstructor
	public NumBuildsPortlet(String name) {
		super(name);
	}

	public NumBuildsStats getStats(Job job) {
		LOGGER.info("######Inside NulBuildsColumn getStats#######");
		NumBuildsStats stats = new NumBuildsStats();
		stats.compute(job);
		return stats;
	}

	/*public static String getProjectID() {
		logger.info("Inside getProjectID ");
		String projectId = null;
		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			projectId = String.valueOf(Stapler.getCurrentRequest()
					.getSession().getAttribute("projectId"));
			logger.info("projectId is "+projectId);
		}
		return projectId;
	}*/

	/*
	 * public static ProjectInfrastructureUsage getProjectInfrastructureUsage()
	 * {
	 * 
	 * logger.info(
	 * "++++++++++++getProjectInfrastructureUsage from jelly++++++++++++++++++"
	 * ); ProjectInfrastructureUsage usage = null; String dataFederationURL =
	 * ServiceLocator.getInstance() .getDataFederationURL(); String projectId =
	 * String
	 * .valueOf(Stapler.getCurrentRequest().getSession().getAttribute("projectId"
	 * )); logger.debug("The project id is "+projectId);
	 * logger.debug("In plugin datafederation URL " + dataFederationURL);
	 * 
	 * DataFederationService baaslogService = BaaSBuildServiceRegistry
	 * .getRegistry().getDataFederationService(
	 * ServiceContext.getContext(dataFederationURL)); if
	 * (baaslogService.hasDataFederationSupport()) { DataFederationSupport
	 * dataFederationSupport = baaslogService .getDataFederationSupport();
	 * 
	 * try { usage = dataFederationSupport .getProjectUsage(new
	 * Long(projectId)); logger.info("Jit Builder " +
	 * usage.getJitBuildJobList()); logger.info("pooled list " +
	 * usage.getPooledBuildJobList()); logger.info("Third party list " +
	 * usage.getThirdPartyJobList());
	 * 
	 * } catch (JPaaSException e) { logger.error("Error " + e.getMessage());
	 * e.printStackTrace(); } } return usage; }
	 */

	public String formatPct(DecimalFormat df, double val) {
		if (val < 1d && val > .99d) {
			return "<100%";
		} else if (val > 0d && val < .01d) {
			return ">0%";
		} else {
			return df.format(val);
		}
	}

	@Extension
	public static class DescriptorImpl extends Descriptor<DashboardPortlet> {

		@Override
		public String getDisplayName() {
			return NUMBUILDSPORTLETS.getString("NumBuilsPortlet");
		}
	}

}
