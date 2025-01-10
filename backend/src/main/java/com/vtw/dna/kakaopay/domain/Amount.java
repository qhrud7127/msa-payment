package com.vtw.dna.kakaopay.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Amount {
    private Long total;
    private Long taxFree;
    private Long vat;
    private Long point;
    private Long discount;
    private Long greenDeposit;
}
