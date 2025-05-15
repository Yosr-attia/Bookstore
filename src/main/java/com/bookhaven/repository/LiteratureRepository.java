package com.bookhaven.repository;

import com.bookhaven.entity.Literature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LiteratureRepository extends JpaRepository<Literature, Long> {
    @Query("SELECT l FROM Literature l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(l.author) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Literature> searchLiterature(@Param("query") String query);
}
