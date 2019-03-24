package org.owasp.pangloss.infra.security.authentication.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
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
@Conditional(MitigatedWithoutHashedPasswords.class)
/**
 * SQL Injection free implementation of database based authentication process using PreparedStatement.
 *
 * This class exists only for the example, a better approach would be the usage of the Spring built-in method:
 *<code>
 *      @Autowired
 *     public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
 *         auth.jdbcAuthentication()
 *                 .dataSource(dataSource)
 *                 .usersByUsernameQuery("select username,password,enabled from users where username = ?")
 *                 .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
 *     }
 *</code>
 *
 * You can add this piece of code inside your Configuration having @EnableWebSecurity
 * Note that the provided queries in the example are the one expected by spring by default if no queries are specified.
 * So if your database schema is compatible with it, you can simply skip usersByUsernameQuery and authoritiesByUsernameQuery.
 */
public class SQLInjectionSafeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            DBUser user = retrieveUser(authentication.getName(), authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        } catch (IncorrectResultSizeDataAccessException exception) {
            throw new BadCredentialsException("Unauthorized");
        }
    }

    /**
     * The following method is using internally a PreparedStatement which is equivalent of doing something such as
     * <code>
     * PreparedStatement statement = connection.prepareStatement("Select * from users where username=? and password=?");
     * statement.setString(1, name);
     * statement.setString(2, password);
     * </code>
     */
    private DBUser retrieveUser(String name, Object credentials) {
        return jdbcTemplate
                .queryForObject("Select * from users where username=? and password=?",
                        BeanPropertyRowMapper.newInstance(DBUser.class),
                        name, credentials);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
