package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RequestCreateAbonementOrderDto {

    @JsonProperty("client_name")
    @NotNull(message = "Client name should not be null")
    private String clientName;

    @JsonProperty("phone_number")
    @NotNull(message = "Phone number should not be null")
    private String phoneNumber;

    @JsonProperty("child_name")
    @NotNull(message = "Child name should not be null")
    private String childName;

    @JsonProperty("child_age")
    @NotNull(message = "Child age should not be null")
    //todo: set min and max age
    private Integer childAge;



    @JsonProperty("base_abonement_id")
    @NotNull(message = "Base Abonement Id should not be null")
    private Long baseAbonementId;

}
