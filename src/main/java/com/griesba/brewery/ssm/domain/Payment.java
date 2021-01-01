package com.griesba.brewery.ssm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentState state;
    private BigDecimal payment;
}
