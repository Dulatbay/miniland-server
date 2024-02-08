package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.miniland.minilandserver.validators.ValidFile;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestCreateMasterClassDto {
    @NonNull
    @JsonProperty("title")
    private String title;

    @NonNull
    @JsonProperty("title")
    private String description;

    @NonNull
    @JsonProperty("title")
    private Double price;

    @ValidFile
    private MultipartFile image;
}
