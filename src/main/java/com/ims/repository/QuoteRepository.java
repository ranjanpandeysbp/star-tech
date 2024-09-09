package com.ims.repository;

import com.ims.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {
    QuoteEntity findByEmail(String email);
}
