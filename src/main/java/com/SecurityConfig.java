package com;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Autowired 
	private UserDetailsService userDetailsService; 	
	
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
    
    protected void configure(HttpSecurity http) throws Exception {
    	http
		// AUTHENTICATION
		.csrf().disable() // if csrf is enabled, /logout must be a POST
		
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/Reset").permitAll()
		.antMatchers("/Reset/**").permitAll()
		.antMatchers("/welcome").permitAll()
		.antMatchers("/jsp/**").permitAll()
		.antMatchers("/js/**").permitAll()
    	.antMatchers("/resources/**").permitAll()
    	.antMatchers("/Img/**").permitAll()
		.antMatchers("/loginValidate").permitAll()
		// AUTHENTICATION AND AUTHORISATION
		.and()
		.authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/admin/").hasRole("ADMIN")
		.antMatchers("/teacher/").hasRole("TEACHER")
		.antMatchers("/teacher/**").hasRole("TEACHER")
		.antMatchers("/student/**").hasRole("STUDENT")

		.anyRequest().authenticated()
		
		.and()
	    	.formLogin()
	    	.failureForwardUrl("/error")
	    		.loginPage("/login/")
	    		.usernameParameter("email")
	    		.passwordParameter("password")
	    		.defaultSuccessUrl("/success-login", true)
	    		.loginProcessingUrl("/login")
	    		.failureUrl("/error")
	    		.permitAll()
		.and()
			.logout()
			.invalidateHttpSession(true) 
			.permitAll()
		
		.and()
		.rememberMe()
	
		.and()
		    .exceptionHandling().accessDeniedPage("/access-denied");	

    	
    }
}

