package com.harera.hayat.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.auth.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

}
