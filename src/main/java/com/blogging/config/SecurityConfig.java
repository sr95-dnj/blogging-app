package com.blogging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.blogging.constant.BlogConstant;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/registration",
			"/login",
			"/h2-console/**",
			"/fonts/**"
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	    .csrf().disable().cors().disable();
		http.authorizeRequests()
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		.antMatchers("/admin/**").hasAuthority(BlogConstant.ROLE_ADMIN)
		.antMatchers("/blogger/**").hasAuthority(BlogConstant.ROLE_BLOGGER)
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
		.and()
		.logout().logoutSuccessUrl("/login?logout").deleteCookies("remember-me").permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied")
		.and().headers().frameOptions().disable();
	}

	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
}
