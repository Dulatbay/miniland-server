package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseOrderCountDto {

    @JsonProperty("order_count")
    Integer orderCount;

}
