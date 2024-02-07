package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class RequestCreateTariffDto {
    @JsonProperty("started_at")
    @NotNull
    private LocalTime startedAt;

    @JsonProperty("finished_at")
    @NotNull
    private LocalTime finishedAt;

    @JsonProperty("days")
    @NotNull
    @Size(min = 1, max = 7, message = "Number of days should be between 1 and 7")
    private Set<@Min(value = 1, message = "Week day must be in range 1-7") @Max(value = 7, message = "Week day must be in range 1-7") Integer> days;

    @JsonProperty("first_price")
    @Min(value = 0, message = "Price must be more than 0")
    @NotNull
    private Double firstPrice;

    @JsonProperty("penalty_per_hour")
    @Min(value = 0, message = "Penalty must be more than 0")
    @NotNull
    private Double penaltyPerHour;

    @JsonProperty("penalty_per_half_hour")
    @Min(value = 0, message = "Penalty must be more than 0")
    @NotNull
    private Double penaltyPerHalfHour;

    @JsonProperty("max_child")
    @Min(value = 0, message = "The number of children must be more than 3")
    @NotNull
    private Integer maxChild;

    @JsonProperty("child_price")
    @Positive(message = "Child price must be greater than 0")
    @NotNull
    private Double childPrice;
}
