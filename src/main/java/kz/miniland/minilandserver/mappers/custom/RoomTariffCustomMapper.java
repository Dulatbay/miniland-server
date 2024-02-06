package kz.miniland.minilandserver.mappers.custom;

import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.entities.RoomTariff;
import kz.miniland.minilandserver.entities.WeekDays;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoomTariffCustomMapper {
    public ResponseCardRoomTariffDto toDto(RoomTariff roomTariff){
        return ResponseCardRoomTariffDto
                .builder()
                .firstPrice(roomTariff.getFirstPrice())
                .startedTime(roomTariff.getStartedAt())
                .endedTime(roomTariff.getFinishedAt())
                .maxChild(roomTariff.getMaxChild())
                .weekDays(roomTariff.getDays()
                        .stream()
                        .map(WeekDays::getInteger)
                        .collect(Collectors.toList()))

                .build();
    }
}
