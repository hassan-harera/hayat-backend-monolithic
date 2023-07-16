package com.harera.hayat.repository.possession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.possession.PossessionCategory;

@Repository
public interface PossessionCategoryRepository
                extends JpaRepository<PossessionCategory, Long> {
}
