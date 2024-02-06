package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCardRoomOrderDto {
    @JsonProperty("started_time")
    private LocalTime startedTime;

    @JsonProperty("ended_time")
    private LocalTime endedTime;

    @JsonProperty("client_name")
    private String clientName;
}
