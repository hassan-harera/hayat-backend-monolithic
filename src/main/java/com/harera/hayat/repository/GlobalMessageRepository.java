package com.harera.hayat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.GlobalMessage;

@Repository
public interface GlobalMessageRepository extends JpaRepository<GlobalMessage, Long> {
    Optional<GlobalMessage> findByLanguageAndCode(String language, String code);
}
