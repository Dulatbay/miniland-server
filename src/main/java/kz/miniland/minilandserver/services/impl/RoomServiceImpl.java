package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.*;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.custom.RoomOrderCustomMapper;
import kz.miniland.minilandserver.mappers.custom.RoomTariffCustomMapper;
import kz.miniland.minilandserver.repositories.RoomOrderRepository;
import kz.miniland.minilandserver.repositories.RoomTariffRepository;
import kz.miniland.minilandserver.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomTariffRepository roomTariffRepository;
    private final RoomOrderRepository roomOrderRepository;
    private final RoomOrderCustomMapper roomOrderCustomMapper;
    private final RoomTariffCustomMapper roomTariffCustomMapper;

    @Override
    public List<ResponseCardRoomOrderDto> getAllCurrentActiveRooms() {
        LocalDate now = LocalDate.now(ZONE_ID);
        LocalDateTime startOfDay = LocalDateTime.of(now, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(now, LocalTime.MAX);

        var roomOrders = roomOrderRepository.getAllByBookedDayBetween(startOfDay, endOfDay);

        return roomOrders
                .stream()
                .map(roomOrderCustomMapper::toCardDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseCardRoomTariffDto getTariffById(Long id) {
        var roomOrderEntity = roomTariffRepository.findById(id)
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Room order doesn't exist"));
        return roomTariffCustomMapper.toDto(roomOrderEntity);
    }

    @Override
    public List<ResponseCardRoomTariffDto> getAllTariffsByEnabled(Boolean enabled) {
        var roomTariff = roomTariffRepository.getAllByEnabled(enabled);
        return roomTariff
                .stream()
                .map(roomTariffCustomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseBookedDayDto> getBookedDaysAfterToday() {
        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        var bookedRoomOrders = roomOrderRepository.getAllByBookedDayAfter(now);
        return bookedRoomOrders
                .stream()
                .map(roomOrderCustomMapper::toBookedDayDto)
                .toList();
    }

    @Override
    public ResponseDetailRoomOrderDto getOrderDetailById(Long id) {
        var orderRoom = roomOrderRepository.findById(id).orElseThrow(()->new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Room order doesn't exist"))
        var orderRoomDetail = roomOrderCustomMapper.toDetailDto(orderRoom);
        orderRoomDetail.setTariffDto(roomTariffCustomMapper.toDto(orderRoom.getRoomTariff()));
        return orderRoomDetail;
    }

    @Override
    public void createRoomOrder(RequestCreateRoomOrderDto requestCreateRoomOrderDto) {
        // todo: tomorrow
    }
}
