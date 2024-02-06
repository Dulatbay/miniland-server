package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class ResponseCardRoomTariffDto {
    @JsonProperty("started_time")
    private LocalTime startedTime;

    @JsonProperty("ended_time")
    private LocalTime endedTime;

    @JsonProperty("first_price")
    private Double firstPrice;

    @JsonProperty("max_child")
    private Integer maxChild;

    @JsonProperty("week_days")
    private List<Integer> weekDays;
}
