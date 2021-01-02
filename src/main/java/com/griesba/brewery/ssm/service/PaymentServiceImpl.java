package com.griesba.brewery.ssm.service;

import com.griesba.brewery.ssm.domain.Payment;
import com.griesba.brewery.ssm.domain.PaymentEvent;
import com.griesba.brewery.ssm.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public Payment newPayment(Payment payment) {
        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId) {
        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> declinePayment(Long paymentId) {
        return null;
    }
}
