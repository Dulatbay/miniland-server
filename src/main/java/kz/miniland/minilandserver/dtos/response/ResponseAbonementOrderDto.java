package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kz.miniland.minilandserver.entities.BaseAbonement;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseAbonementOrderDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("child_name")
    private String childName;

    @JsonProperty("child_age")
    private Integer childAge;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("base_abonement_id")
    private Long baseAbonementId;

    @JsonProperty("enabled")
    private boolean enabled;


}
