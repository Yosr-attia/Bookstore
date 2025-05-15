package com.bookhaven.controller;

import com.bookhaven.entity.Literature;
import com.bookhaven.service.LiteratureService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final LiteratureService literatureService;

    public AdminController(LiteratureService literatureService) {
        this.literatureService = literatureService;
    }

    @GetMapping
    public String showAdminPanel(Model model) {
        model.addAttribute("literatureList", literatureService.findAll());
        model.addAttribute("literature", new Literature());
        return "admin";
    }

    @PostMapping("/add")
    public String addLiterature(@ModelAttribute Literature literature) {
        literatureService.save(literature);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("literature", literatureService.findById(id));
        model.addAttribute("literatureList", literatureService.findAll());
        return "admin";
    }

    @PostMapping("/update")
    public String updateLiterature(@ModelAttribute Literature literature) {
        literatureService.save(literature);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteLiterature(@PathVariable Long id) {
        literatureService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/restock/{id}")
    public String restockLiterature(@PathVariable Long id, @RequestParam int quantity) {
        Literature literature = literatureService.findById(id);
        literature.setQuantity(literature.getQuantity() + quantity);
        literatureService.save(literature);
        return "redirect:/admin";
    }
}
