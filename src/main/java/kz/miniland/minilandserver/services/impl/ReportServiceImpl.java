package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseTableReportDto;
import kz.miniland.minilandserver.entities.Order;
import kz.miniland.minilandserver.entities.OrderWithPriceAndTime;
import kz.miniland.minilandserver.entities.Profit;
import kz.miniland.minilandserver.entities.ProfitTypes;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.repositories.ProfitRepository;
import kz.miniland.minilandserver.repositories.RoomOrderRepository;
import kz.miniland.minilandserver.services.ReportService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final OrderRepository orderRepository;
    private final ProfitRepository profitRepository;
    private final RoomOrderRepository roomOrderRepository;

    @Override
    public ResponseTableReportDto getTableReport() {
        LocalDate today = LocalDate.now(ZONE_ID);

        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);

        var entities = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        ResponseTableReportDto directorMainReport = new ResponseTableReportDto();
        directorMainReport.setOrdersCount(entities.size());

        Map<String, ResponseTableReportDto.Employee> employeeMap = new HashMap<>();


        entities.forEach((entity) -> {
            var employee = employeeMap.get(entity.getAuthorName());
            if (employee == null) {
                employee = new ResponseTableReportDto.Employee();
                employee.setProfit(entity.getFullPrice());
                employee.setUsername(entity.getAuthorName());
                employee.setOrdersCount(1);
                employee.setServeTime(entity.getFullTime());
                employeeMap.put(employee.getUsername(), employee);
            } else {
                employee.setOrdersCount(employee.getOrdersCount() + 1);
                employee.setProfit(entity.getFullPrice() + entity.getFullPrice());
                employee.setServeTime(employee.getServeTime() + entity.getFullTime());
            }
        });

        directorMainReport.setEmployees(new ArrayList<>(employeeMap.values()));

        return directorMainReport;
    }

    @Override
    public ResponseReportByParamsDto getReportByParams(String username, LocalDate startDate, LocalDate endDate) {

        LocalDateTime startOfDay = LocalDateTime.of(startDate, LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.of(endDate, LocalTime.MAX);

        List<Order> orders;
        if (username == null) {
            orders = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        } else {
            orders = orderRepository.findByAuthorNameAndCreatedAtBetween(username, startOfDay, endOfDay);
        }

        log.info("orders: {}", orders);
        ResponseReportByParamsDto responseReportByParamsDto = new ResponseReportByParamsDto(orders.size(), 0L, 0.0);
        orders.forEach(order -> {
            responseReportByParamsDto.setProfit(responseReportByParamsDto.getProfit() + order.getFullPrice());
            responseReportByParamsDto.setTotalTime(responseReportByParamsDto.getTotalTime() + order.getFullTime());
        });

        return responseReportByParamsDto;
    }

    @Override
    public ResponseReportProfitDto getReportProfitInRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = LocalDateTime.of(startDate, LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.of(endDate, LocalTime.MAX);

        var orders = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        ResponseReportProfitDto result = new ResponseReportProfitDto(0.0, 0.0);

        orders.forEach(order -> result.setIncome(result.getIncome() + order.getFullPrice()));

        var profits = profitRepository.findAllByCreateAtBetween(startOfDay, endOfDay);

        profits.forEach(profit -> {
            if (profit.getType() == ProfitTypes.EXPENSE) {
                result.setExpense(result.getExpense() + profit.getProfit());
            } else {
                result.setIncome(result.getIncome() + profit.getProfit());
            }
        });

        return result;
    }

    @Override
    public void createProfit(RequestCreateProfitDto requestCreateProfitDto) {
        var profit = new Profit();
        profit.setProfit(requestCreateProfitDto.getProfit());
        profit.setReason(requestCreateProfitDto.getReason());
        profit.setType(requestCreateProfitDto.getIsExpense() ? ProfitTypes.EXPENSE : ProfitTypes.INCOME);
        profitRepository.save(profit);
    }

    @Data
    private static class EmployeeExcelRow {
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
            minWorkTime = 0L;
            maxWorkTime = 0L;
            totalProfit = 0.0;
            avgProfit = 0.0;
            minProfit = 0.0;
            maxProfit = 0.0;
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Tuple<K, V> {
        private K key;
        private V value;
    }

    enum KeyIndexType {
        FULL_NAME,
        FULL_TIME,
        AVG_TIME,
        MIN_TIME,
        MAX_TIME,
        FULL_PRICE,
        AVG_PRICE,
        MIN_PRICE,
        MAX_PRICE,
        ORDER_COUNT,
        ROOM_ORDER_COUNT,
        FULL_PRICE_ORDER,
        FULL_PRICE_ROOM_ORDER,
    }

    final List<Tuple<KeyIndexType, String>> excelKeys = List.of(
            new Tuple<>(KeyIndexType.FULL_NAME, "ФИО"),
            new Tuple<>(KeyIndexType.FULL_TIME, "ОБЩЕЕ ВРЕМЯ РАБОТЫ"),
            new Tuple<>(KeyIndexType.AVG_TIME, "СРЕДНЕЕ ВРЕМЯ РАБОТЫ В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MIN_TIME, "МИНИМАЛЬНОЕ ВРЕМЯ РАБОТЫ В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MAX_TIME, "МАКСИМАЛЬНОЕ ВРЕМЯ РАБОТЫ В ДЕНЬ"),
            new Tuple<>(KeyIndexType.AVG_PRICE, "СРЕДНИЙ ЗАРАБОТОК В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MIN_PRICE, "МИНИМАЛЬНЫЙ ЗАРАБОТОК В ДЕНЬ"),
            new Tuple<>(KeyIndexType.MAX_PRICE, "МАКСИМАЛЬНЫЙ ЗАРАБОТОК В ДЕНЬ"),
            new Tuple<>(KeyIndexType.ORDER_COUNT, "ЗАКАЗОВ БЫЛО СДЕЛАНО"),
            new Tuple<>(KeyIndexType.ROOM_ORDER_COUNT, "БРОНЬ БЫЛО СДЕЛАНО"),
            new Tuple<>(KeyIndexType.FULL_PRICE_ORDER, "ОБЩИЙ ЗАРАБАТОК С ЗАКАЗОВ"),
            new Tuple<>(KeyIndexType.FULL_PRICE_ROOM_ORDER, "ОБЩИЙ ЗАРАБОТОК С БРОНИ")
    );


    @Override
    public Resource getReportExcel(LocalDate startDate, LocalDate endDate) {
        try (var workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet(String.format("Отчет о сотрудниках, %s - %s", startDate, endDate));
            sheet.setDefaultColumnWidth(15000);

            createHeader(sheet, workbook);

            var style = createCellStyle(workbook);

            final Map<String, EmployeeExcelRow> excelRowMap = new HashMap<>();

            processOrders(startDate, endDate, excelRowMap);

            int rowCount = 2;

            for (var employee : excelRowMap.entrySet()) {
                Row row = sheet.createRow(rowCount++);
                for (var key : excelKeys) {
                    Cell cell = row.createCell(key.getKey().ordinal());
                    cell.setCellValue(employee.getValue().getByKeyIndexType(key.getKey()).toString());
                    cell.setCellStyle(style);
                }
            }

            var file = File.createTempFile("report", ".xlsx");
            try (var fileOutputStream = new FileOutputStream(file)) {
                workbook.write(fileOutputStream);
            }
            return new ByteArrayResource(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            log.info("IOException: {}", e.toString());
            return null;
        }
    }

    private void createHeader(Sheet sheet, Workbook workbook) {
        var header = sheet.createRow(0);
        var headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        var font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        excelKeys.forEach(key -> {
            Cell headerCell = header.createCell(key.getKey().ordinal());
            headerCell.setCellValue(key.value);
            headerCell.setCellStyle(headerStyle);
        });
    }

    private CellStyle createCellStyle(Workbook workbook) {
        var style = workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    private void processOrders(LocalDate startDate, LocalDate endDate, Map<String, EmployeeExcelRow> excelRowMap) {
        List<OrderWithPriceAndTime> orders = new ArrayList<>();
        orders.addAll(orderRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay()));
        orders.addAll(roomOrderRepository.getAllByBookedDayBetween(startDate, endDate));


        orders.forEach(order -> {
            excelRowMap.merge(order.getAuthorName(), new EmployeeExcelRow(order.getAuthorName()),
                    (existingRow, newRow) -> {
                        existingRow.setMaxWorkTime(Math.max(order.getTotalFullTime(), existingRow.getMaxWorkTime()));
                        existingRow.setMinWorkTime(Math.min(order.getTotalFullTime(), existingRow.getMinWorkTime()));
                        existingRow.setTotalWorkTime(order.getTotalFullTime() + existingRow.getMaxWorkTime());
                        existingRow.setMaxProfit(Math.max(order.getTotalFullPrice(), existingRow.getMaxProfit()));
                        existingRow.setMinProfit(Math.min(order.getTotalFullPrice(), existingRow.getMinProfit()));
                        existingRow.setTotalProfit(order.getTotalFullPrice() + existingRow.getTotalProfit());
                        existingRow.setOrdersCount(1 + existingRow.getOrdersCount());
                        existingRow.setTotalOrdersProfit(order.getTotalFullPrice() + existingRow.getTotalOrdersProfit());
                        return existingRow;
                    });
        });
    }


}


