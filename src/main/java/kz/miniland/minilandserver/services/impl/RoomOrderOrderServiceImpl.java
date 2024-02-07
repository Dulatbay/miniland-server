package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomOrderDto;
import kz.miniland.minilandserver.entities.RoomOrder;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.mappers.custom.RoomOrderCustomMapper;
import kz.miniland.minilandserver.mappers.custom.RoomTariffCustomMapper;
import kz.miniland.minilandserver.repositories.RoomOrderRepository;
import kz.miniland.minilandserver.repositories.RoomTariffRepository;
import kz.miniland.minilandserver.services.RoomOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomOrderOrderServiceImpl implements RoomOrderService {
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
    public List<ResponseBookedDayDto> getBookedDaysAfterToday() {
        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        var bookedRoomOrders = roomOrderRepository.getAllByBookedDayAfter(now);
        return bookedRoomOrders
                .stream()
                .map(roomOrderCustomMapper::toBookedDayDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDetailRoomOrderDto getOrderDetailById(Long id) {
        var orderRoom = roomOrderRepository.findById(id).orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Room order doesn't exist"));
        var orderRoomDetail = roomOrderCustomMapper.toDetailDto(orderRoom);
        orderRoomDetail.setTariffDto(roomTariffCustomMapper.toDto(orderRoom.getRoomTariff()));
        return orderRoomDetail;
    }

    @Override
    public void createRoomOrder(RequestCreateRoomOrderDto requestCreateRoomOrderDto) {
        var roomTariff = roomTariffRepository.findById(requestCreateRoomOrderDto.getTariffId())
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Room tariff doesn't exist"));

        var roomOrder = new RoomOrder();

        roomOrder.setAuthorName(requestCreateRoomOrderDto.getAuthorName());
        roomOrder.setRoomTariff(roomTariff);
        roomOrder.setDayOfBooking(LocalDateTime.now(ZONE_ID));

        roomOrder.setClientName(requestCreateRoomOrderDto.getClientName());
        roomOrder.setBookedDay(requestCreateRoomOrderDto.getSelectedBookedDay());
        roomOrder.setChildQuentity(requestCreateRoomOrderDto.getChildCount());


        var tariffTime = roomTariff.getStartedAt().until(roomTariff.getFinishedAt(), ChronoUnit.SECONDS);
        roomOrder.setFullTime(requestCreateRoomOrderDto.getExtraTime() + tariffTime);

        var extraTimeChildPrice = ((requestCreateRoomOrderDto.getChildCount() - roomTariff.getMaxChild()) * roomTariff.getChildPrice());
        var fullPriceByExtraTime = getFullPriceByExtraTime(
                requestCreateRoomOrderDto.getExtraTime(),
                roomTariff.getPenaltyPerHalfHour(),
                roomTariff.getPenaltyPerHour()
        );

        roomOrder.setFullPrice(roomTariff.getFirstPrice() + extraTimeChildPrice + (fullPriceByExtraTime * requestCreateRoomOrderDto.getChildCount()));

        roomOrderRepository.save(roomOrder);
    }


    private Double getFullPriceByExtraTime(Long extraTime, Double penaltyPerHalfHour, Double penaltyPerHour) {
        var extraTimePrice = 0.0;
        var hour = 60 * 60;

        while (extraTime - hour > 0) {
            extraTimePrice += penaltyPerHour;
            extraTime -= hour;
        }
        if (extraTime > 0) {
            var halfHour = 30 * 60;
            while (extraTime > 0) {
                extraTimePrice += penaltyPerHalfHour;
                extraTime -= halfHour;
            }
        }

        return extraTimePrice;
    }

}
