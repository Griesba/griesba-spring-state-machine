package com.griesba.brewery.ssm.service;

import com.griesba.brewery.ssm.domain.Payment;
import com.griesba.brewery.ssm.domain.PaymentEvent;
import com.griesba.brewery.ssm.domain.PaymentState;
import com.griesba.brewery.ssm.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    public static final String PAYMENT_ID = "payment_id";
    private final PaymentRepository paymentRepository;
    private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    @Override
    public Payment newPayment(Payment payment) {
        payment.setState(PaymentState.NEW);
        return paymentRepository.save(payment);
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
        sendEvent(paymentId, sm, PaymentEvent.PRE_AUTHORIZE);
        return sm;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
        sendEvent(paymentId, sm, PaymentEvent.PRE_AUTHORIZE_APPROVED);
        return sm;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> declinePayment(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
        sendEvent(paymentId, sm, PaymentEvent.AUTHORIZE_DECLINED);
        return sm;
    }


    private void sendEvent(Long paymentId, StateMachine<PaymentState, PaymentEvent> sm, PaymentEvent event) {
        Message message = MessageBuilder.withPayload(event)
                .setHeader(PAYMENT_ID, paymentId)
                .build();
        sm.sendEvent(message);
    }
    private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
        Payment payment = paymentRepository.getOne(paymentId);

        StateMachine<PaymentState, PaymentEvent> sm = stateMachineFactory.getStateMachine(Long.toString(payment.getId()));

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                            sma.resetStateMachine(
                                    new DefaultStateMachineContext<>(
                                            payment.getState(),
                                            null,
                                            null,
                                            null
                                    )
                            );
                        }
                );

        sm.start();

        return sm;
    }
}
