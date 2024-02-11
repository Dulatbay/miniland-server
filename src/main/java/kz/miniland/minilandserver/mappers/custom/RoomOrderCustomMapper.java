package kz.miniland.minilandserver.mappers.custom;

import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomOrderDto;
import kz.miniland.minilandserver.entities.RoomOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class RoomOrderCustomMapper {
    private final RoomTariffCustomMapper roomTariffCustomMapper;
    public ResponseCardRoomOrderDto toCardDto(RoomOrder roomOrder) {
        return ResponseCardRoomOrderDto.builder()
                .clientName(roomOrder.getClientName())
                .startedTime(roomOrder.getRoomTariff().getStartedAt())
                .endedTime(roomOrder.getRoomTariff().getFinishedAt())
                .roomTariff(roomTariffCustomMapper.toCardDto(roomOrder.getRoomTariff()))
                .fullPrice(roomOrder.getFullPrice())
                .fullTime(roomOrder.getFullTime())
                .childQuentity(roomOrder.getChildQuentity())
                .bookedDay(roomOrder.getBookedDay())
                .authorName(roomOrder.getAuthorName())
                .dayOfBooking(roomOrder.getDayOfBooking())
                .build();
    }

    public ResponseBookedDayDto toBookedDayDto(RoomOrder roomOrder) {
        return ResponseBookedDayDto.builder()
                .date(roomOrder.getBookedDay())
                .startedTime(roomOrder.getRoomTariff().getStartedAt())
                .endedTime(roomOrder.getRoomTariff().getStartedAt().plusSeconds(roomOrder.getFullTime()))
                .build();
    }

    public ResponseDetailRoomOrderDto toDetailDto(RoomOrder roomOrder) {
        var tariffTime = roomOrder.getRoomTariff().getStartedAt().until(roomOrder.getBookedDay(), ChronoUnit.SECONDS);
        return ResponseDetailRoomOrderDto
                .builder()
                .authorName(roomOrder.getAuthorName())
                .clientName(roomOrder.getClientName())
                .clientPhoneNumber(roomOrder.getClientName())
                .dayOfBooking(roomOrder.getDayOfBooking())
                .bookedDay(roomOrder.getBookedDay())
                .childCount(roomOrder.getChildQuentity())
                .fullTime(roomOrder.getFullTime())
                .extraTime(roomOrder.getFullTime() - tariffTime)
                .build();
    }

}
