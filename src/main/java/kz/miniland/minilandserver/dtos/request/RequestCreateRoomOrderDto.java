package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestCreateRoomOrderDto {
    @JsonProperty("tariff_id")
    @NotNull
    private Long tariffId;

    @JsonProperty("client_name")
    @NotNull
    @Size(min = 3, max = 150, message = "Client name must be between 10 and 150 characters")
    private String clientName;

    @JsonProperty("client_phone_number")
    @NotNull
    @Size(min = 3, max = 150, message = "Client phone number must be between 10 and 150 characters")
    private String clientPhoneNumber;

    @JsonProperty("selected_booked_day")
    @NotNull
    @FutureOrPresent(message = "The selected booked day must be in the future.")
    private LocalDateTime selectedBookedDay;

    @JsonProperty("extra_time")
    @Min(value = 0, message = "Extra time should not be less than 0")
    @Max(value = 36000, message = "Extra time should not be greater than 10 hours")
    private Long extraTime;

    @JsonProperty("child_count")
    @NotNull
    @Positive(message = "The number of children must be greater than 0")
    private Integer childCount;

    @JsonIgnore
    private String authorName;
}
