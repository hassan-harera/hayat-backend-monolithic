package com.harera.hayat.service;

import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.Donation;
import com.harera.hayat.model.DonationResponse;
import com.harera.hayat.repository.DonationRepository;
import com.harera.hayat.service.book.BookDonationService;
import com.harera.hayat.service.clothing.ClothingDonationService;
import com.harera.hayat.service.food.FoodDonationService;
import com.harera.hayat.service.medicine.MedicineDonationService;

@Service
public class DonationsService {

    private final DonationRepository donationRepository;
    private final MedicineDonationService medicineDonationService;
    private final FoodDonationService foodDonationService;
    private final BookDonationService bookDonationService;
    private final ClothingDonationService clothingDonationService;

    public DonationsService(DonationRepository donationRepository,
                    MedicineDonationService medicineDonationService,
                    FoodDonationService foodDonationService,
                    BookDonationService bookDonationService,
                    ClothingDonationService clothingDonationService) {
        this.donationRepository = donationRepository;
        this.medicineDonationService = medicineDonationService;
        this.foodDonationService = foodDonationService;
        this.bookDonationService = bookDonationService;
        this.clothingDonationService = clothingDonationService;
    }

    public List<DonationResponse> search(String query, int page) {
        page = Integer.max(page, 1) - 1;
        List<Donation> search = donationRepository.search(query,
                        Pageable.ofSize(16).withPage(page));
        return mapAll(search, DonationResponse.class);
    }

    public void receive(String qrCode) {
        Donation donation = donationRepository.findByQrCode(qrCode).orElseThrow(
                        () -> new EntityNotFoundException("Donation not found"));
        updateDonation(donation);
    }

    private void updateDonation(Donation donation) {
        switch (donation.getCategory()) {
            case MEDICINE:
                medicineDonationService.receive(donation.getId());
                break;
            case FOOD:
                foodDonationService.receive(donation.getId());
                break;
            case BOOKS:
                bookDonationService.receive(donation.getId());
                break;
            case CLOTHING:
                clothingDonationService.receive(donation.getId());
                break;
        }
    }
}
