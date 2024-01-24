package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.RequestCreateSaleDto;
import kz.miniland.minilandserver.dtos.ResponseSaleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SaleService {
    List<ResponseSaleDto> getAll();

    void createSale(RequestCreateSaleDto requestCreateSaleDto);

    void deleteSale(Long id);
}
