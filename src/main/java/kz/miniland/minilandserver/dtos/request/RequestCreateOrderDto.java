package kz.miniland.minilandserver.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
public class RequestCreateOrderDto {
    @JsonProperty("parent_name")
    @NotNull(message = "The parent's name must not be null")
    @Size(min = 3, max = 150, message = "Parent name must be between 3 and 150 characters")
    private String parentName;


    @JsonProperty("parent_phone_number")
    private String parentPhoneNumber;

    @JsonProperty("child_name")
    @Size(min = 3, max = 150, message = "Child Name must be between 3 and 150 characters")
    @NotNull(message = "The child's name must not be null")
    private String childName;

    @JsonProperty("child_age")
    @NotNull(message = "The child's age must not be null")
    @Min(value = 0, message = "Age should not be less than 0")
    @Max(value = 15, message = "Age should not be greater than 15")
    private Integer childAge;

    @JsonProperty("sale_id")
    private Long saleId;

    @JsonProperty("sale_with_percent_id")
    private Long saleWithPercentId;

    @JsonProperty("extra_time")
    @Min(value = 0, message = "Extra time should not be less than 0")
    @Max(value = 36000, message = "Extra time should not be greater than 10 hours")
    private Long extraTime;

    @JsonProperty("is_paid")
    private Boolean isPaid;

    @JsonIgnore
    private String authorId;
}
