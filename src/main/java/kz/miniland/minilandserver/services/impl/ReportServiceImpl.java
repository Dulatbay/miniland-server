package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseTableReportDto;
import kz.miniland.minilandserver.entities.Order;
import kz.miniland.minilandserver.entities.Profit;
import kz.miniland.minilandserver.entities.ProfitTypes;
import kz.miniland.minilandserver.repositories.OrderRepository;
import kz.miniland.minilandserver.repositories.ProfitRepository;
import kz.miniland.minilandserver.repositories.RoomOrderRepository;
import kz.miniland.minilandserver.services.ReportService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;
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
        @Cleanup
        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet(String.format("Отчет о сотрудниках, %s - %s", startDate, endDate));
        sheet.setDefaultColumnWidth(15000);

        var header = sheet.createRow(0);

        var headerStyle = workbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        final XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);


        excelKeys.forEach((key) -> {
            Cell headerCell = header.createCell(key.getKey().ordinal());
            headerCell.setCellValue(key.value);
            headerCell.setCellStyle(headerStyle);
        });

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        final Map<String, EmployeeExcelRow> excelRowMap = new HashMap<>();

        final var orders = orderRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay());
        final var roomOrders = roomOrderRepository.getAllByBookedDayBetween(startDate, endDate);

        orders.forEach(order -> {
            excelRowMap.computeIfAbsent(order.getAuthorName(), (i) -> new EmployeeExcelRow(order.getAuthorName()));
            var em = excelRowMap.get(order.getAuthorName());
            excelKeys.forEach(i -> {
                switch (i.key) {
                    case MAX_TIME:
                        em.setMaxWorkTime(Math.max(order.getFullTime(), em.getMaxWorkTime()));
                        break;
                    case MIN_TIME:
                        em.setMinWorkTime(Math.min(order.getFullTime(), em.getMinWorkTime()));
                        break;
                    case FULL_TIME:
                        em.setTotalWorkTime(order.getFullTime() + em.getMaxWorkTime());
                        break;
                    case MAX_PRICE:
                        em.setMaxProfit(Math.max(order.getFullPrice(), em.getMaxProfit()));
                        break;
                    case MIN_PRICE:
                        em.setMinProfit(Math.min(order.getFullPrice(), em.getMinProfit()));
                        break;
                    case FULL_PRICE:
                        em.setTotalProfit(order.getFullPrice() + em.getTotalProfit());
                        break;
                    case ORDER_COUNT:
                        em.setOrdersCount(1 + em.getOrdersCount());
                        break;
                    case FULL_PRICE_ORDER:
                        em.setTotalOrdersProfit(em.getTotalOrdersProfit() + order.getFullPrice());
                        break;
                }
            });
        });

        roomOrders.forEach(order -> {
            excelRowMap.computeIfAbsent(order.getAuthorName(), (i) -> new EmployeeExcelRow(order.getAuthorName()));
            var em = excelRowMap.get(order.getAuthorName());
            excelKeys.forEach(i -> {
                switch (i.key) {
                    case MAX_TIME:
                        em.setMaxWorkTime(Math.max(order.getFullTime(), em.getMaxWorkTime()));
                        break;
                    case MIN_TIME:
                        em.setMinWorkTime(Math.min(order.getFullTime(), em.getMinWorkTime()));
                        break;
                    case FULL_TIME:
                        em.setTotalWorkTime(order.getFullTime() + em.getMaxWorkTime());
                        break;
                    case MAX_PRICE:
                        em.setMaxProfit(Math.max(order.getFullPrice(), em.getMaxProfit()));
                        break;
                    case MIN_PRICE:
                        em.setMinProfit(Math.min(order.getFullPrice(), em.getMinProfit()));
                        break;
                    case FULL_PRICE:
                        em.setTotalProfit(order.getFullPrice() + em.getTotalProfit());
                        break;
                    case ORDER_COUNT:
                        em.setOrdersCount(1 + em.getOrdersCount());
                        break;
                    case FULL_PRICE_ORDER:
                        em.setTotalOrdersProfit(em.getTotalOrdersProfit() + order.getFullPrice());
                        break;
                }
            });
        });

        int rowCount = 2;

        for (var employee : excelRowMap.entrySet()) {
            final int finalRowCount = rowCount;

            excelKeys.forEach(i -> {
                Row row = sheet.createRow(finalRowCount);
                Cell cell = row.createCell(i.key.ordinal());
                cell.setCellValue(employee.getValue().getByKeyIndexType(i.key).toString());
                cell.setCellStyle(style);
            });

            rowCount++;
        }

        StreamingResponseBody responseBody = workbook::write;


    }

}


