package com.harera.hayat.repository.clothing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.clothing.ClothingSeason;

@Repository
public interface ClothingSeasonRepository extends JpaRepository<ClothingSeason, Long> {
}
