package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class ResponsePriceDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("full_time")
    private Long fullTime;

    @JsonProperty("full_price")
    private Double fullPrice;

    @JsonProperty("days")
    private Set<Integer> days;
}
