package kz.miniland.minilandserver.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseCardOrderDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("child_name")
    private String childName;
    @JsonProperty("parent_name")
    private String parentName;
    @JsonProperty("parent_phone_number")
    private String phoneNumber;
    @JsonProperty("entered_time")
    private String enteredTime;
    @JsonProperty("full_time")
    private Long fullTime;
    @JsonProperty("full_price")
    private Double fullPrice;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("is_finished")
    private Boolean isFinished;
    @JsonProperty("remain_time")
    private Long remainTime;
    @JsonProperty("is_paid")
    private Boolean isPaid;
    @JsonProperty("author_name")
    private String authorName;

    // JsonProperty
    // left...

}
