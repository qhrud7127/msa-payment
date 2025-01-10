package com.vtw.dna.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtw.dna.kakaopay.domain.ApproveRequest;
import com.vtw.dna.kakaopay.domain.ApproveResponse;
import com.vtw.dna.kakaopay.domain.ReadyRequest;
import com.vtw.dna.kakaopay.domain.ReadyResponse;
import com.vtw.dna.order.domain.Orders;
import com.vtw.dna.order.repository.OrderRepository;
import com.vtw.dna.product.domain.Products;
import com.vtw.dna.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class OrderService {
    @Value("${kakaopay.api.secret.key}")
    private String kakaopaySecretKey;

    @Value("${cid}")
    private String cid;

    @Value("${front.host}")
    private String frontHost;

    private String tid;

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ReadyResponse ready(String agent, String openType, Long productId) {
        Products product = productRepository.findById(productId).orElseThrow();

        // Request header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "DEV_SECRET_KEY " + kakaopaySecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request param
        ReadyRequest readyRequest = ReadyRequest.builder()
          .cid(cid)
          .partnerOrderId("1")
          .partnerUserId("1")
          .itemName(product.getName())
          .quantity(1)
          .totalAmount(product.getPrice().intValue())
          .taxFreeAmount(0)
          .vatAmount(100)
          .approvalUrl(frontHost + "/approve/" + agent + "/" + openType)
          .cancelUrl(frontHost + "/cancel/" + agent + "/" + openType)
          .failUrl(frontHost + "/fail/" + agent + "/" + openType).build();

        // Send reqeust
        HttpEntity<ReadyRequest> entityMap = new HttpEntity<>(readyRequest, headers);
        ResponseEntity<ReadyResponse> response = new RestTemplate().postForEntity("https://open-api.kakaopay.com/online/v1/payment/ready", entityMap, ReadyResponse.class);
        ReadyResponse readyResponse = response.getBody();

        // 주문번호와 TID를 매핑해서 저장해놓는다.
        // Mapping TID with partner_order_id then save it to use for approval request.
        this.tid = readyResponse.getTid();
        return readyResponse;
    }

    public String approve(String pgToken) {
        // ready할 때 저장해놓은 TID로 승인 요청
        // Call “Execute approved payment” API by pg_token, TID mapping to the current payment transaction and other parameters.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + kakaopaySecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request param
        ApproveRequest approveRequest = ApproveRequest.builder()
          .cid(cid)
          .tid(tid)
          .partnerOrderId("1")
          .partnerUserId("1")
          .pgToken(pgToken)
          .build();

        // Send Request
        HttpEntity<ApproveRequest> entityMap = new HttpEntity<>(approveRequest, headers);
        try {
            ResponseEntity<String> response = new RestTemplate().postForEntity("https://open-api.kakaopay.com/online/v1/payment/approve", entityMap, String.class);

            // 승인 결과를 저장한다.
            // save the result of approval
            String approveResponse = response.getBody();
            return approveResponse;
        } catch (HttpStatusCodeException ex) {
            return ex.getResponseBodyAsString();
        }
    }

    public void ready(String tid, Long productId) {
        Orders order = new Orders();
        order.setTid(tid);
        order.setQuantity(1L); // 수량 임시 고정
        order.setUserId("bokyungkim"); // 수량 임시 고정
        order.setProducts(productRepository.findById(productId).orElseThrow());
        order.setStatus("ready");
        orderRepository.save(order);
    }

    public void approval(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ApproveResponse approveResponse = mapper.readValue(response, ApproveResponse.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(approveResponse.getApprovedAt(), formatter);

        Orders order = orderRepository.findByTid(tid);
        order.setStatus("approve");
        order.setOrderDateTime(dateTime);

        orderRepository.save(order);
    }

}
