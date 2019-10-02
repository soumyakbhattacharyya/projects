package hudson.plugins;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.model.Descriptor;
import hudson.plugins.constant.Cloud360PluginConstants;
import hudson.slaves.RetentionStrategy;
import hudson.util.TimeUnit2;

public class Cloud360RetentionStrategy extends RetentionStrategy<Cloud360Computer>{
	
	private static final Logger LOGGER = Logger.getLogger(Cloud360RetentionStrategy.class.getName());
	
	public static boolean disabled = Boolean.getBoolean(Cloud360RetentionStrategy.class.getName()+".disabled");
	
	@DataBoundConstructor
	public Cloud360RetentionStrategy() {
		
	}

	/**
     * Checking the retention policy for a computer
     * Policy as below:
     * 	 If a VM is idle and not a JIT or Third party then starts Retention Strategy
     * 
     * Retention Strategy :: Pooled VM will be retained till the specified Pooled time from start time of that VM 
     *  and if that VM is in idle state.
     */
	@Override
	public long check(Cloud360Computer c) {
		LOGGER.info("Retention Strategy for VM Started.");

		// Consider only pooled VMs, not JIT or 24X7 types
        if (c.isIdle() && !disabled && !c.getNode().isExistingInstance && !StringUtils.contains(c.getNode().getLabelString(), Cloud360PluginConstants.JIT)) {
        	LOGGER.info("VM is a POOLED instance.");
        	// TODO: really think about the right strategy here
        	
            //LOGGER.info("VM has been idle for :: "+idleMilliseconds+" millisec ");
            int retentionTime = Integer.parseInt(System.getProperty("POOLED_VM_TIME"));
            LOGGER.info("VM has been started for "+c.getUptime() + " millisecs");
            LOGGER.info("Retention time provided by Jenkins Admin :: "+ TimeUnit2.MINUTES.toMillis(retentionTime));
            /**
             * Retention Strategy :: Pooled VM will be retained till the specified Pooled time from start time of that VM 
             *  and if that VM in idle state.
             * 
             */
            if (c.getUptime() > TimeUnit2.MINUTES.toMillis(retentionTime) && c.getIdleStartMilliseconds()> 0) {
                LOGGER.info("Disconnecting "+c.getName());
                try {
                	LOGGER.info("Retention details of the slave.");
                	LOGGER.info("Instance Id :: "+c.getNode().getInstanceId());
					c.getNode().terminateInstance(c.getNode().getInstanceId());
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
            }
        }
        return 1;
	}

	@Override
    public void start(Cloud360Computer c) {
        try{
        	c.connect(false);
        }catch(Exception e){
        	LOGGER.severe("Exception while connecting to slave "+c.getName()+"as "+e.getMessage());
        }
    }

    // no registration since this retention strategy is used only for EC2 nodes that we provision automatically.
    // @Extension
    public static class DescriptorImpl extends Descriptor<RetentionStrategy<?>> {
        public String getDisplayName() {
            return "Cloud360";
        }
    }

}
