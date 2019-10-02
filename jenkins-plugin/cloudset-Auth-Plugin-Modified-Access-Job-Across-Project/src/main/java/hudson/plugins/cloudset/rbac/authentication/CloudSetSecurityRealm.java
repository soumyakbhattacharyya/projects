package hudson.plugins.cloudset.rbac.authentication;

import hudson.Extension;
import hudson.Util;
import hudson.cli.CLICommand;
import hudson.cli.LoginCommand;
import hudson.cli.LogoutCommand;
import hudson.cli.declarative.CLIResolver;
import hudson.model.Descriptor;
import hudson.plugins.cloudset.rbac.authorization.CloudSetAuthorizationUserACL;
import hudson.plugins.cloudset.rbac.constants.CloudSetRbacConstants;
import hudson.plugins.cloudset.rbac.util.RBacUtil;
import hudson.security.ACL;
import hudson.security.AbstractPasswordBasedSecurityRealm;
import hudson.security.CliAuthenticator;
import hudson.security.GroupDetails;
import hudson.security.SecurityRealm;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.collections.map.LRUMap;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.Header;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.springframework.dao.DataAccessException;

/**
 * Implementation of the AbstractPasswordBasedSecurityRealm that uses a MySQL
 * database as the source of authentication information.
 * 
 * @author Alex Ackerman
 */
public class CloudSetSecurityRealm extends AbstractPasswordBasedSecurityRealm {

	
	private static final Logger log = Logger
			.getLogger(CloudSetAuthorizationUserACL.class.getName());	
	private String projectID;
	private transient LRUMap cliAuthCache = new LRUMap(32);
	
	//private boolean isUserAuthenticated = false;
	//private boolean isUserAuthorized = false;
	//private String fromString = "";
	
	@DataBoundConstructor
	public CloudSetSecurityRealm(String myRbacServerURL,
			String securityKeyfilePath, String rbacPassword) {
		this.myRbacServerURL = Util.fixEmptyAndTrim(myRbacServerURL);
		this.securityKeyfilePath = Util.fixEmptyAndTrim(securityKeyfilePath);
		this.rbacPassword = Util.fixEmptyAndTrim(rbacPassword);

	}

	public static final class DescriptorImpl extends Descriptor<SecurityRealm> {
		@Override
		public String getHelpFile() {
			return "/plugin/mysql-auth/help/overview.html";
		}

		@Override
		public String getDisplayName() {
			return Messages.DisplayName();
		}
	}

	
	public String getSuperAuthenticationGatewayUrl() {
		return "j_acegi_security_check";
	}

	@Override
	public String getAuthenticationGatewayUrl() {
		return "securityRealm/selectCloudSetProjects";
	}
	
	
	public void doSelectCloudSetProjects(StaplerRequest req, StaplerResponse rsp,@Header("Referer") final String referer)
			throws IOException {

		req.getSession().setAttribute(CloudSetRbacConstants.HTTP_REFERER, referer);
		
//		log.info("********* Username is " + req.getParameter("j_username"));
//		log.info("********* Password is " + req.getParameter("j_password"));
//		log.info("********* myRbacServerURL is " + myRbacServerURL);
//		log.info("*******referer ******"+referer);
		
		String userName = req.getParameter("j_username");
		String password = req.getParameter("j_password");

		if (userName == null || userName.isEmpty() || password == null
				|| password.isEmpty()) {
			
			log.severe("*******Invalid Credential ******");
			SecurityContextHolder.getContext().setAuthentication(null);
			String url = req.getContextPath() + "/" + "loginError";
			rsp.sendRedirect(url);		
			 throw new BadCredentialsException("No password specified");
		}

		// authenticate the user using Rbac ldap authentication
		try {
			boolean isUserAuthenticated = RBacUtil.isUserAuthenticated(
					userName, password, myRbacServerURL,securityKeyfilePath,rbacPassword);
			//boolean isUserAuthenticated = true;
			if (!isUserAuthenticated) {
				log.severe("*******Invalid Credential ******");
				SecurityContextHolder.getContext().setAuthentication(null);
				String url = req.getContextPath() + "/" + "loginError";
				//rsp.sendRedirect(url);	
				throw new BadCredentialsException("Invalid User");
			}
		} catch (CloudSetAuthenticationException ex) {
			log.severe("*******Error occured as ******"+ex.getMessage());
			SecurityContextHolder.getContext().setAuthentication(null);
			String url = req.getContextPath() + "/" + "loginError";
			//rsp.sendRedirect(url);
			throw new BadCredentialsException("Invalid User");
			
		} catch (Exception ex) {
			log.severe("*******Error occured as ******"+ex.getMessage());
			SecurityContextHolder.getContext().setAuthentication(null);
			String url = req.getContextPath() + "/" + "loginError";
			rsp.sendRedirect(url);
			throw new BadCredentialsException("Invalid User");
		}

		String from = req.getParameter("from");
		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			HttpSession sess = Stapler.getCurrentRequest().getSession();
			if (sess != null) {
				sess.setAttribute(CloudSetRbacConstants.USER_ID, userName);
				sess.setAttribute(CloudSetRbacConstants.PASSWORD, password);
				sess.setAttribute(CloudSetRbacConstants.FROM, from);
			}
		}

		

		if (req.getUserPrincipal() == null) {			
			rsp.sendRedirect("login");			
			return;
		}else{
			if (referer!=null) {
	            rsp.sendRedirect(referer);
	        }
		}

		if (from != null && from.startsWith("/") && !from.equals("/loginError")) {
			rsp.sendRedirect2(from);
			return;
		}

		String url = AbstractProcessingFilter.obtainFullRequestUrl(req);
		if (url != null) {
			// if the login redirect is initiated by Acegi
			// this should send the user back to where s/he was from.
			rsp.sendRedirect2(url);
			return;
		}

		rsp.sendRedirect2(".");
	}

	@Extension
	public static DescriptorImpl install() {
		return new DescriptorImpl();
	}

	/**
	 * Authenticates the specified user using the password against the Rbac
	 * configuration.
	 * 
	 * @param username
	 *            The username to lookup
	 * @param password
	 *            The password to use for authentication
	 * @return A UserDetails object containing information about the user.
	 * @throws AuthenticationException
	 *             Thrown when the username/password do not match with ldap.
	 */
	@Override
	public UserDetails authenticate(String username, String password)
			throws AuthenticationException {
		 UserDetails userDetails = null;				
		String projectID =  getProjectID();
				
		Set<GrantedAuthority> groups = new HashSet<GrantedAuthority>();
		groups.add(SecurityRealm.AUTHENTICATED_AUTHORITY);
		
		boolean isUserAuthenticated = true;
		try {
			
			if (Stapler.getCurrentRequest() != null
					&& Stapler.getCurrentRequest().getSession() != null) {
				HttpSession sess = Stapler.getCurrentRequest().getSession();
				if (sess != null
						&& sess.getAttribute(CloudSetRbacConstants.USER_ID) != null) {
					sess.setAttribute(CloudSetRbacConstants.PROJECT_ID,
							projectID);
					username = (String) sess
							.getAttribute(CloudSetRbacConstants.USER_ID);
				} else {
					throw new CloudSetAuthenticationException(
							"No Valid user Found");
				}
			}
			
		} catch (CloudSetAuthenticationException ex) {
			Logger.getLogger(CloudSetSecurityRealm.class.getName()).log(
					Level.SEVERE, null, ex);
			throw new CloudSetAuthenticationException(ex.getMessage());
		} catch (Exception ex) {
			Logger.getLogger(CloudSetSecurityRealm.class.getName()).log(
					Level.SEVERE, null, ex);
			throw new CloudSetAuthenticationException(
					"Problem occured while connecting to Rbac Service "
							+ ex.getMessage());
		}
		LOGGER.info("Is user authenticated " + isUserAuthenticated);

		if (!isUserAuthenticated) {
			LOGGER.severe("Rbac-Security: Invalid Username or Password");
			throw new CloudSetAuthenticationException(
					"Invalid Username or Password");
		} else {
			
			userDetails = new CloudSetUserDetail(username, password, projectID,
					true, true, true, true,
					groups.toArray(new GrantedAuthority[groups.size()]),false);
		}
		return userDetails;
	}

	/**
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException, DataAccessException {
		Set<GrantedAuthority> groups = new HashSet<GrantedAuthority>();
		groups.add(SecurityRealm.AUTHENTICATED_AUTHORITY);		
		if(cliAuthCache!= null && cliAuthCache.containsKey(username)){
			
			GrantedAuthority grantAuth = new GrantedAuthority() {
				
				public String getAuthority() {					
					projectID =(String)cliAuthCache.get(username);
					return projectID;
				}
			};
			groups.add(grantAuth);
		}else if(projectID!= null){			
			GrantedAuthority grantAuth = new GrantedAuthority() {				
				public String getAuthority() {					
					return projectID;
				}
			};
			groups.add(grantAuth);
		}		
		
		return new CloudSetUserDetail(username, "", projectID,
				true, true, true, true,
				groups.toArray(new GrantedAuthority[groups.size()]),false);
	}

	/**
	 * 
	 * @param groupname
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	@Override
	public GroupDetails loadGroupByGroupname(String groupname)
			throws UsernameNotFoundException, DataAccessException {
		LOGGER.warning("ERROR: Group lookup is not supported.");
		throw new UsernameNotFoundException(
				"loadGroupByGroupname: Non-supported function");
	}

	class Authenticator extends AbstractUserDetailsAuthenticationProvider {

		@Override
		protected void additionalAuthenticationChecks(UserDetails userDetails,
				UsernamePasswordAuthenticationToken authentication)
				throws AuthenticationException {
			// Assumed to be done in the retrieveUser method
		}

		@Override
		protected UserDetails retrieveUser(String username,
				UsernamePasswordAuthenticationToken authentication)
				throws AuthenticationException {
			return CloudSetSecurityRealm.this.authenticate(username,
					authentication.getCredentials().toString());
		}

	}
	
	
	/**
	 * Overridden this method to take care of ProjectID for CLI authentication.
	 * User needs to provide the project id in the cli while logging in.the command should
	 * 
	 * java -jar jenkins-cli.jar -s <url> -i id_rsa login --cliProjectID <project-external_id>
	 * 
	 * one should logout to clear the cache
	 * 
	 * java -jar jenkins-cli.jar -s <url> -i id_rsa logout
	 * 
	 */
		@Override
	    public CliAuthenticator createCliAuthenticator(final CLICommand command) {
	    	
	    	 return new CliAuthenticator() {
	            @Option(name="--cliProjectID",usage="project ID to authorize yourself to Jenkins")
	            public String cliProjectID;

	            public Authentication authenticate() throws AuthenticationException, IOException, InterruptedException {
	                Authentication auth = command.getTransportAuthentication();	     
	                
//	                if(command.getCurrent().getTransportAuthentication()!= null){
//	    	    		log.info("command contains "+command.getCurrent().getTransportAuthentication());	    	    		
//	    	    		if(command.getCurrent().getTransportAuthentication().getAuthorities().length >1){	    	    			 	    			
//	    		             return command.getCurrent().getTransportAuthentication();
//	    	    		}
//	    	    	}
	                String userName = (String)auth.getPrincipal();
	                
	                if(userName.equalsIgnoreCase(CloudSetRbacConstants.ANONYMOUS)){
	                	throw new BadCredentialsException("No Valid User Found");
	                }
	                
	                if (cliProjectID!=null){
	                    try {
	                        log.info("cliProjectID is "+cliProjectID);
	                    	projectID = cliProjectID;                        
	                    	if (cliAuthCache == null) {
	    						cliAuthCache = new LRUMap(32);
	    					}
	    					synchronized (cliAuthCache) {						
	    						if(cliProjectID!= null){
	    							cliAuthCache.put(userName, cliProjectID);
	    						}
	    					}
	                    } catch (Exception e) {
	                        throw new BadCredentialsException("Error "+e.getMessage(),e);
	                    }
	                }else if(command instanceof LoginCommand){
	                	throw new BadCredentialsException("No projectID found");
	                }	                	                	                	                
					
	                if(command instanceof LogoutCommand){
	                	if (cliAuthCache != null) {
	                		cliProjectID = (String)cliAuthCache.get(userName);
	                		cliAuthCache.remove(userName);
	                	}
	                }else if(!(command instanceof LoginCommand)){
	                	if (cliAuthCache != null && cliAuthCache.get(userName)!= null) {
	                		cliProjectID = (String)cliAuthCache.get(userName);
	                	}
	                }
	                
	                Set<GrantedAuthority> groups = new HashSet<GrantedAuthority>();
	                GrantedAuthority grantAuth = new GrantedAuthority() {
						
						public String getAuthority() {							
							return cliProjectID;
						}
					};
	        		groups.add(grantAuth);
	                CloudSetUserDetail cloudSetUserDetail = new CloudSetUserDetail(userName, "", cliProjectID,
	        				true, true, true, true,
	        				groups.toArray(new GrantedAuthority[groups.size()]),true);
	                return new UsernamePasswordAuthenticationToken(cloudSetUserDetail, "", groups.toArray(new GrantedAuthority[groups.size()]));	                
	            }
	        };
	    }

	/**
	 * Logger for debugging purposes.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(CloudSetSecurityRealm.class.getName());

	/**
	 * The Rbac server to use.
	 */
	private String myRbacServerURL;
	/**
	 * The Security Key file to use to connect to the Rbac server.
	 */
	private String securityKeyfilePath;

	/**
	 * The password to use to connect to the Rbac server.
	 */
	private String rbacPassword;
	
	public String getMyRbacServerURL() {
		return myRbacServerURL;
	}

	public String getSecurityKeyfilePath() {
		return securityKeyfilePath;
	}

	public Set<String> getProjectList() {

		// Get user from session store
		String userName = null;
		Set<String> projectIds = null;

		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			HttpSession sess = Stapler.getCurrentRequest().getSession();
			if (sess != null
					&& sess.getAttribute(CloudSetRbacConstants.USER_ID) != null) {				
				userName = (String) sess
						.getAttribute(CloudSetRbacConstants.USER_ID);

				if (userName != null) {					
						try {
							projectIds = RBacUtil.getUserProjects(userName,
									myRbacServerURL,securityKeyfilePath,rbacPassword);
						} catch (Exception e) {
							log.severe("Exception occured while getting project list");
						}
						Stapler.getCurrentRequest()
								.getSession(false)
								.setAttribute(
										CloudSetRbacConstants.USER_PROJECTS_SESSION_VAR,
										projectIds);					
				}
			}
		}

		return projectIds;
	}

	
	public String getLoggedInUserName() {
		String userName= "";
		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			HttpSession sess = Stapler.getCurrentRequest().getSession();
			if (sess != null
					&& sess.getAttribute(CloudSetRbacConstants.USER_ID) != null) {
				userName = (String) sess
						.getAttribute(CloudSetRbacConstants.USER_ID);
			}
		}
		
		return userName;
	}

	
	public String getUserPassword() {
		String password = "";
		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			HttpSession sess = Stapler.getCurrentRequest().getSession();
			if (sess != null
					&& sess.getAttribute(CloudSetRbacConstants.PASSWORD) != null) {
				password = (String) sess
						.getAttribute(CloudSetRbacConstants.PASSWORD);
			}
		}
		return password;
	}
		
	
	public String getFromString() {
		
		String fromString = "/";
		
//		String rootURL = Jenkins.getInstance().getRootUrl();
//		String rootURLFromRequest = Jenkins.getInstance().getRootUrlFromRequest();
//		StaplerRequest req = Stapler.getCurrentRequest();	
				
		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			HttpSession sess = Stapler.getCurrentRequest().getSession();
			if (sess != null
					&& sess.getAttribute(CloudSetRbacConstants.FROM) != null) {
				fromString = (String) sess
						.getAttribute(CloudSetRbacConstants.FROM);
			}
		}
		if(fromString == null || fromString.isEmpty()){
			fromString = "/";
		}
		return fromString;
	}

	
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	@Override
	public Filter createFilter(FilterConfig filterConfig) {
		return new CloudSetAuthenticationFilter(super.createFilter(filterConfig), this);
	}


	public String getRbacPassword() {
		return rbacPassword;
	}


	public void setRbacPassword(String rbacPassword) {
		this.rbacPassword = rbacPassword;
	}
		
	
}
