package com.ims.repository;

import com.ims.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {
    Optional<QuoteEntity> findByEmail(String email);
}
