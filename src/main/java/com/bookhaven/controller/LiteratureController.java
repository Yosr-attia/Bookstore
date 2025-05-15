package com.bookhaven.controller;
import com.bookhaven.service.LiteratureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LiteratureController {
    private final LiteratureService literatureService;

    public LiteratureController(LiteratureService literatureService) {
        this.literatureService = literatureService;
    }

    @GetMapping("/books")
    public String showBooks(@RequestParam(required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("literatureList", literatureService.searchLiterature(query));
        } else {
            model.addAttribute("literatureList", literatureService.findAll());
        }
        return "books";
    }

    @GetMapping("/book/{id}")
    public String showBookDetail(@PathVariable Long id, Model model) {
        model.addAttribute("literature", literatureService.findById(id));
        return "book-detail";
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("literatureList", literatureService.findAll());
        return "home";
    }
}