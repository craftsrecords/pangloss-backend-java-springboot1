package org.owasp.pangloss.infra.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("mitigated")
@RestController
@CrossOrigin("https://localhost:11759")
public class CsrfController {

    @RequestMapping("/api/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}