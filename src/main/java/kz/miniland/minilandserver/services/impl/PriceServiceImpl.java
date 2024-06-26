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
import org.apache.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        List<ResponsePriceDto> allPrices = priceMapper.toDTO(priceRepository.findAllByEnabledIsTrueOrderByFullPriceDesc());
        log.info("All prices size: {}", allPrices.size());
        return allPrices;
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

                    if(!sameDays.isEmpty() && price.getFullPrice().equals(requestCreatePriceDto.getFullPrice()))
                        throw new IllegalArgumentException("PriceEntity with the same day and price already exists");

                });

        Price price = new Price();
        price.setFullPrice(requestCreatePriceDto.getFullPrice());
        price.setFullTime(requestCreatePriceDto.getFullTime());
        price.setDays(requestCreatePriceDto.getDays().stream().map(WeekDays::getByInteger).collect(Collectors.toSet()));
        price.setEnabled(true);
        log.info("Created new price: {}", price);
        priceRepository.save(price);
    }

    @Override
    public void deletePriceById(Long id) {
        var price = priceRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Price doesn't exist"));

        if (!price.isEnabled())
            throw new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Price doesn't exist or already deleted");

        price.setEnabled(false);
        log.info("Deleting price by id: {}", id);
        priceRepository.save(price);
    }
}
