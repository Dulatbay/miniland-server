package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDetailRoomTariffDto  {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("started_time")
    private String startedTime;

    @JsonProperty("ended_time")
    private String endedTime;

    @JsonProperty("first_price")
    private Double firstPrice;

    @JsonProperty("max_child")
    private Integer maxChild;

    @JsonProperty("week_days")
    private List<Integer> weekDays;

    @JsonProperty("child_price")
    private Double childPrice;

    @JsonProperty("penalty_per_hour")
    private Double penaltyPerHour;

    @JsonProperty("penalty_per_half_hour")
    private Double penaltyPerHalfHour;
}
