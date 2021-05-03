package com.cognizant.springlearn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().
		withUser("admin").
		password(passwordEncoder().encode("pwd")).
		roles("ADMIN").
		and()
		.withUser("user")
				.password(passwordEncoder()
				.encode("pwd"))
				.roles("USER");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		//httpSecurity.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
		httpSecurity.cors().and().csrf().disable()
		 .httpBasic()
		//.formLogin()
		//.loginPage("/login.html")
		.and()
		.authorizeRequests()
		//.antMatchers("/country")
		//.hasRole("ADMIN")
		//.antMatchers("/countries")
		//.hasRole("USER")
		.antMatchers("/authenticate").hasAnyRole("USER", "ADMIN") 
		.and()
		//.addFilter(new JwtAuthorizationFilter(authenticationManager()))
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID");
		//.addFilter(new JWTFilter());
		; 
		
	}

}
