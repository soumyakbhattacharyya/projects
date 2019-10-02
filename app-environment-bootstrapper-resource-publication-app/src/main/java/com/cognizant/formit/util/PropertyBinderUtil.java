package com.cognizant.formit.util;

import com.pholser.util.properties.PropertyBinder;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;


public final class PropertyBinderUtil 
{
    private static RunDeckPublishProperties runDeckResourcePublishProperties; //NOPMD Long Variable Instance
    
    /**
     * instantiate Logger Object
     */
    private static final Logger LOGGER = Logger.getLogger(PropertyBinderUtil.class);
    
    private PropertyBinderUtil()
    {
        
    }
    
    public static RunDeckPublishProperties getPIPBinderInstance()
    {
        if(runDeckResourcePublishProperties==null)
        {
            try {
                    final PropertyBinder<RunDeckPublishProperties> binder = PropertyBinder.forType(RunDeckPublishProperties.class);
                    final String propFileLoc = getRunDeckResourcePublishHome()+ "/runDeckResourcePublishProperties.properties";
                    runDeckResourcePublishProperties = binder.bind(new File(propFileLoc));
            } catch (IOException e) {
                    // This is NOOP situation for PORTAL
                    LOGGER.error("COULD NOR READ runDeckResourcePublishProperties !! PLEASE RESOLVE THIS ISSUE...", e);
                    LOGGER.error("============= EXITING APPLICATION ======================");
                    //System.exit(1);
            }
        }
        
        return runDeckResourcePublishProperties;
    }
    
    private static String getRunDeckResourcePublishHome()
    {
        String home=null;
        
        home=System.getProperty("RUNDECK_PUBLISH_HOME");
        
        if(home==null)
        {
           home=System.getenv("RUNDECK_PUBLISH_HOME"); 
        }
        LOGGER.info("rundeck publish home value is "+home);
        
        return home;
    }
    
}
