package com.harera.hayat.repository.possession;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.model.possession.PossessionCondition;

public interface PossessionConditionRepository
                extends JpaRepository<PossessionCondition, Long> {
}
