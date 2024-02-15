package kz.miniland.minilandserver.mappers.custom;

import kz.miniland.minilandserver.dtos.request.RequestCreateTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomTariffDto;
import kz.miniland.minilandserver.entities.RoomTariff;
import kz.miniland.minilandserver.entities.WeekDays;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@Component
public class RoomTariffCustomMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZONE_ID);

    public ResponseCardRoomTariffDto toCardDto(RoomTariff roomTariff) {
        return ResponseCardRoomTariffDto
                .builder()
                .id(roomTariff.getId())
                .firstPrice(roomTariff.getFirstPrice())
                .startedTime(roomTariff.getStartedAt().format(dateTimeFormatter))
                .endedTime(roomTariff.getFinishedAt().format(dateTimeFormatter))
                .maxChild(roomTariff.getMaxChild())
                .weekDays(roomTariff.getDays()
                        .stream()
                        .map(WeekDays::getInteger)
                        .collect(Collectors.toList()))

                .build();
    }

    public ResponseDetailRoomTariffDto toDto(RoomTariff roomTariff) {
        return ResponseDetailRoomTariffDto
                .builder()
                .id(roomTariff.getId())
                .firstPrice(roomTariff.getFirstPrice())
                .startedTime(roomTariff.getStartedAt().format(dateTimeFormatter))
                .endedTime(roomTariff.getFinishedAt().format(dateTimeFormatter))
                .maxChild(roomTariff.getMaxChild())
                .weekDays(roomTariff.getDays()
                        .stream()
                        .map(WeekDays::getInteger)
                        .collect(Collectors.toList()))
                .childPrice(roomTariff.getChildPrice())
                .penaltyPerHalfHour(roomTariff.getPenaltyPerHalfHour())
                .penaltyPerHour(roomTariff.getPenaltyPerHour())
                .build();
    }

    public RoomTariff toEntity(RequestCreateTariffDto requestCreateTariffDto) {
        final RoomTariff roomTariff = new RoomTariff();

        roomTariff.setStartedAt(requestCreateTariffDto.getStartedAt());
        roomTariff.setFinishedAt(requestCreateTariffDto.getFinishedAt());
        roomTariff.setFirstPrice(requestCreateTariffDto.getFirstPrice());
        roomTariff.setMaxChild(requestCreateTariffDto.getMaxChild());
        roomTariff.setPenaltyPerHalfHour(requestCreateTariffDto.getPenaltyPerHalfHour());
        roomTariff.setPenaltyPerHour(requestCreateTariffDto.getPenaltyPerHour());
        roomTariff.setDays(requestCreateTariffDto.getDays().stream().map(WeekDays::getByInteger).collect(Collectors.toSet()));
        roomTariff.setChildPrice(requestCreateTariffDto.getChildPrice());
        roomTariff.setEnabled(true);

        return roomTariff;
    }
}
