package com.griesba.brewery.ssm.repositories;

import com.griesba.brewery.ssm.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
