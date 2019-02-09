package org.owasp.pangloss.infra.security;

import org.owasp.pangloss.infra.security.authentication.handlers.OKAuthenticationSuccessHandler;
import org.owasp.pangloss.infra.security.authentication.handlers.UnauthorizedAuthenticationFailureHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                    .successHandler(new OKAuthenticationSuccessHandler())
                    .failureHandler(new UnauthorizedAuthenticationFailureHandler());
    }
}
