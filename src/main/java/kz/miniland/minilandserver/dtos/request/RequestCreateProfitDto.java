package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestCreateProfitDto {
    @NotNull
    @JsonProperty("reason")
    @Size(min = 3, max = 150, message = "Profit must be between 10 and 150 characters")
    private String reason;

    @NotNull
    @Min(value = 0, message = "Profit should not be less than 0")
    @JsonProperty("profit")
    private Double profit;

    @NotNull
    @JsonProperty("is_expense")
    private Boolean isExpense;
}
