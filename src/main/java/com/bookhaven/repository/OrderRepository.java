package com.bookhaven.repository;

import com.bookhaven.entity.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<BookOrder, Long> {
}