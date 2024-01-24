package kz.miniland.minilandserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
}
