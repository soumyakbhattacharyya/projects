package com.cognizant.plugins;
import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import hudson.util.Secret;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.cognizant.util.HAEmailUtility;

public class HAEmailNotifier implements Describable<HAEmailNotifier> {

    private final String name;

    @DataBoundConstructor
    public HAEmailNotifier(String name) {
    	this.name = name;
    }

    public String getName() {
        return name;
    }

    public DescriptorImpl getDescriptor() {
    	return DESCRIPTOR;
    }
    
    
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    @Extension
    public static final class DescriptorImpl extends Descriptor<HAEmailNotifier> {
        
        private String smtpServer;
        
        private String fromEmail;
        
        private String smtpPort;
        
        private String replyToAddress;
        
        private String smtpAuthUsername;

        private Secret smtpAuthPassword;

        private boolean useSsl;
        /**
         * In order to load the persisted global configuration, you have to 
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'name'.
         *
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         */
        public FormValidation doCheckName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a name");
            if (value.length() < 4)
                return FormValidation.warning("Isn't the name too short?");
            return FormValidation.ok();
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Say hello world";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            smtpServer = nullify(json.getString("smtpServer"));
            fromEmail = nullify(json.getString("fromEmail"));
            if(json.has("useSMTPAuth")) {
                JSONObject auth = json.getJSONObject("useSMTPAuth");
                smtpAuthUsername = nullify(auth.getString("smtpAuthUserName"));
                smtpAuthPassword = Secret.fromString(nullify(auth.getString("smtpAuthPassword")));
            } else {
                smtpAuthUsername = null;
                smtpAuthPassword = null;
            }
            useSsl = json.getBoolean("useSsl");
            smtpPort = nullify(json.getString("smtpPort"));
            replyToAddress = nullify(json.getString("replyToAddress"));

            save();
			return true;
        }
        
        private String nullify(String v) {
            if(v!=null && v.length()==0)    v=null;
            return v;
        }

        public String getSmtpServer() {
        	return smtpServer;
        }
        
        public String getFromEmail() {
        	return fromEmail;
        }
        
        public String getSmtpAuthUserName() {
            return smtpAuthUsername;
        }

        public String getSmtpAuthPassword() {
            if (smtpAuthPassword==null) return null;
            return Secret.toString(smtpAuthPassword);
        }
        
        public boolean getUseSsl() {
        	return useSsl;
        }
        
        public String getSmtpPort() {
        	return smtpPort;
        }
        
        public String getReplyToAddress() {
        	return replyToAddress;
        }
        
        public FormValidation doSendTestMail(
                @QueryParameter String smtpServer, @QueryParameter String fromEmail, @QueryParameter boolean useSMTPAuth,
                @QueryParameter String smtpAuthUserName, @QueryParameter String smtpAuthPassword,
                @QueryParameter boolean useSsl, @QueryParameter String smtpPort,
                @QueryParameter String sendTestMailTo) throws IOException, ServletException, InterruptedException {
            try {
            	
            	Session session = createMessageSession(smtpServer, useSMTPAuth, 
                		smtpAuthUserName, smtpAuthPassword, useSsl, smtpPort);
            	if(sendTestEmail(session, fromEmail, sendTestMailTo)) {
            		return FormValidation.ok("E-mail sent successfully");	
            	} else {
            		return FormValidation.error("Error sending mail");
            	}
            	
            } catch(Exception ex) {
            	return FormValidation.error(ex.getMessage());
            }
        }
        
    }
    
    private static Session createMessageSession(String smtpServer, boolean useSMTPAuth, 
    		final String smtpAuthUserName, final String smtpAuthPassword, boolean useSsl, String smtpPort) {

    	Session session = null;
    	Properties props = new Properties();
		props.put("mail.smtp.host", smtpServer);
		if(useSsl) {
			props.put("mail.smtp.socketFactory.port", smtpPort);
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");	
		}
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", String.valueOf(useSMTPAuth));
 
		if(useSMTPAuth) {
			session = Session.getInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(smtpAuthUserName, smtpAuthPassword);
						}
					  });	
		} else {
			session = Session.getInstance(props);
		}
		
    	return session;
    }
    
    public static boolean sendTestEmail(Session session, String fromEmail, String sendTestMailTo) {
    	try {
    		Message message = new MimeMessage(session);
        	message.setFrom(new InternetAddress(fromEmail));
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTestMailTo));
        	message.setSubject("Testing Mail for CloudSet HA");
        	message.setText("Hi, \n this is a test mail. \n \n Please do not reply to this.");
        	Transport.send(message);
    	} catch(Exception ex) {
    		return false;
    	}
    	
    	return true;
    }
    
    public static boolean sendMessage(String hostName, String ip, String url) {
    	try {
    		
    		boolean useAuth = false;
    		if(DESCRIPTOR.getSmtpAuthUserName() != null) {
    			useAuth = true;
    		}
    		
    		String toAddress[] = DESCRIPTOR.getReplyToAddress().split(" ");
    		
    		Session session = createMessageSession(DESCRIPTOR.getSmtpServer(), useAuth,
    				DESCRIPTOR.getSmtpAuthUserName(), DESCRIPTOR.getSmtpAuthPassword(),
    				DESCRIPTOR.getUseSsl(), DESCRIPTOR.getSmtpPort());
    		
    		Message message = new MimeMessage(session);
    		message.setHeader("X-Priority", "1"); 
			message.setFrom(new InternetAddress(DESCRIPTOR.getFromEmail()));
			
			for(int i=0;i<toAddress.length;i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress[i]));
			}
			message.setSubject("Jenkins ShutDown unexpectedly.");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			String date = sdf.format(new Date());
			
			HAEmailUtility mailUtil = new HAEmailUtility();
			String mailBody = mailUtil.createMailForJenkinsFailure(hostName, ip, date.split(" ")[0], date.split(" ")[1], url);
			
			MimeMessageHelper helper = new MimeMessageHelper((MimeMessage) message);
			helper.setText(mailBody, true);
			
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    
}

