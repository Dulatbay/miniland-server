package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestCreateSaleWithPercentDto {

    @JsonProperty("title")
    @Size(min = 3, max = 150, message = "Title must be between 10 and 150 characters")
    @NotNull(message = "Title must not be null")
    private String title;

    @JsonProperty("percent")
    @NotNull(message = "Full time must not be null")
    @Min(value = 1, message = "The minimum value of percent should be 1")
    @Max(value = 100, message = "the maximum value of percent should be 100")
    private Integer percent;

}
