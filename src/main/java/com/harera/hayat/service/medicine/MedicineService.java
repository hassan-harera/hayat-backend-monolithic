package com.harera.hayat.service.medicine;

import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.medicine.Medicine;
import com.harera.hayat.model.medicine.MedicineDto;
import com.harera.hayat.repository.repository.MedicineRepository;


@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;

    public MedicineService(MedicineRepository medicineRepository,
                    ModelMapper modelMapper) {
        this.medicineRepository = medicineRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable("medicines")
    public List<MedicineDto> list(int page) {
        page = Integer.max(page, 1) - 1;
        List<Medicine> medicineList = medicineRepository
                        .findAll(Pageable.ofSize(16).withPage(page)).getContent();
        return mapAll(medicineList, MedicineDto.class);
    }

    @Cacheable("medicines#id")
    public MedicineDto get(long id) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow();
        return modelMapper.map(medicine, MedicineDto.class);
    }

    @Cacheable("medicines#query#page")
    public List<MedicineDto> search(String query, int page) {
        page = Integer.max(page, 1) - 1;
        List<Medicine> medicineList = medicineRepository.search(query,
                        Pageable.ofSize(16).withPage(page));
        return mapAll(medicineList, MedicineDto.class);
    }
}
