package com.toby.springsecurity.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {		
		auth.inMemoryAuthentication().withUser(User.withUsername("john").password("{noop}test123").roles("EMPLOYEE"))
		.withUser(User.withUsername("mary").password("{noop}test123").roles("EMPLOYEE","MANAGER"))
		.withUser(User.withUsername("Toby").password("{noop}test123").roles("EMPLOYEE","ADMIN"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").hasRole("EMPLOYEE")
		.antMatchers("/manager/**").hasRole("MANAGER")
		.antMatchers("/admin/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").loginProcessingUrl("/authenticate")
		.permitAll().and().logout().permitAll()
		.and()
		.exceptionHandling()
		.accessDeniedPage("/access-denied");
	}

}
