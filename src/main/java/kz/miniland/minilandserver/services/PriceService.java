package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.RequestCreatePriceDto;
import kz.miniland.minilandserver.dtos.ResponsePriceDto;

import java.util.List;

public interface PriceService {
    List<ResponsePriceDto> getAllPrices();

    void createPrice(RequestCreatePriceDto requestCreatePriceDto);

    void deletePriceById(Long id);
}
