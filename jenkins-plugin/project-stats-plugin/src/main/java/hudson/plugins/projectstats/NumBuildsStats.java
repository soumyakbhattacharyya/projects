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

package hudson.plugins.projectstats;

import hudson.Util;
import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;
import hudson.plugins.projectstats.data.DataCollector;
import hudson.plugins.projectstats.portlet.NumBuildsPortlet;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.kohsuke.stapler.Stapler;

/**
 * 
 * @author Marco Ambu
 */
public class NumBuildsStats implements Stats {

	int success;
	int unstable;
	int fail;

	String timeUsedOnDemand;
	String chargeComputedOnDemand;
	String totalNumberOfBuildOnDemand;
	
	
	String timeUsedOnPooled;
	String chargeComputedOnPooled;
	String totalNumberOfBuildOnPooled;
	
	
	String timeUsedOnThirdParty;
	String chargeComputedOnThirdParty;;
	String totalNumberOfBuildOnThirdParty;;
	
	
	String timeUsedTotal;
	String chargeComputedTotal;
	String totalNumberOfBuildTotal;
	
	final static Logger LOGGER = Logger.getLogger(NumBuildsPortlet.class);

	public String getTimeUsedOnDemand() {
		return timeUsedOnDemand;
	}

	public void setTimeUsedOnDemand(String timeUsedOnDemand) {
		this.timeUsedOnDemand = timeUsedOnDemand;
	}

	public String getChargeComputedOnDemand() {
		return chargeComputedOnDemand;
	}

	public void setChargeComputedOnDemand(String chargeComputedOnDemand) {
		this.chargeComputedOnDemand = chargeComputedOnDemand;
	}
	

	public String getTotalNumberOfBuildOnDemand() {
		return totalNumberOfBuildOnDemand;
	}

	public void setTotalNumberOfBuildOnDemand(String totalNumberOfBuildOnDemand) {
		this.totalNumberOfBuildOnDemand = totalNumberOfBuildOnDemand;
	}

	public String getTimeUsedOnPooled() {
		return timeUsedOnPooled;
	}

	public void setTimeUsedOnPooled(String timeUsedOnPooled) {
		this.timeUsedOnPooled = timeUsedOnPooled;
	}

	public String getChargeComputedOnPooled() {
		return chargeComputedOnPooled;
	}

	public void setChargeComputedOnPooled(String chargeComputedOnPooled) {
		this.chargeComputedOnPooled = chargeComputedOnPooled;
	}

	public String getTotalNumberOfBuildOnPooled() {
		return totalNumberOfBuildOnPooled;
	}

	public void setTotalNumberOfBuildOnPooled(String totalNumberOfBuildOnPooled) {
		this.totalNumberOfBuildOnPooled = totalNumberOfBuildOnPooled;
	}

	public String getTimeUsedOnThirdParty() {
		return timeUsedOnThirdParty;
	}

	public void setTimeUsedOnThirdParty(String timeUsedOnThirdParty) {
		this.timeUsedOnThirdParty = timeUsedOnThirdParty;
	}

	public String getChargeComputedOnThirdParty() {
		return chargeComputedOnThirdParty;
	}

	public void setChargeComputedOnThirdParty(String chargeComputedOnThirdParty) {
		this.chargeComputedOnThirdParty = chargeComputedOnThirdParty;
	}

	public String getTotalNumberOfBuildOnThirdParty() {
		return totalNumberOfBuildOnThirdParty;
	}

	public void setTotalNumberOfBuildOnThirdParty(
			String totalNumberOfBuildOnThirdParty) {
		this.totalNumberOfBuildOnThirdParty = totalNumberOfBuildOnThirdParty;
	}

	public String getTimeUsedTotal() {
		return timeUsedTotal;
	}

	public void setTimeUsedTotal(String timeUsedTotal) {
		this.timeUsedTotal = timeUsedTotal;
	}

	public String getChargeComputedTotal() {
		return chargeComputedTotal;
	}

	public void setChargeComputedTotal(String chargeComputedTotal) {
		this.chargeComputedTotal = chargeComputedTotal;
	}

	public String getTotalNumberOfBuildTotal() {
		return totalNumberOfBuildTotal;
	}

	public void setTotalNumberOfBuildTotal(String totalNumberOfBuildTotal) {
		this.totalNumberOfBuildTotal = totalNumberOfBuildTotal;
	}

	public NumBuildsStats() {
		this.success = 0;
		this.unstable = 0;
		this.fail = 0;
	}

	public void compute(Job job) {
		List<Run> builds = job.getBuilds();
		for (Run build : builds) {
			if(!build.isBuilding()){
			if (build.getResult().isBetterOrEqualTo(Result.SUCCESS))
				addSuccess();
			else if (build.getResult().isBetterOrEqualTo(Result.UNSTABLE))
				addUnstable();
			else if (build.getResult().isBetterOrEqualTo(Result.FAILURE))
				addFail();
			}
		}
		analyzeJPaaSFeed(job);
	}

	private void analyzeJPaaSFeed(Job job) {
		String projectId = String.valueOf(Stapler.getCurrentRequest().getSession().getAttribute("projectId"));	
		LOGGER.debug("*****************|||||||Before calling datacollector||||||************************");
		DataCollector.getInstance().getJobTxn(NumberUtils.toInt(projectId), job.getName(), this);
	}

	public void addSuccess() {
		success++;
	}

	public String getTotalNumberOfBuild() {
		return String.valueOf(this.success + this.unstable + this.fail);
	}

	public void addUnstable() {
		unstable++;
	}

	public void addFail() {
		fail++;
	}

	public int getSuccess() {
		return success;
	}

	public int getUnstable() {
		return unstable;
	}

	public int getFail() {
		return fail;
	}

	public double getSuccessPct() {
		int total = success + unstable + fail;
		return total != 0 ? ((double) success / total) : 0d;
	}

	public double getUnstablePct() {
		int total = success + unstable + fail;
		return total != 0 ? ((double) unstable / total) : 0d;
	}

	public double getFailPct() {
		int total = success + unstable + fail;
		return total != 0 ? ((double) fail / total) : 0d;
	}

}
