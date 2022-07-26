package com.villvay.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.HttpMethod.*;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-26
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return  provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/blog/home")
                .hasAuthority("USER")
                .antMatchers("/blog/admin")
                .hasAuthority("ADMIN")
                .antMatchers("/blog/author/**")
                .hasAuthority("USER")
                .antMatchers("/blog/post/**")
                .hasAuthority("USER")
                .antMatchers("/blog/comment/**")
                .hasAuthority("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(POST, "/blog/author/register");
        webSecurity.ignoring().antMatchers(PUT, "/blog/author/update");
        webSecurity.ignoring().antMatchers(POST, "/blog/author/login");
        webSecurity.ignoring().antMatchers(POST, "/blog/author/logout");
        webSecurity.ignoring().antMatchers(DELETE, "/blog/author/delete");

        webSecurity.ignoring().antMatchers(POST, "/blog/post/add");
        webSecurity.ignoring().antMatchers(PUT, "/blog/post/update");
        webSecurity.ignoring().antMatchers(POST, "/blog/post/login");
        webSecurity.ignoring().antMatchers(POST, "/blog/post/logout");
        webSecurity.ignoring().antMatchers(DELETE, "/blog/post/delete");

        webSecurity.ignoring().antMatchers(POST, "/blog/comment/add");
        webSecurity.ignoring().antMatchers(PUT, "/blog/comment/update");
        webSecurity.ignoring().antMatchers(POST, "/blog/comment/login");
        webSecurity.ignoring().antMatchers(POST, "/blog/comment/logout");
        webSecurity.ignoring().antMatchers(DELETE, "/blog/comment/delete");
    }

}
