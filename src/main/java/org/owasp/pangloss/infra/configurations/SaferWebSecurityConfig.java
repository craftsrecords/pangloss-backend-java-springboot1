package org.owasp.pangloss.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import static org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse;

@Profile("mitigated")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SaferWebSecurityConfig extends WebSecurityConfig {

    @Bean
    public CorsFilter corsFilter() throws UnknownHostException {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //Allowing only trusted partners
        config.addAllowedOrigin("https://localhost:11759");
        config.addAllowedOrigin("http://localhost:11759");
        config.addAllowedOrigin("http://" + Inet4Address.getLocalHost().getHostAddress() + ":9571");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .csrfTokenRepository(withHttpOnlyFalse());

        super.configure(http);
    }
}
