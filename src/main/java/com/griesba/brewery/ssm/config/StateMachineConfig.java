package com.griesba.brewery.ssm.config;

import com.griesba.brewery.ssm.domain.PaymentEvent;
import com.griesba.brewery.ssm.domain.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@EnableStateMachineFactory
@Service
@Slf4j
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
        states.withStates()
                .initial(PaymentState.NEW)
                .states(EnumSet.allOf(PaymentState.class))
                .end(PaymentState.AUTH)
                .end(PaymentState.AUTH_ERROR)
                .end(PaymentState.PRE_AUTH_ERROR);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentState, PaymentEvent> transitions) throws Exception {

        // Transition 1 : when on NEW if PRE_AUTHORIZE then go to NEW
        transitions
                .withExternal()
                .source(PaymentState.NEW)
                .target(PaymentState.NEW)
                .event(PaymentEvent.PRE_AUTHORIZE)

                .and().withExternal()
                .source(PaymentState.NEW)
                .target(PaymentState.PRE_AUTH)
                .event(PaymentEvent.PRE_AUTHORIZE_APPROVED)

                .and().withExternal()
                .source(PaymentState.NEW)
                .target(PaymentState.PRE_AUTH_ERROR)
                .event(PaymentEvent.PRE_AUTHORIZE_DECLINED)
                ;

    }
}
