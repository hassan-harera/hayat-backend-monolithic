package com.harera.hayat.service.city;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.city.CityResponse;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.util.ObjectMapperUtils;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable("cities#id")
    public CityResponse get(long id) {
        City city = cityRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                                        ErrorCode.NOT_FOUND_CITY));
        return modelMapper.map(city, CityResponse.class);
    }

    @Cacheable("state_cities#stateId")
    public List<CityResponse> list(long stateId) {
        List<City> cityList;
        if (stateId != 0) {
            cityList = cityRepository.findByStateId(stateId);
        } else {
            cityList = cityRepository.findAll();
        }
        return cityList.stream().map(city -> modelMapper.map(city, CityResponse.class))
                        .toList();
    }

    @Cacheable("cities")
    public List<CityResponse> list() {
        return ObjectMapperUtils.mapAll(cityRepository.findAll(), CityResponse.class);
    }

    @Cacheable("cities_search#query")
    public List<CityResponse> search(String query) {
        List<City> cities = cityRepository.search(query);
        return cities.stream().map(city -> modelMapper.map(city, CityResponse.class))
                        .toList();
    }

    public City getCity(long id) {
        return cityRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                                        ErrorCode.NOT_FOUND_CITY));
    }
}
