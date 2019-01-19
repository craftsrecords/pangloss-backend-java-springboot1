package org.owasp.pangloss.infra.security.authenticationproviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Profile("insecure")
public class RawSQLAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            DBUser user = retrieveUser(authentication.getName(), authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(user.username, user.password, null);
        } catch (IncorrectResultSizeDataAccessException exception) {
            throw new BadCredentialsException("Unauthorized");
        }
    }

    private DBUser retrieveUser(String name, Object password) {
        return jdbcTemplate
                .queryForObject(
                        "Select * from USERS where username='" + name + "' and password='" + password + "'",
                        BeanPropertyRowMapper.newInstance(DBUser.class));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    /**
     * We don't want to expose this class, it holds the password and is reserved for the authentication process
     */
    private static class DBUser {
        String username;
        String password;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
