package com.bookhaven.controller;

import com.bookhaven.entity.BookOrder;
import com.bookhaven.entity.PaymentDetails;
import com.bookhaven.entity.Reader;
import com.bookhaven.repository.OrderRepository;
import com.bookhaven.repository.PaymentDetailsRepository;
import com.bookhaven.service.CartService;
import com.bookhaven.service.LiteratureService;
import com.bookhaven.service.ReaderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private final CartService cartService;
    private final LiteratureService literatureService;
    private final ReaderService readerService;
    private final OrderRepository orderRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;

    public OrderController(CartService cartService, LiteratureService literatureService, ReaderService readerService,
                           OrderRepository orderRepository, PaymentDetailsRepository paymentDetailsRepository) {
        this.cartService = cartService;
        this.literatureService = literatureService;
        this.readerService = readerService;
        this.orderRepository = orderRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        Map<Long, Integer> cart = cartService.getCart(session);
        //noinspection DuplicatedCode
        List<Object[]> cartItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            cartItems.add(new Object[]{literatureService.findById(entry.getKey()), entry.getValue()});
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.calculateTotal(cart, literatureService));
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long literatureId, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        cartService.addToCart(session, literatureId, quantity, literatureService);
        return "redirect:/books";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long literatureId, @RequestParam int quantity, HttpSession session) {
        cartService.updateCartItem(session, literatureId, quantity, literatureService);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long literatureId, HttpSession session) {
        cartService.removeFromCart(session, literatureId);
        return "redirect:/cart";
    }



    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model) {
        Map<Long, Integer> cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }
        List<Object[]> cartItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            cartItems.add(new Object[]{literatureService.findById(entry.getKey()), entry.getValue()});
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.calculateTotal(cart, literatureService));
        model.addAttribute("paymentDetails", new PaymentDetails());
        model.addAttribute("order", new BookOrder());
        return "checkout";
    }

    @PostMapping("/order")
    public String placeOrder(@ModelAttribute PaymentDetails paymentDetails, @ModelAttribute BookOrder order, HttpSession session, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Reader reader = readerService.findByUsername(username);
        Map<Long, Integer> cart = cartService.getCart(session);

        if (!cart.isEmpty()) {
            // Vérifier le stock
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                Long literatureId = entry.getKey();
                int quantity = entry.getValue();
                var literature = literatureService.findById(literatureId);
                if (literature.getQuantity() < quantity) {
                    model.addAttribute("error", "Stock insuffisant pour " + literature.getTitle());
                    return "cart";
                }
            }

            // Réduire le stock
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                Long literatureId = entry.getKey();
                int quantity = entry.getValue();
                var literature = literatureService.findById(literatureId);
                literature.setQuantity(literature.getQuantity() - quantity);
                literatureService.save(literature);
            }

            // Sauvegarder la commande
            PaymentDetails savedPayment = paymentDetailsRepository.save(paymentDetails);
            order.setReader(reader);
            List<Long> literatureIds = new ArrayList<>();
            cart.forEach((id, qty) -> {
                for (int i = 0; i < qty; i++) {
                    literatureIds.add(id);
                }
            });
            order.setLiteratureIds(literatureIds);
            order.setTotal(cartService.calculateTotal(cart, literatureService));
            order.setOrderDate(LocalDateTime.now());
            order.setPaymentDetails(savedPayment);
            orderRepository.save(order);

            session.removeAttribute("cart");
            model.addAttribute("order", order);
            List<Object[]> orderedItems = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                orderedItems.add(new Object[]{literatureService.findById(entry.getKey()), entry.getValue()});
            }
            model.addAttribute("orderedItems", orderedItems);
            return "order-confirmation";
        }
        return "redirect:/cart";
    }
}