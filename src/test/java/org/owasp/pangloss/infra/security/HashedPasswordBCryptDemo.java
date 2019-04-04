package org.owasp.pangloss.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;

public class HashedPasswordBCryptDemo {

    public static void main(String[] args) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        Stream.of("admin", "pwd").forEach((password) -> {
                    range(1, 5)
                            .forEach(value -> {

                                String encodedPassword = encoder.encode(password);
                                boolean matches = encoder.matches(password, encodedPassword);

                                System.out.println(format("%s matches %s => %s", encodedPassword, password, matches));
                            });
                    System.out.println();
                }
        );

        System.out.println(format("insert into users (username, password) values ('admin', '%s');", encoder.encode("admin")));
        System.out.println(format("insert into users (username, password) values ('user', '%s');", encoder.encode("pwd")));
    }
}
