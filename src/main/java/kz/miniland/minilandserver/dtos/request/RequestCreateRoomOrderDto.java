package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestCreateRoomOrderDto {
    @JsonProperty("tariff_id")
    @NotNull
    private Long tariffId;

    @JsonProperty("client_name")
    @NotNull
    private String clientName;

    @JsonProperty("client_phone_number")
    @NotNull
    private String clientPhoneNumber;

    @JsonProperty("selected_booked_day")
    @NotNull
    private LocalDateTime selectedBookedDay;

    @JsonProperty("extra_time")
    @NotNull
    private Long extraTime;

    @JsonProperty("child_count")
    @NotNull
    private Integer childCount;

    @JsonIgnore
    private String authorName;
}