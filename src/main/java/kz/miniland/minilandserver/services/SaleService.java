package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateSaleDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleDto;

import java.util.List;

public interface SaleService {
    List<ResponseSaleDto> getAll();

    void createSale(RequestCreateSaleDto requestCreateSaleDto);

    void deleteSale(Long id);
}
