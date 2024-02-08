package kz.miniland.minilandserver.controllers;


import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreatePriceDto;
import kz.miniland.minilandserver.dtos.response.ResponsePriceDto;
import kz.miniland.minilandserver.services.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceService priceService;

    @GetMapping()
    public ResponseEntity<List<ResponsePriceDto>> getAllPrices() {
        return ResponseEntity.ok(priceService.getAllPrices());
    }

    @PostMapping()
    public ResponseEntity<Void> createPrice(@RequestBody @Valid RequestCreatePriceDto requestCreatePriceDto) {
        priceService.createPrice(requestCreatePriceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriceById(@PathVariable(value = "id") Long id) {
        priceService.deletePriceById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

