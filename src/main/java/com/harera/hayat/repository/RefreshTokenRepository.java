package com.harera.hayat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.auth.Token;

@Repository
public interface RefreshTokenRepository extends CrudRepository<Token, Long> {

}
