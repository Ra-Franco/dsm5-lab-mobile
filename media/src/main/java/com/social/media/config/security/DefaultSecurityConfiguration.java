package com.social.media.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfiguration {

    //@Autowired
    //CustomBasicAuthFilter customBasicAuthFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/auth/**").permitAll();
                request.anyRequest().authenticated();
            })
            .csrf(AbstractHttpConfigurer::disable);
            //.addFilterAfter(customBasicAuthFilter, BasicAuthenticationFilter.class);
    return http.build();
    }

}
