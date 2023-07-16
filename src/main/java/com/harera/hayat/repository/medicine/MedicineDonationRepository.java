package com.harera.hayat.repository.medicine;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.medicine.MedicineDonation;

@Repository
public interface MedicineDonationRepository
                extends JpaRepository<MedicineDonation, Long> {

    @Query("SELECT d FROM MedicineDonation d WHERE d.status = 'ACTIVE' "
                    + "AND d.donationExpirationDate > CURRENT_TIMESTAMP "
                    + "AND (d.title LIKE %:query% OR d.description LIKE %:query%)")
    List<MedicineDonation> search(String query, Pageable pageable);
}
