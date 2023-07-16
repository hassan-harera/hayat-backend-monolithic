package com.harera.hayat.service.city;

import static com.harera.hayat.util.ErrorCode.NOT_FOUND_STATE_ID;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.city.State;
import com.harera.hayat.model.city.StateResponse;
import com.harera.hayat.repository.city.StateRepository;

@Service
public class StateService {

    private final StateRepository stateRepository;
    private final ModelMapper mapper;

    @Autowired
    public StateService(StateRepository stateRepository, ModelMapper mapper) {
        this.stateRepository = stateRepository;
        this.mapper = mapper;
    }

    @Cacheable("states#id")
    public StateResponse get(long id) {
        State state = stateRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                                        NOT_FOUND_STATE_ID));
        return mapper.map(state, StateResponse.class);
    }

    @Cacheable("states")
    public List<StateResponse> list() {
        List<State> stateList = stateRepository.findAll();
        return stateList.stream().map(state -> mapper.map(state, StateResponse.class))
                        .collect(Collectors.toList());
    }
}
