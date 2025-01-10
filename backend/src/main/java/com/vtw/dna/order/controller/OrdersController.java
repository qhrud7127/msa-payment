package com.vtw.dna.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtw.dna.kakaopay.domain.ReadyResponse;
import com.vtw.dna.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrdersController {

    private final OrderService orderService;

    @GetMapping("/orders/ready")
    public ReadyResponse ready(@RequestParam Long productId, @RequestParam Long quantity) {
        ReadyResponse readyResponse = orderService.ready("pc", "popup", productId, quantity);
        orderService.readyLog(readyResponse.getTid(), productId, quantity);
        return readyResponse;
    }

    @GetMapping("/orders/approve")
    public String approve(@RequestParam("pg_token") String pgToken) throws JsonProcessingException {
        String approveResponse = orderService.approve(pgToken);
        orderService.approval(approveResponse);
        return approveResponse;
    }

    @GetMapping("/cancel/{agent}/{openType}")
    public String cancel(@PathVariable("agent") String agent, @PathVariable("openType") String openType) {
        // 주문건이 진짜 취소되었는지 확인 후 취소 처리
        // 결제내역조회(/v1/payment/status) api에서 status를 확인한다.
        // To prevent the unwanted request cancellation caused by attack,
        // the “show payment status” API is called and then check if the status is QUIT_PAYMENT before suspending the payment
        return agent + "/" + openType + "/cancel";
    }

    @GetMapping("/fail/{agent}/{openType}")
    public String fail(@PathVariable("agent") String agent, @PathVariable("openType") String openType) {
        // 주문건이 진짜 실패되었는지 확인 후 실패 처리
        // 결제내역조회(/v1/payment/status) api에서 status를 확인한다.
        // To prevent the unwanted request cancellation caused by attack,
        // the “show payment status” API is called and then check if the status is FAIL_PAYMENT before suspending the payment
        return agent + "/" + openType + "/fail";
    }
}
