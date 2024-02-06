package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookedDayDto {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("started_time")
    private LocalTime startedTime;
    @JsonProperty("ended_time")
    private LocalTime endedTime;
}
