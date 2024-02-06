package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;

import java.util.List;

public interface RoomService {

    List<ResponseCardRoomOrderDto> getAllCurrentActiveRooms();

    ResponseCardRoomTariffDto getTariffById(Long id);

    List<ResponseCardRoomTariffDto> getAllTariffsByEnabled(Boolean enabled);

    List<ResponseBookedDayDto> getBookedDaysAfterDay();
}
