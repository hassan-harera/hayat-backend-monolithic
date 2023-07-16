package com.harera.hayat.repository.clothing;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.harera.hayat.model.clothing.ClothingDonation;

public interface ClothingDonationRepository
                extends JpaRepository<ClothingDonation, Long> {

    @Query("SELECT d FROM ClothingDonation d WHERE d.status = 'ACTIVE' "
                    + "AND d.donationExpirationDate > CURRENT_TIMESTAMP "
                    + "AND (d.title LIKE %:query% OR d.description LIKE %:query%)")
    List<ClothingDonation> search(@Param("query") String query, PageRequest of);
}
