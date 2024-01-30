package kz.miniland.minilandserver.dtos;

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
    private List<Integer> days; // 0 - sun, 1 - mon, 2 - tue ...
}
