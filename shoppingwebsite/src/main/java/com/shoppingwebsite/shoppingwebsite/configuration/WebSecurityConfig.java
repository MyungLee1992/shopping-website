package com.shoppingwebsite.shoppingwebsite.configuration;

import com.shoppingwebsite.shoppingwebsite.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable() // Enable CORS and disable CSRF
				.authorizeRequests() // Set permissions on endpoints
				.antMatchers("/auth/**").permitAll()
				.antMatchers("/item/**").permitAll()
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/cart/**").hasAnyAuthority("ADMIN", "USER", "ANONYMOUS")
				.anyRequest().authenticated()
			.and()
				.anonymous().principal("guest").authorities("ANONYMOUS")
			.and()
			.exceptionHandling() // Set unauthorized requests exception handler
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Set session management to stateless

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.withUsername("user")
				.password("{bcrypt}$2a$10$VGVJanfvUVDBqojb5Jui7OXiban8t3m7hYzrx0EXmqR3FivJZMdA6")
				.authorities("USER")
				.build();
		UserDetails admin = User.withUsername("admin")
				.password("{bcrypt}$2a$10$YesKQCRVc2nvpzGc.FBipeFy0Yx3V8M1cRyz9vnt84zs5S/vsL2H6")
				.authorities("ADMIN")
				.build();
		UserDetails guest = User.withUsername("guest")
				.password("{bcrypt}$2a$10$zj8zkftGjnUk98zxCuCUDenrjj/XxUimfq1VDYEAnW.x3ei/4T6cS")
				.authorities("ANONYMOUS")
				.build();
		return new InMemoryUserDetailsManager(user, admin, guest);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}