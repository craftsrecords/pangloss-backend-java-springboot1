package org.owasp.pangloss.infra.security.authentication.providers;

/**
 * We don't want to expose this class, it holds the password and is reserved for the authentication process
 */
class DBUser {
    private String username;
    private String password;

    String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
