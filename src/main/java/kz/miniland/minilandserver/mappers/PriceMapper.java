package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.ResponsePriceDto;
import kz.miniland.minilandserver.entities.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper extends BaseMapper<Price, ResponsePriceDto> {
}
