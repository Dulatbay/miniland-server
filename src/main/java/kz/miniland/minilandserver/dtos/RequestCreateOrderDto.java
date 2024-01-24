package kz.miniland.minilandserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
public class RequestCreateOrderDto {
    @JsonProperty("parent_name")
    @NotNull
    private String parentName;

    @JsonProperty("parent_phone_number")
    @NotNull
    private String parentPhoneNumber;

    @JsonProperty("child_name")
    @NotNull
    private String childName;

    @JsonProperty("child_age")
    @NotNull
    private Integer childAge;

    @JsonProperty("sale_id")
    private Long saleId;

    @JsonProperty("extra_time")
    private Long extraTime;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonIgnore
    private String authorId;
}
