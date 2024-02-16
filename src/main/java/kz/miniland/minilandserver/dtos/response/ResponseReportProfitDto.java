package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseReportProfitDto {
    @JsonProperty("expense")
    private Double expense;
    @JsonProperty("income")
    private Double income;
    @JsonProperty("title")
    private String title;
}
