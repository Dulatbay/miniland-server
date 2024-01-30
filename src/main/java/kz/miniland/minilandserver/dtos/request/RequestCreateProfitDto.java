package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestCreateProfitDto {
    @NotNull
    @JsonProperty("reason")
    private String reason;

    @NotNull
    @JsonProperty("profit")
    private Double profit;

    @NotNull
    @JsonProperty("is_expense")
    private Boolean isExpence;
}
