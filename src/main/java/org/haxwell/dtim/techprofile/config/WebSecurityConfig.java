package org.haxwell.dtim.techprofile.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private AuthenticationEntryPoint auth;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select name,password,enabled from user where name=?")
		.authoritiesByUsernameQuery(
			"select u.name, ur.name "
			+ "from user u, user_role ur, user_user_role_map urMap "
			+ "where u.name=? "
			+ "and u.id = urMap.user_id "
			+ "and ur.id = urMap.user_role_id"
			);
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().authenticationEntryPoint(auth)
        	.and()
        	.csrf()
            	.disable()
        	.authorizeRequests()
	    		.antMatchers("/api/candidate").permitAll()
	        	.antMatchers("/api/candidate/new").permitAll()
        		.antMatchers("/api/candidate/**/markInAttendance").permitAll()
	    		.antMatchers("/api/**").authenticated()
                .and() 
                .logout()
                .permitAll();
    }
}
