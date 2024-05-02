package com.thanhstudy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.thanhstudy.service.impl.CustomCustomerDetailsService;
import com.thanhstudy.service.impl.CustomUserDetailsService;

import jakarta.annotation.Resource;

@Configuration
@EnableMethodSecurity
public class SpringSecurity {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static JwtAuthenticationFilter authenticationFilter() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public static AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private static JwtAuthenticationEntryPoint unauthorizedHandler;

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter {
        @Resource(name = "customUserDetailsService")
        private CustomUserDetailsService customUserDetailsService;

        public void globalUserDetails1(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable()).securityMatcher("/api/**").authorizeHttpRequests((authorize) -> {
                authorize.requestMatchers(HttpMethod.POST,"/api/authentication").permitAll();
                authorize.anyRequest().authenticated();
            }).exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                    .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

    @Configuration
    public static class App2ConfigurationAdapter {
        @Resource(name = "customCustomerDetailsService")
        private CustomCustomerDetailsService customCustomerDetailsService;

        public void globalUserDetails2(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customCustomerDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable()).securityMatcher("/api/user/**").authorizeHttpRequests((authorize) -> {
                authorize.requestMatchers(HttpMethod.POST,"/api/user/authentication").permitAll();
                authorize.anyRequest().authenticated();
            }).exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                    .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

}
