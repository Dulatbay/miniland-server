package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.RequestCreateOrderDto;
import kz.miniland.minilandserver.dtos.ResponseCardOrderDto;
import kz.miniland.minilandserver.dtos.ResponseDetailOrderDto;
import kz.miniland.minilandserver.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@RequestBody @Valid RequestCreateOrderDto requestCreateOrderDto) {
        var token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        requestCreateOrderDto.setAuthorId(token.getName());
        orderService.createOrder(requestCreateOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all-cards")
    public ResponseEntity<List<ResponseCardOrderDto>> getAllCards() {
        return ResponseEntity.ok(orderService.getOrderCards());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseDetailOrderDto> getOrderByOd(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getDetailOrderById(id));
    }
}
