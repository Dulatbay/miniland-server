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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public List<ResponsePriceDto> getAllPrices() {
        return priceMapper.toDTO(priceRepository.findAllByEnabledIsTrueOrderByFullPriceDesc());
    }

    @Override
    public void createPrice(RequestCreatePriceDto requestCreatePriceDto) {

        if (requestCreatePriceDto.getDays().isEmpty())
            throw new IllegalArgumentException("Available days is empty");

        priceRepository.findAll()
                .forEach(price -> {

                    var sameDays = price
                            .getDays()
                            .stream()
                            .map(WeekDays::getInteger)
                            .filter(day -> requestCreatePriceDto.getDays().contains(day))
                            .toList();

                    if(!sameDays.isEmpty() && price.getFullPrice().equals(requestCreatePriceDto.getFullPrice())){

                        log.error("PriceEntity with the same day and price already exists");

                        throw new BadRequestException("PriceEntity with the same day and price already exists");

                    }

                });

        Price price = new Price();
        price.setFullPrice(requestCreatePriceDto.getFullPrice());
        price.setFullTime(requestCreatePriceDto.getFullTime());
        price.setDays(requestCreatePriceDto.getDays().stream().map(WeekDays::getByInteger).collect(Collectors.toSet()));
        price.setEnabled(true);
        priceRepository.save(price);
    }

    @Override
    public void deletePriceById(Long id) {
        var price = priceRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Price doesn't exist"));

        if (!price.isEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Price doesn't exist or already deleted");

        price.setEnabled(false);
        priceRepository.save(price);
    }
}
