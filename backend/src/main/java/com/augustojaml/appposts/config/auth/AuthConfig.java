package com.augustojaml.appposts.config.auth;

import java.util.Arrays;

import com.augustojaml.appposts.config.auth.JWT.JWTBasicAuthenticationFilter;
import com.augustojaml.appposts.config.auth.JWT.JWTPasswordAuthenticationFilter;
import com.augustojaml.appposts.config.auth.JWT.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JWTUtil jwtUtil;

  private static final String[] PUBLIC_MATCH = {
      "/uploads/**",
      // "/users/**",
      "/post/**",
  };

  private static final String[] PUBLIC_MATCHERS_GET = {
      "/roles/**"
  };

  private static final String[] PUBLIC_MATCHERS_POST = {
      "/auth/refresh-token",
      "/users",
      "/roles"
  };

  private static final String[] PUBLIC_MATCHERS_PUT = {
      "/users"
  };

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.cors().and().csrf().disable();

    http.authorizeRequests()
        .antMatchers(PUBLIC_MATCH).permitAll()
        .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
        .antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).permitAll()
        .anyRequest().authenticated();

    http.addFilter(new JWTPasswordAuthenticationFilter(authenticationManager(), jwtUtil));
    http.addFilter(new JWTBasicAuthenticationFilter(authenticationManager(), jwtUtil, userDetailsService));
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
