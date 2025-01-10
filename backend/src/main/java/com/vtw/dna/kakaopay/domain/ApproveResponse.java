package com.vtw.dna.kakaopay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApproveResponse {
    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partnerOrderId;
    private String partnerUserId;
    private String itemName;
    private String itemCode;
    private String payload;
    private Long quantity;
    private Amount amount;
    private String paymentMethodType;
    private String cardInfo;
    private String sequentialPaymentMethods;
    private String createdAt;
    private String approvedAt;

}
