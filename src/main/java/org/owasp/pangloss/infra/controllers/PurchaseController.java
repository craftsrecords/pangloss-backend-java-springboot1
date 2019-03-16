package org.owasp.pangloss.infra.controllers;

import org.owasp.pangloss.infra.entities.CartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class PurchaseController {

    @Autowired
    private CrudRepository<? extends CartEntity, UUID> cartRepository;

    @GetMapping("/purchases/{cartId}")
    public String purchases(@PathVariable UUID cartId, Model model) {
        model.addAttribute("cart", cartRepository.findOne(cartId));
        return "purchase";
    }
}
