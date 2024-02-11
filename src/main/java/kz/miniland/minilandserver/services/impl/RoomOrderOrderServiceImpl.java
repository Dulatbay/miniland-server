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

import javax.ws.rs.BadRequestException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
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

        var roomOrders = roomOrderRepository.getAllByBookedDayBetween(now, now.plusDays(7));

        return roomOrders
                .stream()
                .map(roomOrderCustomMapper::toCardDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ResponseBookedDayDto> getBookedDaysAfterToday(Long id) {
        var now = LocalDate.now(ZONE_ID);
        var bookedRoomOrders = roomOrderRepository.getAllByBookedDayAfter(now);


        return bookedRoomOrders
                .stream()
                .filter(i -> Objects.equals(i.getRoomTariff().getId(), id))
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

        if (!roomTariff.getEnabled())
            throw new IllegalArgumentException("Room tariff doesn't exist or disabled");

        var now = LocalDate.now(ZONE_ID);

        log.info("{}",requestCreateRoomOrderDto.getSelectedBookedDay());

        var nextSession = roomOrderRepository.findTopByBookedDayAfterOrderByStartedAt(requestCreateRoomOrderDto.getSelectedBookedDay().minusDays(1));

        log.info("{}", nextSession);

        if (now.isAfter(requestCreateRoomOrderDto.getSelectedBookedDay()))
            throw new IllegalArgumentException("The selected booked day must be in the future.");

        var endedAt = roomTariff.getFinishedAt().plusSeconds(requestCreateRoomOrderDto.getExtraTime());

        log.info("{}", nextSession.isPresent()
                && isSameDate(nextSession.get().getBookedDay(), requestCreateRoomOrderDto.getSelectedBookedDay()));

        if (nextSession.isPresent()
                && isSameDate(nextSession.get().getBookedDay(), requestCreateRoomOrderDto.getSelectedBookedDay())
                && !nextSession.get().getRoomTariff().getStartedAt().isAfter(endedAt))
            throw new IllegalArgumentException("The selected booked day is not available");

        var roomOrder = new RoomOrder();

        roomOrder.setAuthorName(requestCreateRoomOrderDto.getAuthorName());
        roomOrder.setRoomTariff(roomTariff);
        roomOrder.setDayOfBooking(LocalDateTime.now(ZONE_ID));

        roomOrder.setClientName(requestCreateRoomOrderDto.getClientName());
        roomOrder.setBookedDay(requestCreateRoomOrderDto.getSelectedBookedDay());
        roomOrder.setChildQuentity(requestCreateRoomOrderDto.getChildCount());
        roomOrder.setStartedAt(roomTariff.getStartedAt());

        var tariffTime = roomTariff.getStartedAt().until(roomTariff.getFinishedAt(), ChronoUnit.SECONDS);
        roomOrder.setFullTime(requestCreateRoomOrderDto.getExtraTime() + tariffTime);

        var extraTimeChildPrice = ((requestCreateRoomOrderDto.getChildCount() - roomTariff.getMaxChild()) * roomTariff.getChildPrice());

        if (extraTimeChildPrice < 0)
            extraTimeChildPrice = 0;

        var fullPriceByExtraTime = getFullPriceByExtraTime(
                requestCreateRoomOrderDto.getExtraTime(),
                roomTariff.getPenaltyPerHalfHour(),
                roomTariff.getPenaltyPerHour(),
                requestCreateRoomOrderDto.getChildCount()
        );

        roomOrder.setFullPrice(roomTariff.getFirstPrice() + extraTimeChildPrice + fullPriceByExtraTime);
        roomOrder.setClientPhoneNumber(requestCreateRoomOrderDto.getClientPhoneNumber());
        roomOrder.setFinishedAt(endedAt);

        roomOrderRepository.save(roomOrder);
    }


    private Double getFullPriceByExtraTime(Long extraTime, Double penaltyPerHalfHour, Double penaltyPerHour, Integer childCount) {
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

        return extraTimePrice * childCount;
    }

    private boolean isSameDate(LocalDate dateTime1, LocalDate dateTime2) {
        return dateTime1.getYear() == dateTime2.getYear() &&
                dateTime1.getMonth() == dateTime2.getMonth() &&
                dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth();
    }

}
