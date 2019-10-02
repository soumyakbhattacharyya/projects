package hudson.plugins.emailext.plugins.content;

import hudson.Functions;
import hudson.model.Action;
import hudson.model.TopLevelItem;
import hudson.model.AbstractBuild;
import hudson.model.Cause;
import hudson.model.Cause.UpstreamCause;
import hudson.model.Job;
import hudson.model.Run;
import hudson.tasks.junit.TestResult;
import hudson.tasks.junit.TestResultAction;
import hudson.tasks.test.AggregatedTestResultAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


import jenkins.model.Jenkins;

public class ScriptContentBuildWrapper {

    private AbstractBuild<?, ?> build;
    private static final Logger log = Logger.getLogger(ScriptContentBuildWrapper.class.getName());

    public ScriptContentBuildWrapper(AbstractBuild<?, ?> build) {
        this.build = build;
    }

    public String getTimestampString() {
        return Functions.rfc822Date(build.getTimestamp());
    }

    public Action getAction(String className) {
        for (Action a : build.getActions()) {
            if (a.getClass().getName().equals(className)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Returns all build actions that derive from
     * <code>AbstractResultAction</code>. Every action represents a single
     * analysis result.
     * 
     * @return The static analysis actions for the current build. The returned
     *         list might be empty if there are no such actions.
     */
    public List<Action> getStaticAnalysisActions() {
        if (isPluginInstalled("analysis-core")) {
            return new StaticAnalysisUtilities().getActions(build);
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * Returns whether the specified plug-in is installed.
     *
     * @param shortName
     *            the plug-in to check
     * @return <code>true</code> if the specified plug-in is installed,
     *         <code>false</code> if not.
     */
    public static boolean isPluginInstalled(final String shortName) {
        Jenkins instance = Jenkins.getInstance();
        if (instance != null) {
            return instance.getPlugin(shortName) != null;
        }
        return true;
    }

    public Action getCoberturaAction() {
        return getAction("hudson.plugins.cobertura.CoberturaBuildAction");
    }

 public List<AbstractBuild> getDetailsOfAllParentBuild(AbstractBuild build) {
    	
    	List<AbstractBuild> parentAbstractBuildList = new ArrayList<AbstractBuild>();
    	List<Cause> causesList = build.getCauses();
    	log.info("build details is "+build.getDisplayName());
    	log.info("causesList is "+causesList);
    	
    	for(Cause cause:causesList){
			if(cause instanceof UpstreamCause){
				log.info("Upstream project found as "+cause.getShortDescription());    				
				UpstreamCause upstreamCause = (UpstreamCause)cause;
				TopLevelItem item = hudson.model.Hudson.getInstance().getItem(upstreamCause.getUpstreamProject());
				if(item instanceof Job){
					Job upStreamJob = (Job)item;
					Run upStreamRun = upStreamJob.getBuildByNumber(upstreamCause.getUpstreamBuild());
					if(upStreamRun!= null && upStreamRun instanceof AbstractBuild){
						AbstractBuild upStreamBuild = (AbstractBuild)upStreamRun;
						log.info("The parent upStreamBuild found as "+upStreamBuild.getDisplayName());
						parentAbstractBuildList.add(upStreamBuild);						
						if(upStreamBuild.getCauses()!= null && upStreamBuild.getCauses().size()>0){
							parentAbstractBuildList.addAll(getDetailsOfAllParentBuild(upStreamBuild));
						}else{
							break;
						}
					}
				}
			}			
    	}
    	
    	return parentAbstractBuildList;
    }
    
    
    
    public List<TestResult> getJUnitTestResult() {
        List<TestResult> result = new ArrayList<TestResult>();
        List<Action> actions = build.getActions();
        for (Action action : actions) {
            if (action instanceof hudson.tasks.test.AggregatedTestResultAction) {
                /* Maven Project */
                List<AggregatedTestResultAction.ChildReport> reportList =
                        ((AggregatedTestResultAction) action).getChildReports();
                for (AggregatedTestResultAction.ChildReport report : reportList) {
                    if (report.result instanceof hudson.tasks.junit.TestResult) {
                        result.add((TestResult) report.result);
                    }
                }
            }
        }

        if (result.isEmpty()) {
            /*FreestyleProject*/
            TestResultAction action = build.getAction(TestResultAction.class);
            if (action != null) {
                result.add(action.getResult());
            }
        }
        return result;
    }
}
