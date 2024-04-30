package com.richstone.cargo.repository;

import com.richstone.cargo.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {
}
