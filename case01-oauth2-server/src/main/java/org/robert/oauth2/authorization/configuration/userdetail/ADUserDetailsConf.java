package org.robert.oauth2.authorization.configuration.userdetail;

import java.util.Arrays;

import org.robert.oauth2.authorization.ldap.userdetails.ad.ActiveDirectoryLdapAuthoritiesPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.provisioning.UserDetailsManager;


//@Configuration
public class ADUserDetailsConf {
	/**
     * LDAP Server Context
     * @return
     */
	@Bean
	public DefaultSpringSecurityContextSource contextSource() {
		return new DefaultSpringSecurityContextSource(Arrays.asList("ldap://192.168.2.12:389"),
				"OU=15_TDD00,OU=IE,DC=iead,DC=local") {
			{

				setUserDn("iisildap@iead.local");
				setPassword("Password");
			}
		};
	}
    @Bean
    public AuthenticationProvider authenticationProvider(){
        ActiveDirectoryLdapAuthenticationProvider ap = new ActiveDirectoryLdapAuthenticationProvider(
                                                                    "iead.local",
                                                                       "ldap://192.168.2.12:389");
        ap.setConvertSubErrorCodesToExceptions(true);
        return ap;
    }

    @Bean
    public BindAuthenticator bindAuthenticator(FilterBasedLdapUserSearch userSearch){
        return new BindAuthenticator(contextSource()){{
            setUserSearch(userSearch);

        }};
    }

    @Bean
    public FilterBasedLdapUserSearch filterBasedLdapUserSearch(){
        return new FilterBasedLdapUserSearch("CN=Users", //user-search-base
                "(sAMAccountName={0})", //user-search-filter
                contextSource()); //ldapServer
    }

    @Bean
    public LdapAuthoritiesPopulator authoritiesPopulator(){
        return new ActiveDirectoryLdapAuthoritiesPopulator();
    }

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper(){
        return new InetOrgPersonContextMapper();
    }
	@Bean
	public UserDetailsManager LdapUserDetailsServices(@Autowired DefaultSpringSecurityContextSource context) {

		LdapUserDetailsManager mgr = new LdapUserDetailsManager(context);

		return mgr;
	}
}
