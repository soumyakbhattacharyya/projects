package com.cognizant.formit.util;

import com.pholser.util.properties.BoundProperty;

public interface RunDeckPublishProperties 
{
    @BoundProperty("rundeck.publish.db.url")
    String rundeckPublishDbUrl();
    
    @BoundProperty("rundeck.publish.db.driver.class")
    String rundeckPublishDbDriverClass();
    
    @BoundProperty("rundeck.publish.db.uid")
    String rundeckPublishDbUid();
    
    @BoundProperty("rundeck.publish.db.pwd")
    String rundeckPublishDbPwd();
    
    @BoundProperty("rundeck.publish.db.maxPoolSize")
    String rundeckPublishDbMaxPoolSize();
    
    @BoundProperty("rundeck.publish.db.minPoolSize")
    String rundeckPublishDbMinPoolSize();
    
    @BoundProperty("rundeck.publish.db.initialPoolSize")
    String rundeckPublishDbInitialPoolSize();
    
    @BoundProperty("rundeck.publish.db.maxIdleTime")
    String rundeckPublishDbMaxIdleTime();
        
}
