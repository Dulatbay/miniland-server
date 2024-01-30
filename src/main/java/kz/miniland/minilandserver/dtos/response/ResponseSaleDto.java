package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseSaleDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("full_time")
    private Long fullTime;
    @JsonProperty("full_price")
    private Double fullPrice;
    @JsonProperty("enabled")
    private Boolean enabled;
}
