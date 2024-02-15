package kz.miniland.minilandserver.utils.ExcelUtils;

import kz.miniland.minilandserver.entities.OrderWithPriceAndTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelUtil {
    public final static List<Tuple<KeyIndexType, String>> excelKeys = List.of(
            new Tuple<>(KeyIndexType.FULL_NAME, "ФИО"),
            new Tuple<>(KeyIndexType.FULL_TIME, "ОБЩЕЕ ВРЕМЯ РАБОТЫ"),
            new Tuple<>(KeyIndexType.AVG_TIME, "СРЕДНЕЕ ВРЕМЯ РАБОТЫ В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MIN_TIME, "МИНИМАЛЬНОЕ ВРЕМЯ РАБОТЫ В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MAX_TIME, "МАКСИМАЛЬНОЕ ВРЕМЯ РАБОТЫ В ДЕНЬ"),
            new Tuple<>(KeyIndexType.FULL_PRICE, "ОБЩИЙ ЗАРАБОТОК"),
            new Tuple<>(KeyIndexType.AVG_PRICE, "СРЕДНИЙ ЗАРАБОТОК В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MIN_PRICE, "МИНИМАЛЬНЫЙ ЗАРАБОТОК В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MAX_PRICE, "МАКСИМАЛЬНЫЙ ЗАРАБОТОК В ДЕНЬ"),
            new Tuple<>(KeyIndexType.ORDER_COUNT, "ЗАКАЗОВ БЫЛО СДЕЛАНО"),
            new Tuple<>(KeyIndexType.ROOM_ORDER_COUNT, "БРОНЬ БЫЛО СДЕЛАНО"),
            new Tuple<>(KeyIndexType.FULL_PRICE_ORDER, "ОБЩИЙ ЗАРАБАТОК С ЗАКАЗОВ"),
            new Tuple<>(KeyIndexType.FULL_PRICE_ROOM_ORDER, "ОБЩИЙ ЗАРАБОТОК С БРОНИ")
    );

    public static void createHeader(Sheet sheet, Workbook workbook) {
        var header = sheet.createRow(0);
        var headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);
        var font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        excelKeys.forEach(key -> {
            Cell headerCell = header.createCell(key.getKey().ordinal());
            headerCell.setCellValue(key.getValue());
            headerCell.setCellStyle(headerStyle);
        });
    }

    public static CellStyle createCellStyle(Workbook workbook) {
        var style = workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    public static Map<String, EmployeeExcelRow> processOrders(List<OrderWithPriceAndTime> orders) {
        Map<String, EmployeeExcelRow> excelRowMap = new HashMap<>();
        orders.forEach(order -> {
            var row = excelRowMap.computeIfAbsent(order.getAuthorName(), (i) -> new EmployeeExcelRow(order.getAuthorName()));
            row.setMaxWorkTime(Math.max(order.getTotalFullTime(), row.getMaxWorkTime()));
            row.setMinWorkTime(Math.min(order.getTotalFullTime(), row.getMinWorkTime()));
            row.setTotalWorkTime(order.getTotalFullTime() + row.getTotalWorkTime());
            row.setMaxProfit(Math.max(order.getTotalFullPrice(), row.getMaxProfit()));
            row.setMinProfit(Math.min(order.getTotalFullPrice(), row.getMinProfit()));
            row.setTotalProfit(order.getTotalFullPrice() + row.getTotalProfit());
            if (order.isRoomOrder()) {
                row.setTotalRoomOrdersProfit(order.getTotalFullPrice() + row.getTotalRoomOrdersProfit());
                row.setBookedRoomsCount(1 + row.getBookedRoomsCount());
            } else {
                row.setTotalOrdersProfit(order.getTotalFullPrice() + row.getTotalOrdersProfit());
                row.setOrdersCount(1 + row.getOrdersCount());
            }
        });

        excelRowMap.forEach((key, value) -> {
            if (value.getMinProfit() == Double.MIN_VALUE)
                value.setMinProfit(null);
            if (value.getMaxProfit() == Double.MAX_VALUE)
                value.setMaxProfit(null);
            if (value.getMaxWorkTime() == Long.MAX_VALUE)
                value.setMaxWorkTime(null);
            if (value.getMinWorkTime() == Long.MIN_VALUE)
                value.setMinWorkTime(null);

            if (value.getTotalProfit() != 0)
                value.setAvgProfit(value.getTotalProfit() / (value.getOrdersCount() + value.getBookedRoomsCount()));
            else value.setAvgProfit(0.0);
            if (value.getTotalWorkTime() != 0)
                value.setAvgWorkTime((double) (value.getTotalWorkTime() / (value.getOrdersCount() + value.getBookedRoomsCount())));
            else value.setAvgWorkTime(0.0);

        });

        return excelRowMap;
    }
}
