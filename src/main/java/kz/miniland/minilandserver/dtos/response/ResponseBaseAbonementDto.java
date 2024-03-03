package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseBaseAbonementDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("full_time")
    private Long fullTime;

    @JsonProperty("full_price")
    private Long fullPrice;

    @JsonProperty("created_at")
    private LocalDateTime createAt;

    @JsonProperty("enabled")
    private Boolean enabled;

}
