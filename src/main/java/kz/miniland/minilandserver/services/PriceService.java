package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreatePriceDto;
import kz.miniland.minilandserver.dtos.response.ResponsePriceDto;

import java.util.List;

public interface PriceService {
    List<ResponsePriceDto> getAllPrices();

    void createPrice(RequestCreatePriceDto requestCreatePriceDto);

    void deletePriceById(Long id);
}
