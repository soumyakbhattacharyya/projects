package hudson.plugins.ec2;

import hudson.model.Descriptor;
import hudson.plugins.ec2.resource.ServiceLocator;
import hudson.slaves.RetentionStrategy;
import hudson.util.RunList;
import hudson.util.TimeUnit2;

import java.util.logging.Logger;

import jenkins.model.Jenkins;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * {@link RetentionStrategy} for EC2.
 *
 * @author Kohsuke Kawaguchi
 */
public class EC2RetentionStrategy extends RetentionStrategy<EC2Computer> {
    @DataBoundConstructor
    public EC2RetentionStrategy() {
    }

    /**
     * Checking the retention policy for a computer
     * Policy as below:
     * 	 If a VM is idle and not a JIT or Third party then starts Retention Strategy
     * 
     * Retention Strategy :: Pooled VM will be retained till the specified Pooled time from start time of that VM 
     *  and if that VM is in idle state.
     */
    public synchronized long check(EC2Computer c) {
    	LOGGER.info("Retention Strategy for VM Started.");
    	//LOGGER.info(""+c.isIdle() + ""+disabled +""+c.getNode().isExistingInstance);
    	// Consider only pooled VMs, not JIT or 24X7 types
    	if (StringUtils.contains(c.getNode().getLabelString(),
    			EC2Constant.JIT)) {

    		
    		LOGGER.info("c.getBuilds() details "+c.getBuilds());
    		LOGGER.info("c.getBuilds() is empty details "+c.getBuilds().isEmpty());
    		LOGGER.info("c.isConnecting() is empty details "+c.isConnecting());
    		LOGGER.info("c.getIdleStartMilliseconds() "+c.getIdleStartMilliseconds());
    		LOGGER.info("buildable item "+Jenkins.getInstance().getQueue().getBuildableItems(c));
    		LOGGER.info("number of busy executor "+c.countBusy());
    		LOGGER.info("is idle "+c.isIdle());
    		
			if (Jenkins.getInstance().getQueue().getBuildableItems(c).isEmpty() && c.isIdle() && !c.isConnecting() && c.getIdleStartMilliseconds()> 0
					&& c.countBusy() == 0) {
				
				
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
    	else if (c.isIdle() && !disabled && !c.getNode().isExistingInstance && !StringUtils.contains(c.getNode().getLabelString(), EC2Constant.JIT)) {
        	LOGGER.info("VM is a POOLED instance.");

           // final long idleMilliseconds = System.currentTimeMillis() - c.getIdleStartMilliseconds();
            //LOGGER.info("VM has been idle for :: "+idleMilliseconds+" millisec ");
        	int retentionTime = 180;
        	try{
        		retentionTime = Integer.parseInt(System.getProperty("POOLED_VM_TIME"));
        	}catch(Exception e){
        		String time = ServiceLocator.getInstance().getEc2PooledTime();
        		if(time!= null && !time.isEmpty()){
        			retentionTime = Integer.parseInt(time);
        		}
        	}
            
            
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
    public void start(EC2Computer c) {
        c.connect(false);
    }

    // no registration since this retention strategy is used only for EC2 nodes that we provision automatically.
    // @Extension
    public static class DescriptorImpl extends Descriptor<RetentionStrategy<?>> {
        public String getDisplayName() {
            return "EC2";
        }
    }

    private static final Logger LOGGER = Logger.getLogger(EC2RetentionStrategy.class.getName());

    public static boolean disabled = Boolean.getBoolean(EC2RetentionStrategy.class.getName()+".disabled");
}
