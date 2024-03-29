package com.harera.hayat.service.otp;

import static java.util.Objects.requireNonNull;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.ExpiredOtpException;
import com.harera.hayat.exception.InvalidOtpException;
import com.harera.hayat.model.otp.OTP;
import com.harera.hayat.repository.otp.OtpRepository;
import com.harera.hayat.service.sms.SmsService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OtpService {

    private final OtpRepository otpCacheRepository;
    private final SmsService smsService;

    public OtpService(OtpRepository otpCacheRepository, SmsService smsService) {
        this.otpCacheRepository = otpCacheRepository;
        this.smsService = smsService;
    }

    public void request(String mobile) {
        requireNonNull(mobile);
        //        generate(mobile);
        OTP otp = new OTP(LocalDateTime.now(), mobile, "000000");
        otpCacheRepository.save(otp);
    }

    public void validate(String mobile, String otp) {
        requireNonNull(mobile);
        requireNonNull(otp);
        OTP savedOtp = otpCacheRepository.findById(mobile).orElse(null);
        if (savedOtp == null) {
            throw new ExpiredOtpException(mobile, otp);
        } else if (StringUtils.equals(savedOtp.getOtp(), otp)) {
            return;
        }
        throw new InvalidOtpException(mobile, otp);
    }

    private String generate(String mobile) {
        requireNonNull(mobile);

        final String otpCode = generateOtp();
        if (otpCode != null) {
            OTP otp = new OTP(LocalDateTime.now(), mobile, "000000");
            otpCacheRepository.save(otp);
        }
        return otpCode;
    }

    private String generateOtp() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder otp = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                otp.append(random.nextInt(10));
            }
            return otp.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }
}
