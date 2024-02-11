package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomTariffDto;

import java.util.List;

public interface RoomTariffService {
    ResponseDetailRoomTariffDto getTariffById(Long id);

    List<ResponseCardRoomTariffDto> getAllTariffsByEnabled(Boolean enabled);

    void create(RequestCreateTariffDto requestCreateTariffDto);

    void disableRoomTariffById(Long id);
}
