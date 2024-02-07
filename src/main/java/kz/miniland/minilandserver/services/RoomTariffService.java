package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;

import java.util.List;

public interface RoomTariffService {
    ResponseCardRoomTariffDto getTariffById(Long id);

    List<ResponseCardRoomTariffDto> getAllTariffsByEnabled(Boolean enabled);
}
