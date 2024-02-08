package kz.miniland.minilandserver.mappers;

import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;
import kz.miniland.minilandserver.entities.MasterClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterClassMapper extends BaseMapper<MasterClass, ResponseCardMasterClassDto> {
}
