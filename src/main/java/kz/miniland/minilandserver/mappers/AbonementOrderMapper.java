package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponseAbonementOrderDto;
import kz.miniland.minilandserver.entities.AbonementOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AbonementOrderMapper extends BaseMapper<AbonementOrder, ResponseAbonementOrderDto>{

    @Override
    default ResponseAbonementOrderDto toDto(AbonementOrder abonementOrder){

        return ResponseAbonementOrderDto.builder()
                .id(abonementOrder.getId())
                .clientName(abonementOrder.getClientName())
                .phoneNumber(abonementOrder.getPhoneNumber())
                .childName(abonementOrder.getChildName())
                .childAge(abonementOrder.getChildAge())
                .quantity(abonementOrder.getQuantity())
                .createdAt(abonementOrder.getCreatedAt())
                .baseAbonementId(abonementOrder.getBaseAbonement().getId())
                .enabled(abonementOrder.isEnabled())
                .build();
    }

}
