package hudson.plugins.cloudset.rbac.util;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

public final class ACLCache<T> {

	/** Objects are stored here */
	private final Map<String, T> objects;
	/** Holds custom expiration dates */
	private final Map<String, Long> expire;
	/** The default expiration date */
	private final long defaultExpire;
	/** Is used to speed up some operations */
	private final ExecutorService threads;
	/** Singleton instance */
	private static final ACLCache me = spawn();

	private static String CACHETIME = "10";
	
	/**
	 * Get the singleton instance
	 */
	public static ACLCache getThis(String cacheTime) {
		CACHETIME = cacheTime;
		return me;
	}

	public Map<String, T> getObjects() {
		return objects;
	}

	/**
	 * Constructs the cache with a default expiration time for the objects of
	 * 100 seconds.
	 */
	private ACLCache() {
		this(100);
	}

	/**
	 * Instantiate ACLCache with custom CACHE_REFRESH_RATE that can be
	 * configured via -DCRR parameter served at runtime. if not specified it is
	 * defaulted to 15 Minute
	 * 
	 * @return
	 */
	private static ACLCache spawn() {
		return new ACLCache(getCacheRefreshPeriod());
	}

	/**
	 * Construct a cache with a custom expiration time for the objects.
	 * 
	 * @param defaultExpire
	 *            default expiration time in Minute 
	 */
	private ACLCache(final int defaultExpire) {
		this.objects = Collections.synchronizedMap(new HashMap<String, T>());
		this.expire = Collections.synchronizedMap(new HashMap<String, Long>());
		this.defaultExpire = defaultExpire;
		this.threads = Executors.newFixedThreadPool(256);		
	}

	
	/**
	 * Returns a runnable that removes a specific object from the cache.
	 * 
	 * @param name
	 *            the name of the object
	 */
	private final Runnable createRemoveRunnable(final String name) {
		return new Runnable() {
			public void run() {
				objects.remove(name);
				expire.remove(name);
			}
		};
	}

	/**
	 * Returns the default expiration time for the objects in the cache.
	 * 
	 * @return default expiration time in seconds
	 */
	public long getExpire() {
		return this.defaultExpire;
	}

	/**
	 * Put an object into the cache.
	 * 
	 * @param name
	 *            the object will be referenced with this name in the cache
	 * @param obj
	 *            the object
	 */
	public void put(final String name, final T obj) {
		this.put(name, obj, this.defaultExpire);
	}

	/**
	 * Put an object into the cache with a custom expiration date.
	 * 
	 * @param name
	 *            the object will be referenced with this name in the cache
	 * @param obj
	 *            the object
	 * @param expire
	 *            custom expiration time in seconds
	 */
	public void put(final String name, final T obj, final long expireTime) {
		this.objects.put(name, obj);
		this.expire.put(name, System.currentTimeMillis());
	}

	/**
	 * Returns an object from the cache. If time has expired schedule an executor service for 
	 * removing it in future. Otherwise return ASIS
	 * 
	 * @param name
	 *            the name of the object you'd like to get
	 * @param type
	 *            the type of the object you'd like to get
	 * @return the object for the given name and type
	 */
	public T get(final String name) {
		final Long expireTime = this.expire.get(name);
		int cachePushTime = 0;
		int now = 0;
		if (expireTime == null) {
			return null;
		}
		now = Integer.parseInt(String.format("%d",	TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())));		
		if (shouldRemove(diffInTime(expireTime))) {
			// Removing item
			this.threads.execute(this.createRemoveRunnable(name));
			return null;
		}
		return this.objects.get(name);
	}
	
	/**
	 * Forcefully remove the user related information from the cache
	 * Useful for all those cases where session of the user has ended
	 * @param name
	 */
	public void forceRemoval(final String name){		
		this.threads.execute(this.createRemoveRunnable(name));
	}

	private int diffInTime(final long cacheInTimeMls) {

		int cachePushTime = 0;
		if (cacheInTimeMls != 0) {			
			cachePushTime = Integer.parseInt(String.format("%d",TimeUnit.MILLISECONDS.toMinutes(cacheInTimeMls)));
		}
		int now = Integer.parseInt(String.format("%d",TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())));
		return (now - cachePushTime);

	}

	private boolean shouldRemove(int timeDiffInMin) {
		//System.out.println(" Time difference "+timeDiffInMin);
		return (timeDiffInMin >= getCacheRefreshPeriod());
	}

	private static int getCacheRefreshPeriod() {
		// Get expiration time
		String customExpireFromPrompt = CACHETIME;
		if (null == customExpireFromPrompt) {
			// System.out.println("Its null");
		}
		// System.out.println("customExpireFromPrompt -- > "+customExpireFromPrompt);
		int customExpire = 0;
		if (!StringUtils.isBlank(customExpireFromPrompt)) {
			customExpire = Integer.parseInt(customExpireFromPrompt);
			// System.out.println("customExpire -- > "+customExpire);
		} else {
			// Default to 10 min
			customExpire = 10;
		}

		return customExpire;
	}

	/**
	 * Convenience method.
	 */
	@SuppressWarnings("unchecked")
	public <R extends T> R get(final String name, final Class<R> type) {
		return (R) this.get(name);
	}

	private static final Logger LOGGER = Logger.getLogger(ACLCache.class
			.getName());

}
