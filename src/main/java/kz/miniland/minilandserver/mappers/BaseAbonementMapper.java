package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponseBaseAbonementDto;
import kz.miniland.minilandserver.entities.BaseAbonement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseAbonementMapper extends BaseMapper<BaseAbonement, ResponseBaseAbonementDto>{

    @Override
    default ResponseBaseAbonementDto toDto(BaseAbonement baseAbonement){

        return ResponseBaseAbonementDto.builder()
                .id(baseAbonement.getId())
                .title(baseAbonement.getTitle())
                .description(baseAbonement.getDescription())
                .fullTime(baseAbonement.getFullTime())
                .fullPrice(baseAbonement.getFullPrice())
                .createAt(baseAbonement.getCreatedAt())
                .enabled(baseAbonement.isEnabled())
                .build();

    }
}
