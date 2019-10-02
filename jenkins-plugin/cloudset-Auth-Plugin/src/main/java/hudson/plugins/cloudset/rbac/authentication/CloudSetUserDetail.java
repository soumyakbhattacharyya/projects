package hudson.plugins.cloudset.rbac.authentication;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

/**
 *
 * @author Cloudset
 */
public class CloudSetUserDetail implements UserDetails
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 404977611385042146L;

	public CloudSetUserDetail(String username, String password, String projectId,boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked, GrantedAuthority[] authorities,boolean isCliUser)
            throws IllegalArgumentException
    {
        this.username = username;
        this.password = password;
        this.projectId = projectId;
        this.enabled = enabled;
        this.accountNotExpired = accountNonExpired;
        this.credentialsNotExpired = credentialsNonExpired;
        this.accountNotLocked = accountNonLocked;
        this.authorities = authorities;
        this.isCliUser = isCliUser;
        
    }

    public GrantedAuthority[] getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return accountNotExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNotLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNotExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getProjectId() {
        return projectId;
    }
        
    
    public boolean isCliUser() {
		return isCliUser;
	}

	public void setCliUser(boolean isCliUser) {
		this.isCliUser = isCliUser;
	}
	
	
	private GrantedAuthority[] authorities;
    private String password;
    private String username;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialsNotExpired;
    private boolean enabled;
    private String projectId;
    private boolean isCliUser;
   

    @Override
    public String toString() {
        return "CloudSetUserDetail{" + "authorities=" + authorities + ", password=" + password + ", username=" + username + ", accountNotExpired=" + accountNotExpired + ", accountNotLocked=" + accountNotLocked + ", credentialsNotExpired=" + credentialsNotExpired + ", enabled=" + enabled + ", projectId=" + projectId +", isCliUser=" +isCliUser+ "}";
    }
    
    
}
