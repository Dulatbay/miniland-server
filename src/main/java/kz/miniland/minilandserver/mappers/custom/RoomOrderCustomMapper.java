package kz.miniland.minilandserver.mappers.custom;

import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomOrderDto;
import kz.miniland.minilandserver.entities.RoomOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class RoomOrderCustomMapper {
    private final RoomTariffCustomMapper roomTariffCustomMapper;
    public ResponseCardRoomOrderDto toCardDto(RoomOrder roomOrder) {
        var durationFullTime = Duration.between(roomOrder.getRoomTariff().getStartedAt(), roomOrder.getFinishedAt()).toSeconds();
        var durationTariff = Duration.between(roomOrder.getRoomTariff().getStartedAt(), roomOrder.getRoomTariff().getFinishedAt()).toSeconds();

        var format = DateTimeFormatter.ofPattern("HH:mm");
        var formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        return ResponseCardRoomOrderDto.builder()
                .clientName(roomOrder.getClientName())
                .startedTime(roomOrder.getRoomTariff().getStartedAt().format(format))
                .endedTime(roomOrder.getRoomTariff().getFinishedAt().format(format))
                .roomTariff(roomTariffCustomMapper.toDto(roomOrder.getRoomTariff()))
                .fullPrice(roomOrder.getFullPrice())
                .fullTime(roomOrder.getFullTime())
                .childQuentity(roomOrder.getChildQuentity())
                .bookedDay(roomOrder.getBookedDay())
                .authorName(roomOrder.getAuthorName())
                .dayOfBooking(roomOrder.getDayOfBooking().format(formatDate))
                .clientPhoneNumber(roomOrder.getClientPhoneNumber())
                .extra_time(durationFullTime - durationTariff)
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
