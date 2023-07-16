package com.harera.hayat.repository.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.city.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
