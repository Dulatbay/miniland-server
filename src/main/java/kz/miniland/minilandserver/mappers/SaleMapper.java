package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponsePriceDto;
import kz.miniland.minilandserver.dtos.response.ResponseSaleDto;
import kz.miniland.minilandserver.entities.Price;
import kz.miniland.minilandserver.entities.Sale;
import kz.miniland.minilandserver.entities.WeekDays;
import org.mapstruct.Mapper;

import javax.swing.text.html.parser.Entity;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SaleMapper extends BaseMapper<Sale, ResponseSaleDto> {
    @Override
    default ResponseSaleDto toDto(Sale entity) {
        ResponseSaleDto saleDto = new ResponseSaleDto();
        saleDto.setId(entity.getId());
        saleDto.setEnabled(entity.isEnabled());
        saleDto.setFullPrice(entity.getFullPrice());
        saleDto.setFullTime(entity.getFullTime());
        saleDto.setTitle(entity.getTitle());
        return saleDto;
    }

    @Override
    default Sale toEntity(ResponseSaleDto responsePriceDto) {
        Sale sale = new Sale();
        sale.setId(responsePriceDto.getId());
        sale.setEnabled(responsePriceDto.getEnabled());
        sale.setTitle(responsePriceDto.getTitle());
        sale.setFullTime(responsePriceDto.getFullTime());
        sale.setFullPrice(responsePriceDto.getFullPrice());
        return sale;
    }
}
