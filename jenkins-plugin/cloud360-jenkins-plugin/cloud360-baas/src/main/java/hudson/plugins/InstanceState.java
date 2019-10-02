package hudson.plugins;

/**
 * Constants that represent the running state of EC2. 
 *
 * @author Kohsuke Kawaguchi
 */
public enum InstanceState {
    PENDING,
    PAUSED,
    RUNNING,
    SHUTTING_DOWN,
    TERMINATED,
    DELETED,
    POWERED_ON,
    POWERED_OFF,
    PARKED,
    SUSPENDED;

    public String getCode() {
        return name().toLowerCase().replace('_','-');
    }

    public static InstanceState find(String name) {
        return Enum.valueOf(InstanceState.class,name.toUpperCase().replace('-','_'));
    }
}
