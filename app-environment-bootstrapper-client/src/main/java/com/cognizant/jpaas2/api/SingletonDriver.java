package com.cognizant.jpaas2.api;

import com.cognizant.formit.util.file.FileHelper;
import com.cognizant.jpaas2.api.command.Command;
import com.cognizant.jpaas2.api.command.CommandFactory;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.persistence.HibernateUtil;
import com.cognizant.jpaas2.api.persistence.factory.DAOFactory;
import com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceDAO;
import com.pholser.util.properties.PropertyBinder;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.hibernate.Session;


/**
 * driver program, one / JVM
 *
 * TODO : implement producer - consumer paradigm as per following http://dhruba.name/2011/05/21/the-producer-consumer-pattern-in-java/ until
 * then keep synchronized qualifier for the time being
 *
 */
public final class SingletonDriver {

	private static SingletonDriver INSTANCE;
	// request queue
	private final Queue<Command> pipeline;
	// driving properties
	private final ConstantProperty clientProperties;

	public synchronized ConstantProperty getClientProperties() {
		return clientProperties;
	}

	public synchronized Queue<Command> getPipeline() {
		return pipeline;
	}

	// instantiate request queue
	private SingletonDriver() throws IOException {

		pipeline = new ConcurrentLinkedQueue<Command>();
		PropertyBinder<ConstantProperty> binder = PropertyBinder.forType(ConstantProperty.class);
		clientProperties = binder.bind(FileHelper.getFile(getCONFIG_FILE_PATH()+"properties/app-environment-bootstrapper.properties"));

	}
	
	private static final String CONFIG_FILE_PATH = System.getProperty("CONFIG_FILE_PATH");

	public static String getCONFIG_FILE_PATH() {		
		return System.getProperty("CONFIG_FILE_PATH");
	}
	
	

	/**
	 * get the singleton instance
	 *
	 * @return
	 */
	public static synchronized SingletonDriver getInstance() {
		if (INSTANCE == null) {
			try {
				INSTANCE = new SingletonDriver();
			} catch (IOException ex) {
				ex.printStackTrace();
				// throw the exception by wrapping it into runtime exception
				// as you can not do much after this
				throw new RuntimeException(ex);
			}
		}
		return INSTANCE;
	}

	/**
	 * find launch request from DB which are parked in SCHEDULED status
	 *
	 * @return list of scheduled launches
	 */
	public synchronized List<AppEnvInstance> findScheduledLaunches() {
		// begin transaction
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		// grab dao
		DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
		AppEnvInstanceDAO appEnvInstanceDAO = factory.getAppEnvInstanceDAO(session);
		// get result
		List<AppEnvInstance> result = appEnvInstanceDAO.getScheduledBuilds(AppEnvInstance.ExecutionStatus.SCHEDULED.toString());
		session.getTransaction().commit();
		//HibernateUtil.getCurrentSession().getTransaction().commit();
		return result;
	}
	
    /**
	 * finds scheduled termination requests
	 * @return 
	 */
	public synchronized List<AppEnvInstance> findScheduledTerminations() {
		// begin transaction
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		// grab dao
		DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
		AppEnvInstanceDAO appEnvInstanceDAO = factory.getAppEnvInstanceDAO(session);
		// get result
		List<AppEnvInstance> result = appEnvInstanceDAO.getScheduledBuilds(AppEnvInstance.ExecutionStatus.TORNDOWN.toString());
		session.getTransaction().commit();
		//HibernateUtil.getCurrentSession().getTransaction().commit();
		return result;
	}

	/**
	 * place launch requests to the queue
	 *
	 * @param scheduledBuilds scheduled builds
	 * @return if enqueue is a success
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public synchronized boolean enQueueLaunchRequests(List<AppEnvInstance> scheduledBuilds) throws InstantiationException, IllegalAccessException {

		boolean isSuccess = true;

		SingletonDriver singletonDriver = SingletonDriver.getInstance();
		// place all pending requests inside the queue
		for (AppEnvInstance appEnvInstance : scheduledBuilds) {
			Command launchCommand = CommandFactory.LAUNCH.newCommand(appEnvInstance);
			launchCommand.setAppEnvInstance(appEnvInstance);
			// submit launch execution to the executor service
			boolean enqueueStatus = singletonDriver.getPipeline().add(launchCommand);
			isSuccess = isSuccess & enqueueStatus;
		}

		return isSuccess;
	}
	
	/**
	 * place termination requests to the queue
	 * 
	 * @param scheduledBuilds
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 */
	public synchronized boolean enQueueTerminationRequests(List<AppEnvInstance> scheduledTerminations) throws InstantiationException, IllegalAccessException {

		boolean isSuccess = true;

		SingletonDriver singletonDriver = SingletonDriver.getInstance();
		// place all pending requests inside the queue
		for (AppEnvInstance appEnvInstance : scheduledTerminations) {
			Command launchCommand = CommandFactory.TEAR_DOWN.newCommand(appEnvInstance);
			launchCommand.setAppEnvInstance(appEnvInstance);
			// submit launch execution to the executor service
			boolean enqueueStatus = singletonDriver.getPipeline().add(launchCommand);
			isSuccess = isSuccess & enqueueStatus;
		}

		return isSuccess;
	}

	/**
	 * start processing launch request in blocking fashion, sequentially
	 */
	public synchronized void process() {

		SingletonDriver singletonDriver = SingletonDriver.getInstance();
		// process all requests in FIFO fashion
		while (singletonDriver.getPipeline().peek() != null) {
			Command command = singletonDriver.getPipeline().poll();
			command.execute();
		}

	}

	public static void main(String[] args) {

		Properties properties = System.getProperties();
		String absolutePath = getInstance().getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		System.out.println(absolutePath);

	}

	
}
