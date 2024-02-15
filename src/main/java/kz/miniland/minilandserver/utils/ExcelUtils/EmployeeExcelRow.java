package kz.miniland.minilandserver.utils.ExcelUtils;

import lombok.Data;

@Data
public class EmployeeExcelRow {
    private String name;
    private Long totalWorkTime;
    private Double avgWorkTime;
    private Long minWorkTime;
    private Long maxWorkTime;
    private Double totalProfit;
    private Double avgProfit;
    private Double minProfit;
    private Double maxProfit;
    private Integer ordersCount;
    private Integer bookedRoomsCount;
    private Double totalOrdersProfit;
    private Double totalRoomOrdersProfit;

    public EmployeeExcelRow(String name) {
        this.name = name;
        totalWorkTime = 0L;
        avgWorkTime = 0.0;
        minWorkTime = Long.MAX_VALUE;
        maxWorkTime = Long.MIN_VALUE;
        totalProfit = 0.0;
        avgProfit = 0.0;
        minProfit = Double.MAX_VALUE;
        maxProfit = Double.MIN_VALUE;
        ordersCount = 0;
        bookedRoomsCount = 0;
        totalOrdersProfit = 0.0;
        totalRoomOrdersProfit = 0.0;
    }

    public Object getByKeyIndexType(KeyIndexType keyIndexType) {
        return switch (keyIndexType) {
            case MAX_TIME -> maxWorkTime;
            case MIN_TIME -> minWorkTime;
            case FULL_TIME -> totalWorkTime;
            case MAX_PRICE -> maxProfit;
            case MIN_PRICE -> minProfit;
            case FULL_PRICE -> totalProfit;
            case ORDER_COUNT -> ordersCount;
            case FULL_PRICE_ORDER -> totalOrdersProfit;
            case FULL_PRICE_ROOM_ORDER -> totalRoomOrdersProfit;
            case AVG_TIME -> avgWorkTime;
            case ROOM_ORDER_COUNT -> bookedRoomsCount;
            case AVG_PRICE -> avgProfit;
            case FULL_NAME -> name;
        };
    }
}
