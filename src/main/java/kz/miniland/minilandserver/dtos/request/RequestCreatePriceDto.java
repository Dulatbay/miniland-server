package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RequestCreatePriceDto {
    @JsonProperty("full_time")
    @NotNull
    private Long fullTime;

    @JsonProperty("full_price")
    @NotNull
    private Double fullPrice;

    @JsonProperty("days")
    @NotNull
    private List<Integer> days; // 1 - sun, 2 - mon, 3 - tue ...
}
