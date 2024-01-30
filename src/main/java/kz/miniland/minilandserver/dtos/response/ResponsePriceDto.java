package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePriceDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("full_time")
    private Long fullTime;

    @JsonProperty("full_price")
    private Double fullPrice;

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("days")
    private List<Integer> days;
}