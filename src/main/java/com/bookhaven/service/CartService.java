package com.bookhaven.service;
import com.bookhaven.entity.Literature;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private static final String CART_ATTRIBUTE = "cart";

    public Map<Long, Integer> getCart(HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, Long literatureId, int quantity, LiteratureService literatureService) {
        Map<Long, Integer> cart = getCart(session);
        Literature literature = literatureService.findById(literatureId);
        if (literature.getQuantity() >= quantity) {
            cart.put(literatureId, cart.getOrDefault(literatureId, 0) + quantity);
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
    }

    public void updateCartItem(HttpSession session, Long literatureId, int quantity, LiteratureService literatureService) {
        Map<Long, Integer> cart = getCart(session);
        Literature literature = literatureService.findById(literatureId);
        if (quantity <= 0) {
            cart.remove(literatureId);
        } else if (literature.getQuantity() >= quantity) {
            cart.put(literatureId, quantity);
        }
        session.setAttribute(CART_ATTRIBUTE, cart);
    }

    public void removeFromCart(HttpSession session, Long literatureId) {
        Map<Long, Integer> cart = getCart(session);
        cart.remove(literatureId);
        session.setAttribute(CART_ATTRIBUTE, cart);
    }

    public double calculateTotal(Map<Long, Integer> cart, LiteratureService literatureService) {
        return cart.entrySet().stream()
                .mapToDouble(entry -> {
                    Literature lit = literatureService.findById(entry.getKey());
                    return lit.getPrice() * entry.getValue();
                })
                .sum();
    }
}
