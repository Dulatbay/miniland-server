package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseReportDetailProfitDto {
    @JsonProperty("is_income")
    private boolean isIncome;
    @JsonProperty("profit")
    private Double profit;
    @JsonProperty("title")
    private String title;

}
