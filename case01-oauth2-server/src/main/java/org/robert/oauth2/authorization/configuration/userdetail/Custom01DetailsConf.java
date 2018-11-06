package org.robert.oauth2.authorization.configuration.userdetail;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.robert.oauth2.authorization.ldap.userdetails.ad.ActiveDirectoryLdapAuthoritiesPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.util.ClassUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.provisioning.UserDetailsManager;
 


//@Configuration
public class Custom01DetailsConf {
	 
    @Bean
    public AuthenticationProvider authenticationProvider(){
    	AuthenticationProvider ap = new AuthenticationProvider( ) {

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				System.out.println("--------------------------------" + authentication.getClass().getCanonicalName());
				;
				UsernamePasswordAuthenticationToken upad = (UsernamePasswordAuthenticationToken) authentication;
				String username = (String) upad.getPrincipal();
				String password = (String) upad.getCredentials();

				Map<String, String> requestParameters = null;
				String clientId = null;
				
				Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
				boolean approved = false;
				Set<String> scope = new HashSet<String>(Arrays.asList("read_profile"));
				Set<String> resourceIds = new HashSet<String>(Arrays.asList("auth2server"));
				String redirectUri = "";
				Set<String> responseTypes =  new HashSet<String>(Arrays.asList("auth2server"));
				Map<String, Serializable> extensionProperties = new HashMap<String, Serializable> ();
				OAuth2Request storedRequest = new OAuth2Request(requestParameters, clientId, authorities, approved,
						scope, resourceIds, redirectUri, responseTypes, extensionProperties);
				OAuth2Authentication result = new OAuth2Authentication(storedRequest, authentication);
//				
//				return result; 
				 return new UsernamePasswordAuthenticationToken(username, null, authorities);
			}

			@Override
			public boolean supports(Class<?> authentication) {
				System.out.println("--------------------------------");
				if (org.apache.commons.lang3.ClassUtils.isAssignable(authentication,
						UsernamePasswordAuthenticationToken.class)) {
					return true;
				}
				;
				return false;
			}
    		
    	};
        return ap;
    }

    
//	@Bean
//	public UserDetailsManager LdapUserDetailsServices(@Autowired DefaultSpringSecurityContextSource context) {
//
//		UserDetailsManager mgr = new LdapUserDetailsManager(context);
//
//		return mgr;
//	}
}
