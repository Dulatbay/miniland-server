package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponseSaleDto;
import kz.miniland.minilandserver.entities.Sale;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface SaleMapper extends BaseMapper<Sale, ResponseSaleDto>{
}
