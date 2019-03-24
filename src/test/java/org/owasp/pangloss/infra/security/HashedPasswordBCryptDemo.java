package org.owasp.pangloss.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;

public class HashedPasswordBCryptDemo {

    public static void main(String[] args) {
        String password = "pwd";

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        range(1, 5)
                .forEach(value -> {

                    String encodedPassword = encoder.encode(password);
                    boolean matches = encoder.matches(password, encodedPassword);

                    System.out.println(format("%s matches %s => %s", encodedPassword, password, matches));
                });
    }
}
