package com.griesba.brewery.ssm.service;

import com.griesba.brewery.ssm.domain.Payment;
import com.griesba.brewery.ssm.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;
    @BeforeEach
    void setUp() {
        payment = Payment.builder().payment(new BigDecimal(1200)).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        assertEquals("NEW", savedPayment.getState().toString());

        StateMachine sm = paymentService.preAuth(savedPayment.getId());

        System.out.println(savedPayment.getState().toString());
        //assertEquals("PRE_AUTH", savedPayment.getState().toString());

        Payment preAuthPayment = paymentRepository.getOne(savedPayment.getId());

        System.out.println(preAuthPayment.getState().toString());
        //assertEquals("PRE_AUTH", preAuthPayment.getState().toString());

        System.out.println(preAuthPayment);
    }
}
