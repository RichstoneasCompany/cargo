package com.richstone.cargo.repository;

import com.richstone.cargo.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByCode(String otp);

}

