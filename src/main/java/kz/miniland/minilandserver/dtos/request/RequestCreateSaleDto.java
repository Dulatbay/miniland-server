package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestCreateSaleDto {
    @JsonProperty("title")
    @NotNull
    private String title;

    @JsonProperty("full_time")
    @NotNull
    private Long fullTime;

    @JsonProperty("full_price")
    @NotNull
    private Double fullPrice;

}
