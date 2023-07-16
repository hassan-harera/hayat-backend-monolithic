package com.harera.hayat.repository.otp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.otp.OTP;

@Repository
public interface OtpRepository extends CrudRepository<OTP, String> {

}
