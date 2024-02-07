package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class RequestCreateTariffDto {
    @JsonProperty("started_at")
    private LocalTime startedAt;
    @JsonProperty("finished_at")
    private LocalTime finishedAt;
    @JsonProperty("days")
    private Set<Integer> days;
    @JsonProperty("first_price")
    @Min(value = 0, message = "Price must be more than 0")
    private Double firstPrice;
    @JsonProperty("penalty_per_hour")
    @Min(value = 0, message = "Penalty must be more than 0")
    private Double penaltyPerHour;
    @JsonProperty("penalty_per_half_hour")
    @Min(value = 0, message = "Penalty must be more than 0")
    private Double penaltyPerHalfHour;
    @JsonProperty("max_child")
    @Min(value = 0, message = "The number of children must be more than 3")
    private Integer maxChild;
    @JsonProperty("child_price")
    @Positive(message = "Child price must be greater than 0")
    private Double childPrice;
}
