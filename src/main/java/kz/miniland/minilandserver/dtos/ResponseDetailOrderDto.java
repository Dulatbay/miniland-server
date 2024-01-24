package kz.miniland.minilandserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDetailOrderDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("parent_name")
    private String parentName;
    @JsonProperty("parent_phone_number")
    private String parentPhoneNumber;
    @JsonProperty("child_name")
    private String childName;
    @JsonProperty("child_age")
    private Integer childAge;
    @JsonProperty("sale")
    private ResponseSaleDto sale;
    @JsonProperty("extra_time")
    private Long extraTime;
    @JsonProperty("entered_time")
    private String enteredTime;
    @JsonProperty("remain_time")
    private Long remainTime;
    @JsonProperty("full_price")
    private Double fullPrice;
    @JsonProperty("full_time")
    private Long fullTime;
    @JsonProperty("is_paid")
    private Boolean isPaid;
    @JsonProperty("is_finished")
    private Boolean isFinished;
    @JsonProperty("author_name")
    private String authorName;
}
