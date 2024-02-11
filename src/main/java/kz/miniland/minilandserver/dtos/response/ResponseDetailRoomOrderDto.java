package kz.miniland.minilandserver.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDetailRoomOrderDto {
    private ResponseDetailRoomTariffDto tariffDto;
    private String clientName;
    private String clientPhoneNumber;
    private LocalDateTime dayOfBooking;
    private Long extraTime;
    private Integer childCount;
    private Long fullTime;
    private Double fullPrice;
    private String authorName;
    private LocalDate bookedDay;
}
