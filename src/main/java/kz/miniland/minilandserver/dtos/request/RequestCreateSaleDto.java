package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestCreateSaleDto {
    @JsonProperty("title")
    @Size(min = 3, max = 150, message = "Title must be between 10 and 150 characters")
    @NotNull
    private String title;

    @JsonProperty("full_time")
    @NotNull
    @Min(value = 0, message = "Full time should not be less than 0")
    @Max(value = 36000, message = "Full time should not be greater than 10 hours")
    private Long fullTime;

    @JsonProperty("full_price")
    @NotNull
    @Positive(message = "Full price must be greater than 0")
    private Double fullPrice;

}
