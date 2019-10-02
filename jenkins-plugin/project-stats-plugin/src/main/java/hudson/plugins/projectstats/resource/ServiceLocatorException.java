package hudson.plugins.projectstats.resource;

public class ServiceLocatorException extends RuntimeException{

	public ServiceLocatorException(Exception ne) {
		super(ne);
		//ne.printStackTrace();		
	}

}
