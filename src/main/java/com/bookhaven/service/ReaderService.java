package com.bookhaven.service;

import com.bookhaven.entity.Reader;
import com.bookhaven.repository.ReaderRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;

    public ReaderService(ReaderRepository readerRepository, PasswordEncoder passwordEncoder) {
        this.readerRepository = readerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveReader(Reader reader) {
        reader.setPassword(passwordEncoder.encode(reader.getPassword()));
        reader.setRole("USER");
        readerRepository.save(reader);
    }

    public Reader findByUsername(String username) {
        return readerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Reader not found"));
    }
}
