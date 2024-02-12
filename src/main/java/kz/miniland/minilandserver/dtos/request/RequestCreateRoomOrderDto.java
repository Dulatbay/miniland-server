package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;

import java.time.LocalDate;

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

    @NotNull(message = "The number of child must not be null")
    @Min(value = 0, message = "Child should not be less than 0")
    @JsonProperty("child_count")
    private Integer childCount;

    @JsonProperty("paid")
    @NotNull(message = "Property paid must be not null")
    private boolean paid;

    @JsonIgnore
    private String authorName;
}
