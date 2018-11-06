package org.robert.oauth2.authorization.configuration.userdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

//@Configuration
public class InMemoryUserDetailsConf {
	
	@Autowired
	private ClientDetailsServiceConfigurer clients;
	
	@Bean
	public UserDetailsManager InMemoryUserDetailsServices() {
		// 記憶體
		InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();
		mgr.createUser(User.builder().username("user1@example.com").password("user1").roles("USER").build());
		 
		return mgr;
	}
}
