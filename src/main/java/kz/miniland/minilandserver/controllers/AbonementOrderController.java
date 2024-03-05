package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateAbonementOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseAbonementOrderDto;
import kz.miniland.minilandserver.services.AbonementOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abonement-orders")
@RequiredArgsConstructor
public class AbonementOrderController {

    private final AbonementOrderService abonementOrderService;

    @GetMapping()
    public ResponseEntity<List<ResponseAbonementOrderDto>> getAllAbonementOrders(){

        return ResponseEntity
                .ok(abonementOrderService.getAllAbonementOrders());

    }

    @PostMapping
    public ResponseEntity<Void> createAbonementOrder(
            @RequestBody @Valid RequestCreateAbonementOrderDto requestCreateAbonementOrderDto){

        abonementOrderService.createAbonementOrder(requestCreateAbonementOrderDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/order")
    public ResponseEntity<ResponseAbonementOrderDto> getAbonementOrderByPhoneNumber(
            @RequestParam("phone_number") String phoneNumber){

        return ResponseEntity
                .ok(abonementOrderService.getAbonementOrderByPhoneNumber(phoneNumber));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbonementOrderById(@PathVariable("id") Long id){

        abonementOrderService.deleteAbonementOrderById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



}
