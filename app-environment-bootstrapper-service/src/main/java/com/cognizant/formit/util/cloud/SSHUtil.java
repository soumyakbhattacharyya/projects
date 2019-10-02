package com.cognizant.formit.util.cloud;

import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHUtil {
	
	private static final Logger l = Logger.getLogger(CloudResourceManager.class);
	
	public static Session openSession(String userId,String hostName,int portNumber,int maxwait,String keyFileLoc) throws JSchException {
		
		//userId = "ubuntu";
		//portNumber = 22;
		//keyFileLoc ="/EBS/Install/rundeck_ssh_key/amazon.priv";
		//keyFileLoc = "D:/Cloud-Set/Source-Code/App-Env-Code/Rundeck-Configuration/app-environment-bootstrapper-client-0.0.1-configuration/amazon.priv";
				
        JSch jsch = new JSch();        
        // adding the private key to the identity
        if (null != keyFileLoc) {
            jsch.addIdentity(keyFileLoc);
        }      

        Session session = jsch.getSession(userId, hostName, portNumber);
        session.setTimeout( (int) maxwait);
        
        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        l.info("Set timeout to " + maxwait);               
        l.info("Connecting to " + hostName + ":" + portNumber);
        
        //session.setConfig("MaxAuthTries", "1");//jsch 0.1.46+
        //use keyboard-interactive last
        //session.setConfig("PreferredAuthentications", "publickey,password,keyboard-interactive");
        session.connect();        
        session.disconnect();        
        return session;
    }


}
