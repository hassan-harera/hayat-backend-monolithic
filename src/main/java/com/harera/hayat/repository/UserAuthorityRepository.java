package com.harera.hayat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.model.user.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
