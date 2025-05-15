package com.bookhaven.controller;

import com.bookhaven.entity.Reader;
import com.bookhaven.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("reader", new Reader());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerReader(@ModelAttribute Reader reader) {
        readerService.saveReader(reader);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
