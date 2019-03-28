package org.owasp.pangloss.infra.configurations;

import org.owasp.pangloss.infra.security.authentication.handlers.OKAuthenticationSuccessHandler;
import org.owasp.pangloss.infra.security.authentication.handlers.UnauthorizedAuthenticationFailureHandler;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CorsFilter corsFilter() { //FIXME: remove it
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//FIXME: remove it
        //
        configureHttpSecurity(http);
    }

    private void configureHttpSecurity(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/login", "/api/logout", "/api/csrf").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().loginProcessingUrl("/api/login")
                .successHandler(new OKAuthenticationSuccessHandler())
                .failureHandler(new UnauthorizedAuthenticationFailureHandler())
                .and()
                .exceptionHandling()
                /*
                 * avoiding the redirect to /login behavior
                 * when trying to access a resource endpoint without being authenticated
                 * inside a browser
                 */
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint("login"))
                .and()
                .logout().logoutUrl("/api/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }
}
