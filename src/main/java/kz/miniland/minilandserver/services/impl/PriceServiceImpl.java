package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreatePriceDto;
import kz.miniland.minilandserver.dtos.response.ResponsePriceDto;
import kz.miniland.minilandserver.entities.Price;
import kz.miniland.minilandserver.entities.WeekDays;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.PriceMapper;
import kz.miniland.minilandserver.repositories.PriceRepository;
import kz.miniland.minilandserver.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public List<ResponsePriceDto> getAllPrices() {
        return priceMapper.toDTO(priceRepository.findAllByOrderByFullPriceDesc());
    }

    @Override
    public void createPrice(RequestCreatePriceDto requestCreatePriceDto) {
        if(requestCreatePriceDto.getDays().isEmpty())
            throw new IllegalArgumentException("Available days is empty");
        Price price = new Price();
        price.setFullPrice(requestCreatePriceDto.getFullPrice());
        price.setFullTime(requestCreatePriceDto.getFullTime());
        price.setDays(requestCreatePriceDto.getDays().stream().map(WeekDays::getByInteger).collect(Collectors.toSet()));
        priceRepository.save(price);
    }

    @Override
    public void deletePriceById(Long id) {
        var price = priceRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Price doesn't exist"));
        priceRepository.delete(price);
    }
}
