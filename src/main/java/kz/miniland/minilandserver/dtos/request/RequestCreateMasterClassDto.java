package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestCreateMasterClassDto {
    @NotNull(message = "Title must not be null")
    @JsonProperty("title")
    private String title;

    @NotNull(message = "Description must not be null")
    @JsonProperty("description")
    private String description;

    @NotNull(message = "Price must not be null")
    @JsonProperty("price")
    @PositiveOrZero(message = "Price must be positive or zero")
    private Double price;

    private MultipartFile image;
}
