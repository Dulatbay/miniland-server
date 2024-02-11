package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RequestCreateRoomOrderDto {
    @JsonProperty("tariff_id")
    @NotNull(message = "You need to choose one of the tariffs")
    private Long tariffId;

    @JsonProperty("client_name")
    @NotNull(message = "Client's name must not be null")
    @Size(min = 3, max = 150, message = "Client name must be between 10 and 150 characters")
    private String clientName;

    @JsonProperty("client_phone_number")
    @NotNull(message = "The client phone number must not be null")
    @Size(min = 3, max = 150, message = "Client phone number must be between 10 and 150 characters")
    private String clientPhoneNumber;

    @JsonProperty("selected_booked_day")
    @NotNull(message = "The selected booked day must not be null")
    @FutureOrPresent(message = "The selected booked day must be in the future.")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate selectedBookedDay;

    @JsonProperty("extra_time")
    @Min(value = 0, message = "Extra time should not be less than 0")
    @Max(value = 36000, message = "Extra time should not be greater than 10 hours")
    private Long extraTime;

    @JsonProperty("child_count")
    @NotNull(message = "The number of child must not be null")
    @Positive(message = "The number of children must be greater than 0")
    private Integer childCount;

    @JsonIgnore
    private String authorName;
}
