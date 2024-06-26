package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import kz.miniland.minilandserver.dtos.request.RequestCreateOrderByAbonementDto;
import kz.miniland.minilandserver.dtos.request.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseOrderCountDto;
import kz.miniland.minilandserver.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<Void> createOrder(@RequestBody @Valid RequestCreateOrderDto requestCreateOrderDto) {
        var token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        requestCreateOrderDto.setAuthorId(token.getToken().getClaim("preferred_username"));
        orderService.createOrder(requestCreateOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/today")
    public ResponseEntity<List<ResponseCardOrderDto>> getTodaysOrderCards() {
        return ResponseEntity.ok(orderService.getTodaysOrderCards());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseDetailOrderDto> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getDetailOrderById(id));
    }

    @GetMapping("/{id}/master-classes")
    public ResponseEntity<List<ResponseCardMasterClassDto>> getMasterClassesByOrderId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getMasterClassesByOrderId(id));
    }

    @PatchMapping("/finish/{id}")
    public ResponseEntity<Void> finishOrderById(@PathVariable("id")
                                                Long id,
                                                @RequestParam(value = "is_paid")
                                                @AssertTrue(message = "Is paid param must be true")
                                                Boolean isPaid) {
        orderService.finishOrderById(id, isPaid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<ResponseOrderCountDto> getCountOfOrdersByPhoneNumber(@RequestParam("phone_number") String phoneNumber){

        return ResponseEntity
                .ok(orderService.getOrderCountByPhoneNumber(phoneNumber));

    }

    @PostMapping("/by-abonement")
    public ResponseEntity<Void> createOrderByAbonement(
            @RequestBody @Valid RequestCreateOrderByAbonementDto requestCreateAbonementOrderDto){

        var token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        requestCreateAbonementOrderDto.setAuthorId(token.getToken().getClaim("preferred_username"));

        orderService.createOrderByAbonement(requestCreateAbonementOrderDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }



}
