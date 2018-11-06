package org.robert.oauth2.authorization.configuration.userdetail;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class JdbcUserDetailsConf {
	public static String CUSTOM_CREATE_USER_SQL = "insert into calendar_users (username, password, enabled) values (?,?,?)";

	public static String CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY = "select g.id, g.group_name, ga.authority "
			+ "from groups g, group_members gm, " + "group_authorities ga where gm.username = ? "
			+ "and g.id = ga.group_id and g.id = gm.group_id";

	public static String CUSTOM_USERS_BY_USERNAME_QUERY = "select email, password, true "
			+ "from calendar_users where email = ?";

	public static String CUSTOM_AUTHORITIES_BY_USERNAME_QUERY = "select cua.id, cua.authority "
			+ "from calendar_users cu, calendar_user_authorities " + "cua where cu.email = ? "
			+ "and cu.id = cua.calendar_user";
	
	
	@Autowired
	private DataSource dataSource;

 
	
	@Bean
	public UserDetailsManager JdbcUserDetailsServices() {
		 
		// JDBC
		JdbcUserDetailsManager mgr = new JdbcUserDetailsManager();
		mgr.setDataSource(dataSource);

		// Override default SQL for JdbcUserDetailsManager
		mgr.setGroupAuthoritiesByUsernameQuery(CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY);
		mgr.setUsersByUsernameQuery(CUSTOM_USERS_BY_USERNAME_QUERY);
		mgr.setAuthoritiesByUsernameQuery(CUSTOM_AUTHORITIES_BY_USERNAME_QUERY);

		// TODO: This is not available through AuthenticationManagerBuilder
		mgr.setCreateUserSql(CUSTOM_CREATE_USER_SQL);
		return mgr;
	}
}
