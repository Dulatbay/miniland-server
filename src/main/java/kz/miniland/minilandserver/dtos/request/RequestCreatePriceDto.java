package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RequestCreatePriceDto {
    @JsonProperty("full_time")
    @NotNull(message = "Full time field must not be null")
    @Min(value = 600, message = "Full time should not be less than 10 minute")
    @Max(value = 36000, message = "Full time should not be greater than 10 hours")
    private Long fullTime;

    @JsonProperty("full_price")
    @NotNull(message = "Full price field must not be null")
    @Min(value = 0, message = "Full price should not be less than 0tg")
    private Double fullPrice;

    @JsonProperty("days")
    @NotNull(message = "Week day list must not be null")
    @Size(min = 1, max = 7, message = "Number of days should be between 1 and 7")
    private Set<@Min(value = 1, message = "Week day must be in range 1-7") @Max(value = 7, message = "Week day must be in range 1-7") Integer> days;
    // 1 - sun, 2 - mon, 3 - tue ...
}
