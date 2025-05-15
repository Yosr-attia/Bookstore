package com.bookhaven.service;

import com.bookhaven.entity.Literature;
import com.bookhaven.repository.LiteratureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteratureService {
    private final LiteratureRepository literatureRepository;

    public LiteratureService(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    public List<Literature> findAll() {
        return literatureRepository.findAll();
    }

    public Literature findById(Long id) {
        return literatureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Literature not found"));
    }

    public List<Literature> searchLiterature(String query) {
        return literatureRepository.searchLiterature(query);
    }

    public void save(Literature literature) {
        literatureRepository.save(literature);
    }

    public void deleteById(Long id) {
        literatureRepository.deleteById(id);
    }
}