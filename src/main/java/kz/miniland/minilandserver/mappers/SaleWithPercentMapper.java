package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponseSaleWithPercentDto;
import kz.miniland.minilandserver.entities.SaleWithPercent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleWithPercentMapper extends BaseMapper<SaleWithPercent, ResponseSaleWithPercentDto>{
    
    @Override
    default ResponseSaleWithPercentDto toDto(SaleWithPercent entity) {
        
        if(entity == null) return null;
        
        ResponseSaleWithPercentDto saleWithPercentDto = new ResponseSaleWithPercentDto();
        
        saleWithPercentDto.setId(entity.getId());
        saleWithPercentDto.setEnabled(entity.isEnabled());
        saleWithPercentDto.setTitle(entity.getTitle());
        saleWithPercentDto.setPercent(entity.getPercent());
        
        return saleWithPercentDto;
    }

    @Override
    default SaleWithPercent toEntity(ResponseSaleWithPercentDto saleWithPercentDto) {
        
        if(saleWithPercentDto == null) return null;
        
        SaleWithPercent sale = new SaleWithPercent();
        
        sale.setId(saleWithPercentDto.getId());
        sale.setEnabled(saleWithPercentDto.isEnabled());
        sale.setTitle(saleWithPercentDto.getTitle());
        sale.setPercent(saleWithPercentDto.getPercent());
        
        return sale;
    }
    
}
