package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import kz.miniland.minilandserver.validators.ValidFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestCreateMasterClassDto {
    @NotNull(message = "Title must not be null")
    @JsonProperty("title")
    private String title;

    @NotNull(message = "Description must not be null")
    @JsonProperty("title")
    private String description;

    @NotNull(message = "Price must not be null")
    @JsonProperty("title")
    @PositiveOrZero(message = "Price must be positive or zero")
    private Double price;

    @ValidFile
    private MultipartFile image;
}
