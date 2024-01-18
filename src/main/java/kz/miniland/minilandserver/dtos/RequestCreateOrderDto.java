package kz.miniland.minilandserver.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RequestCreateOrderDto {
    private String parentName;
    private String parentPhoneNumber;
    private String childName;
    private String childAge;
    private List<Integer> salesIds;
    private Long extraTime;
}
