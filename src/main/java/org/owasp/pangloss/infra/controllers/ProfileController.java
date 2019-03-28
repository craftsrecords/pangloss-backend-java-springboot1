package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController("profile")
@RequestMapping("api/profile")
public class ProfileController {

    @GetMapping
    public ResponseEntity<User> profile(Principal principal) {
        return ResponseEntity.ok(new User(principal.getName()));
    }
}
