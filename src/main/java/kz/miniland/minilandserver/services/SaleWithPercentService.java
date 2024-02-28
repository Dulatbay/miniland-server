package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateSaleWithPercentDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleWithPercentDto;

import java.util.List;

public interface SaleWithPercentService {

    List<ResponseSaleWithPercentDto> getAllSalesWithPercent(Boolean enabled);

    void createSaleWithPercent(RequestCreateSaleWithPercentDto requestCreateSaleWithPercentDto);

    void deleteSaleWithPercent(Long id);

}

