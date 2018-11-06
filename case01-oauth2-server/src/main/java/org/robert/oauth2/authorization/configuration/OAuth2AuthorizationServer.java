package org.robert.oauth2.authorization.configuration;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer; 
 

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	// token是否儲存驗證
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }
//
//    @Bean
//    public ApprovalStore approvalStore() {
//        return new JdbcApprovalStore(dataSource);
//    }

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//            throws Exception {
//        //@formatter:off
//        endpoints
//            .approvalStore(approvalStore())
//            .tokenStore(tokenStore());
//        //@formatter:on
//    }

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//JDBC
		clients.jdbc(dataSource); 
	}

	// 密碼是否Bcrypt加密(/database/h2/calendar-bcrypt.sql)
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.passwordEncoder(passwordEncoder());
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // the size can be between 4 to 31
//        return new BCryptPasswordEncoder(4);
//    }




	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
		String clientId = "clientapp";
		String clientSecret = "123456";
		clientSecret = encoder.encode(clientSecret);
		System.out.println(clientId);
		System.out.println(clientSecret);
	}
}
