package com.harera.hayat.repository.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.property.PropertyDonation;

@Repository
public interface PropertyDonationRepository
                extends JpaRepository<PropertyDonation, Long> {
}
