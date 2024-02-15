package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCardRoomTariffDto {
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
}
