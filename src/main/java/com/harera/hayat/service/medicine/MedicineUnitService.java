package com.harera.hayat.service.medicine;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.medicine.MedicineUnit;
import com.harera.hayat.model.medicine.MedicineUnitDto;
import com.harera.hayat.repository.repository.MedicineUnitRepository;
import com.harera.hayat.util.ObjectMapperUtils;

@Service
public class MedicineUnitService {

    private final MedicineUnitRepository medicineUnitRepository;
    private final ModelMapper modelMapper;

    public MedicineUnitService(MedicineUnitRepository medicineUnitRepository,
                    ModelMapper modelMapper) {
        this.medicineUnitRepository = medicineUnitRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable("medicine_units")
    public List<MedicineUnitDto> list() {
        List<MedicineUnit> medicineUnitList = medicineUnitRepository.findAll();
        return ObjectMapperUtils.mapAll(medicineUnitList, MedicineUnitDto.class);
    }

    @Cacheable("medicine_units#id")
    public MedicineUnitDto get(long id) {
        MedicineUnit medicineUnit = medicineUnitRepository.findById(id).orElseThrow();
        return modelMapper.map(medicineUnit, MedicineUnitDto.class);
    }
}
