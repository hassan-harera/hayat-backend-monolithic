package com.harera.hayat.repository.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.otp.OtpTransaction;

@Repository
public interface OtpTransactionRepository extends JpaRepository<OtpTransaction, Long> {
}
