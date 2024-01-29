package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.RequestCreatePriceDto;
import kz.miniland.minilandserver.dtos.ResponsePriceDto;
import kz.miniland.minilandserver.entities.Price;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.PriceMapper;
import kz.miniland.minilandserver.repositories.PriceRepository;
import kz.miniland.minilandserver.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public List<ResponsePriceDto> getAllPrices() {
        return priceMapper.toDTO(priceRepository.findAllByEnabledOrderByFullPriceDesc(true));
    }

    @Override
    public void createPrice(RequestCreatePriceDto requestCreatePriceDto) {
        Price price = new Price();
        price.setFullPrice(requestCreatePriceDto.getFullPrice());
        price.setFullTime(requestCreatePriceDto.getFullTime());
        price.setEnabled(true);
        priceRepository.save(price);
    }

    @Override
    public void deletePriceById(Long id) {
        var price = priceRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_GATEWAY.getReasonPhrase(), "Price doesn't exist"));
        price.setEnabled(false);
        priceRepository.save(price);
    }
}
