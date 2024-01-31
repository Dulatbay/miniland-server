package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponsePriceDto;
import kz.miniland.minilandserver.entities.Price;
import kz.miniland.minilandserver.entities.WeekDays;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PriceMapper extends BaseMapper<Price, ResponsePriceDto> {
    @Override
    default ResponsePriceDto toDto(Price entity) {
        ResponsePriceDto responsePriceDto = new ResponsePriceDto();
        responsePriceDto.setId(entity.getId());
        responsePriceDto.setFullPrice(entity.getFullPrice());
        responsePriceDto.setFullTime(entity.getFullTime());
        responsePriceDto.setDays(entity.getDays().stream().map(WeekDays::getInteger).collect(Collectors.toSet()));
        return responsePriceDto;
    }

    @Override
    default Price toEntity(ResponsePriceDto responsePriceDto) {
        Price price = new Price();
        price.setId(responsePriceDto.getId());
        price.setDays(responsePriceDto.getDays().stream().map(WeekDays::getByInteger).collect(Collectors.toSet()));
        price.setFullPrice(responsePriceDto.getFullPrice());
        price.setFullTime(responsePriceDto.getFullTime());
        return price;
    }
}
