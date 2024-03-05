package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
public class RequestCreateOrderByAbonementDto {

    @JsonProperty("abonement_id")
    @Positive
    private Long abonementOrderId;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonIgnore
    private String authorId;

}
