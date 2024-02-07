package kz.miniland.minilandserver.mappers.custom;

import kz.miniland.minilandserver.dtos.request.RequestCreateTariffDto;
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

    public RoomTariff toEntity(RequestCreateTariffDto requestCreateTariffDto){
        RoomTariff roomTariff =  new RoomTariff();

        roomTariff.setStartedAt(requestCreateTariffDto.getStartedAt());
        roomTariff.setFinishedAt(requestCreateTariffDto.getFinishedAt());
        roomTariff.setFirstPrice(requestCreateTariffDto.getFirstPrice());
        roomTariff.setMaxChild(requestCreateTariffDto.getMaxChild());
        roomTariff.setPenaltyPerHalfHour(requestCreateTariffDto.getPenaltyPerHalfHour());
        roomTariff.setPenaltyPerHour(requestCreateTariffDto.getPenaltyPerHour());
        roomTariff.setDays(requestCreateTariffDto.getDays().stream().map(WeekDays::getByInteger).collect(Collectors.toSet()));
        roomTariff.setChildPrice(requestCreateTariffDto.getChildPrice());    ;
        roomTariff.setEnabled(true);

        return roomTariff;
    }
}
