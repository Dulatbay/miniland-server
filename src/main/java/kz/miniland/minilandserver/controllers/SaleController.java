package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import kz.miniland.minilandserver.dtos.request.RequestCreateSaleDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleDto;
import kz.miniland.minilandserver.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
class SaleController {
    private final SaleService saleService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseSaleDto>> getAll(@PathParam("enabled") Boolean enabled){
        return ResponseEntity.ok(saleService.getAll(enabled));
    }

    @PostMapping("/")
    public ResponseEntity<Void> createSale(@RequestBody @Valid RequestCreateSaleDto requestCreateSaleDto){
        saleService.createSale(requestCreateSaleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable("id") Long id){
        saleService.deleteSale(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
