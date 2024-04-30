package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponseAbonementOrderDto;
import kz.miniland.minilandserver.entities.AbonementOrder;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AbonementOrderMapper extends BaseMapper<AbonementOrder, ResponseAbonementOrderDto>{


    @Override
    default ResponseAbonementOrderDto toDto(AbonementOrder abonementOrder){
        var baseAbonement = abonementOrder.getBaseAbonement();
        var responseAbonementOrderDto = new ResponseAbonementOrderDto();

        responseAbonementOrderDto.setId(abonementOrder.getId());
        responseAbonementOrderDto.setClientName(abonementOrder.getClientName());
        responseAbonementOrderDto.setChildName(abonementOrder.getChildName());
        responseAbonementOrderDto.setChildAge(abonementOrder.getChildAge());
        responseAbonementOrderDto.setCreatedAt(abonementOrder.getCreatedAt());
        responseAbonementOrderDto.setBaseAbonementId(baseAbonement.getId());
        responseAbonementOrderDto.setBaseAbonementDescription(baseAbonement.getDescription());
        responseAbonementOrderDto.setBaseAbonementName(baseAbonement.getTitle());
        responseAbonementOrderDto.setBaseAbonementPrice(baseAbonement.getFullPrice());
        responseAbonementOrderDto.setBaseAbonementTime(baseAbonement.getFullTime());
        responseAbonementOrderDto.setEnabled(abonementOrder.isEnabled());
        responseAbonementOrderDto.setPhoneNumber(abonementOrder.getPhoneNumber());
        responseAbonementOrderDto.setQuantity(abonementOrder.getQuantity());

        return responseAbonementOrderDto;
    }

}
