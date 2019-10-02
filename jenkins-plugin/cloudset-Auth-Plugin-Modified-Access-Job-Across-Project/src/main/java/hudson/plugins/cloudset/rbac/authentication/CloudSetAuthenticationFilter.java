package hudson.plugins.cloudset.rbac.authentication;

import hudson.plugins.cloudset.rbac.util.RBacUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.cognizant.jpaas.rbacclient.RbacClientUtil;


public class CloudSetAuthenticationFilter implements Filter {
		
	private final Filter superFilter;
	private final CloudSetSecurityRealm cloudSetSecurityRealm;

	public CloudSetAuthenticationFilter(final Filter superFilter,
			final CloudSetSecurityRealm cloudSetSecurityRealm) {
		this.superFilter = superFilter;
		this.cloudSetSecurityRealm = cloudSetSecurityRealm;
	}

	public void destroy() {
	}

	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
									
		String project = request.getParameter("projectSelect");					
		if(project!= null && !project.isEmpty()){
			project = RBacUtil.getProjectExternalID(project);
			cloudSetSecurityRealm.setProjectID(project);			
		}		
		this.superFilter.doFilter(request, response, chain);
	}

	public void init(final FilterConfig filterConfig) throws ServletException {
		
	}
}
