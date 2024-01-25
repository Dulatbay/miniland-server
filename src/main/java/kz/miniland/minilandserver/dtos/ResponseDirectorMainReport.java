package kz.miniland.minilandserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDirectorMainReport {
    @JsonProperty("orders_count")
    private Integer ordersCount;

    @JsonProperty("employee")
    private List<Employee> employees;
    @Data
    public static class Employee {
        @JsonProperty("username")
        private String username;
        @JsonProperty("orders_count")
        private Integer ordersCount;
        @JsonProperty("profit")
        private Double profit;
        @JsonProperty("serve_time")
        private Long serveTime;
    }

}
