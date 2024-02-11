package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCardRoomOrderDto {
    @JsonProperty("started_time")
    private LocalTime startedTime;

    @JsonProperty("ended_time")
    private LocalTime endedTime;

    @JsonProperty("booked_day")
    private LocalDate bookedDay;

    @JsonProperty("day_of_booking")
    private LocalDateTime dayOfBooking;

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("child_quantity")
    private Integer childQuentity;

    @JsonProperty("full_price")
    private Double fullPrice;

    @JsonProperty("full_time")
    private Long fullTime;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("room_tariff")
    private ResponseCardRoomTariffDto roomTariff;
}
