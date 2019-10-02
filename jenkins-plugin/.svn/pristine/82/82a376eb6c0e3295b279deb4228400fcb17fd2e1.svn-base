package hudson.plugins.cloud;

import hudson.model.TaskListener;
import hudson.slaves.ComputerLauncher;
import hudson.slaves.SlaveComputer;

import java.io.IOException;
import java.io.PrintStream;

import org.jets3t.service.S3ServiceException;

import com.cognizant.jpaas2.resource.VirtualMachineType;

/**
 * {@link ComputerLauncher} for VMware that waits for the instance to really come up before proceeding to
 * the real user-specified {@link ComputerLauncher}.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class VMwareComputerLauncher extends ComputerLauncher {
    @Override
    public void launch(SlaveComputer _computer, TaskListener listener) {
        try {
            VMwareComputer computer = (VMwareComputer)_computer;
            
            PrintStream logger = listener.getLogger();

            OUTER:
            while(true) {
                switch (computer.getState()) {
                    case PENDING:
                        Thread.sleep(5000); // check every 5 secs
                        continue OUTER;
                    case RUNNING:
                        break OUTER;
                    case SHUTTING_DOWN:
                    case TERMINATED:
                        // abort
                        logger.println("The instance "+computer.getInstanceId()+" appears to be shutting down. Aborting launch.");
                        return;
                }
            }

            launch(computer, logger, computer.describeInstance());
        } catch (IOException e) {
            e.printStackTrace(listener.error(e.getMessage()));
        } catch (InterruptedException e) {
            e.printStackTrace(listener.error(e.getMessage()));
        } catch (S3ServiceException e) {
            e.printStackTrace(listener.error(e.getMessage()));
        }

    }

    /**
     * Stage 2 of the launch. Called after the VMware instance comes up.
     */
    protected abstract void launch(VMwareComputer computer, PrintStream logger, VirtualMachineType server)
            throws IOException, InterruptedException, S3ServiceException;
}