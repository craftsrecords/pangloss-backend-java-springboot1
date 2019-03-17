package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.domain.user.User;
import org.owasp.pangloss.infra.entities.CartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController("profile")
@RequestMapping("api/profile")
public class ProfileController {

    @Autowired
    private CrudRepository<? extends CartEntity, UUID> cartRepository;

    @GetMapping
    public ResponseEntity<User> profile(Principal principal) {
        return ResponseEntity.ok(new User(principal.getName()));
    }

    @GetMapping(path = "/cart")
    public ResponseEntity<CartEntity> cart(Principal principal) {
        System.out.println(principal.getName());
        Stream<? extends CartEntity> carts = StreamSupport.stream(cartRepository.findAll().spliterator(), false);
        CartEntity userCart = carts.filter(cart -> {
            System.out.println(cart.getOwner());
            return principal.getName().equals(cart.getOwner());
        }).findFirst().get();
        return ResponseEntity.ok(userCart);
    }
}
