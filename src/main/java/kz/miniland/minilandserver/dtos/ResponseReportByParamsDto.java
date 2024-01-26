package kz.miniland.minilandserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseReportByParamsDto {
    @JsonProperty("orders_count")
    private Integer ordersCount;
    @JsonProperty("total_time")
    private Long totalTime;
    @JsonProperty("profit")
    private Double profit;
}
