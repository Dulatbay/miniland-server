package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCardRoomOrderDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("started_time")
    private String startedTime;

    @JsonProperty("ended_time")
    private String endedTime;

    @JsonProperty("booked_day")
    private LocalDate bookedDay;

    @JsonProperty("day_of_booking")
    private String dayOfBooking;

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

    @JsonProperty("client_phone_number")
    private String clientPhoneNumber;

    @JsonProperty("extra_time")
    private Long extra_time;

    @JsonProperty("room_tariff")
    private ResponseDetailRoomTariffDto roomTariff;

    @JsonProperty("finished")
    private boolean finished;

    @JsonProperty("started")
    private boolean started;

    @JsonProperty("paid")
    private boolean paid;
}
