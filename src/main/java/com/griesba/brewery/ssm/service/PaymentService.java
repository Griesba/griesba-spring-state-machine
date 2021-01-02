package com.griesba.brewery.ssm.service;

import com.griesba.brewery.ssm.domain.Payment;
import com.griesba.brewery.ssm.domain.PaymentEvent;
import com.griesba.brewery.ssm.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {
    Payment newPayment(Payment payment);

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> declinePayment(Long paymentId);
}
