package hudson.plugins.cloud;

import hudson.model.Descriptor;
import hudson.slaves.RetentionStrategy;
import hudson.util.TimeUnit2;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * {@link RetentionStrategy} for EC2.
 *
 * @author Kohsuke Kawaguchi
 */
public class VMwareRetentionStrategy extends RetentionStrategy<VMwareComputer> {
    @DataBoundConstructor
    public VMwareRetentionStrategy() {
    }

    /**
     * Checking the retention policy for a computer
     * Policy as below:
     * 	 If a VM is idle and not a JIT or Third party then starts Retention Strategy
     * 
     * Retention Strategy :: Pooled VM will be retained till the specified Pooled time from start time of that VM 
     *  and if that VM is in idle state.
     */
    public synchronized long check(VMwareComputer c) {
    	LOGGER.info("Retention Strategy for VM Started.");
    	// Terminate the Machines whiich are with label JIT
        /*if(StringUtils.contains(c.getNode().getLabelString(), VMwareConstant.JIT) && c.getBuilds().isEmpty()){
        	try {
            	LOGGER.info("Retention details of the slave.");
            	LOGGER.info("Instance Id :: "+c.getNode().getInstanceId()+" Account Id :: "+c.getNode().accountNumber+" User Id :: "+ c.getNode().userId);
				c.getNode().terminateInstance(c.getNode().getInstanceId(), 
						c.getNode().getProjectUserID(),c.getNode().getProjectID(),new String[]{c.getNode().accountNumber,c.getNode().userId});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
        }*/
        // Consider only pooled VMs, not JIT or 24X7 types
		if (StringUtils.contains(c.getNode().getLabelString(),
				VMwareConstant.JIT)) {

			if (c.getBuilds().isEmpty() && c.isIdle() && !c.isConnecting() && c.getIdleStartMilliseconds()> 0) {
				LOGGER.info("*************************************************************************************");
				try {
					LOGGER.info("Retention details of the slave.");
					LOGGER.info("Instance Id :: " + c.getNode().getInstanceId()
							+ " Account Id :: " + c.getNode().accountNumber
							+ " User Id :: " + c.getNode().userId);
					c.getNode().terminateInstance(
							c.getNode().getInstanceId(),
							c.getNode().getProjectUserID(),
							c.getNode().getProjectID(),
							new String[] { c.getNode().accountNumber,
									c.getNode().userId });
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				LOGGER.info("*************************************************************************************");
			} else {
				LOGGER.info("---------------------------------------------------------------------------------");
				LOGGER.info("---------------------Not Deleting JIT since its being used -----------------------------");
				LOGGER.info("---------------------------------------------------------------------------------");
			}
		}
    	
    	else if (c.isIdle() && !disabled && !c.getNode().isExistingInstance && !StringUtils.contains(c.getNode().getLabelString(), VMwareConstant.JIT)) {
        	LOGGER.info("VM is a POOLED instance.");
        	// TODO: really think about the right strategy here
           // final long idleMilliseconds = System.currentTimeMillis() - c.getIdleStartMilliseconds();
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
                	LOGGER.info("Instance Id :: "+c.getNode().getInstanceId()+" Account Id :: "+c.getNode().accountNumber+" User Id :: "+ c.getNode().userId);
					c.getNode().terminateInstance(c.getNode().getInstanceId(), 
							c.getNode().getProjectUserID(),c.getNode().getProjectID(),new String[]{c.getNode().accountNumber,c.getNode().userId});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
            }
        }
        return 1;
    }

    /**
     * Try to connect to it ASAP.
     */
    @Override
    public void start(VMwareComputer c) {
        c.connect(false);
    }

    // no registration since this retention strategy is used only for EC2 nodes that we provision automatically.
    // @Extension
    public static class DescriptorImpl extends Descriptor<RetentionStrategy<?>> {
        public String getDisplayName() {
            return "EC2";
        }
    }

    private static final Logger LOGGER = Logger.getLogger(VMwareRetentionStrategy.class.getName());

    public static boolean disabled = Boolean.getBoolean(VMwareRetentionStrategy.class.getName()+".disabled");
}
