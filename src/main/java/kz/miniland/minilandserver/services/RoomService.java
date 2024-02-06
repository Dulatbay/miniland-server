package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailOrderDto;

import java.util.List;

public interface RoomService {

    List<ResponseCardRoomOrderDto> getAllCurrentActiveRooms();

    ResponseCardRoomTariffDto getTariffById(Long id);

    List<ResponseCardRoomTariffDto> getAllTariffsByEnabled(Boolean enabled);

    List<ResponseBookedDayDto> getBookedDaysAfterDay();

    ResponseDetailOrderDto getOrderDetailById(Long id);
}
