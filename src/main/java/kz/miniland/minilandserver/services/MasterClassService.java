package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateMasterClassDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;

import java.io.IOException;
import java.util.List;

public interface MasterClassService {
    void addMasterClassToOrder(String token, Long orderId, Long masterClassId);

    void createMasterClass(RequestCreateMasterClassDto requestCreateMasterClassDto) throws IOException;

    void disableMasterClass(Long id);

    List<ResponseCardMasterClassDto> getAllMasterClasses(boolean enabled);

    ResponseCardMasterClassDto getMasterClassById(Long id);
}
