package com.griesba.brewery.ssm.action;

import com.griesba.brewery.ssm.domain.PaymentEvent;
import com.griesba.brewery.ssm.domain.PaymentState;
import com.griesba.brewery.ssm.service.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class AuthApprovedAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> context) {
        log.info("Auth was called");
        if (new Random().nextInt(10) < 8){
            log.info("AUTHORIZE Approved");
            context.getStateMachine()
                    .sendEvent(
                            MessageBuilder.withPayload(PaymentEvent.AUTHORIZE_APPROVED)
                                    .setHeader(PaymentServiceImpl.PAYMENT_ID, context.getMessageHeader(PaymentServiceImpl.PAYMENT_ID))
                                    .build()
                    );

        } else {
            log.info("AUTHORIZE DECLINED Client not credit");
            context.getStateMachine()
                    .sendEvent(
                            MessageBuilder.withPayload(PaymentEvent.AUTHORIZE_DECLINED)
                                    .setHeader(PaymentServiceImpl.PAYMENT_ID, context.getMessageHeader(PaymentServiceImpl.PAYMENT_ID))
                                    .build()
                    );
        }
    }
}
