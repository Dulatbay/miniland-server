package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestCreateBaseAbonementDto {

    @JsonProperty("title")
    @Size(min = 3, max = 300, message = "Title must be between 10 and 300 characters")
    @NotNull(message = "Title must not be null")
    private String title;

    @JsonProperty("description")
    @Size(min = 3, max = 500, message = "Description must be between 10 and 500 characters")
    @NotNull(message = "Description must not be null")
    private String description;

    @JsonProperty("full_time")
    @NotNull(message = "Full time must not be null")
    @Min(value = 0, message = "Full time should not be less than 0")
    @Max(value = 36000, message = "Full time should not be greater than 10 hours")
    private Long fullTime;

    @JsonProperty("full_price")
    @NotNull(message = "Full price must not be null")
    @Positive(message = "Full price must be greater than 0")
    private Double fullPrice;

}
