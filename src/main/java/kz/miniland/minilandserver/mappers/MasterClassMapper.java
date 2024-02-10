package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;
import kz.miniland.minilandserver.entities.MasterClass;
import org.mapstruct.Mapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Mapper(componentModel = "spring")
public interface MasterClassMapper extends BaseMapper<MasterClass, ResponseCardMasterClassDto> {

    @Override
    default ResponseCardMasterClassDto toDto(MasterClass entity) {
        ResponseCardMasterClassDto dto = new ResponseCardMasterClassDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        if (entity.getImageUrl() != null)
            dto.setImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/files/" + entity.getImageUrl());
        return dto;
    }
}
