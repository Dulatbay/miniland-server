package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import kz.miniland.minilandserver.dtos.request.RequestCreateSaleDto;
import kz.miniland.minilandserver.dtos.request.RequestCreateSaleWithPercentDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleWithPercentDto;
import kz.miniland.minilandserver.services.SaleService;
import kz.miniland.minilandserver.services.impl.SaleWithPercentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales-with-percent")
@RequiredArgsConstructor
public class SaleWithPercentController {

    private final SaleWithPercentServiceImpl saleWithPercentService;

    @GetMapping()
    public ResponseEntity<List<ResponseSaleWithPercentDto>> getAllSalesWithPercent(@PathParam("enabled") Boolean enabled) {

        return ResponseEntity
                .ok(saleWithPercentService.getAllSalesWithPercent(enabled));

    }

    @PostMapping()
    public ResponseEntity<Void> createSaleWithPercent(
            @RequestBody @Valid RequestCreateSaleWithPercentDto requestCreateSaleWithPercentDto) {

        saleWithPercentService
                .createSaleWithPercent(requestCreateSaleWithPercentDto);

        return ResponseEntity
                .status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable("id") Long id) {

        saleWithPercentService.deleteSaleWithPercent(id);

        return ResponseEntity
                .status(HttpStatus.OK).build();

    }

}
