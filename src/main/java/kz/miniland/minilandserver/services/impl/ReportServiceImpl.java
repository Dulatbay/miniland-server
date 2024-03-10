package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.dtos.request.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportDetailProfitDto;
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
import kz.miniland.minilandserver.utils.ExcelUtils.ExcelUtil;
import kz.miniland.minilandserver.utils.ExcelUtils.KeyIndexType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
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

        var orders = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        var roomOrders = roomOrderRepository.getAllByBookedDayBetweenAndDeletedAndFinished(startOfDay.toLocalDate(), today.plusDays(1), false, true);

        ResponseTableReportDto directorMainReport = new ResponseTableReportDto();

        Map<String, ResponseTableReportDto.Employee> employeeMap = new HashMap<>();

        calculateOrders(orders.stream().map(i -> (OrderWithPriceAndTime) i).toList(), employeeMap);
        calculateOrders(roomOrders.stream().map(i -> (OrderWithPriceAndTime) i).toList(), employeeMap);
        directorMainReport.setOrdersCount(orders.size() + roomOrders.size());

        directorMainReport.setEmployees(new ArrayList<>(employeeMap.values()));

        return directorMainReport;
    }

    private static void calculateOrders(List<OrderWithPriceAndTime> orders, Map<String, ResponseTableReportDto.Employee> employeeMap) {
        orders.forEach((entity) -> {
            var employee = employeeMap.get(entity.getAuthorName());
            if (employee == null) {
                employee = new ResponseTableReportDto.Employee();
                employee.setProfit(entity.getTotalFullPrice());
                employee.setUsername(entity.getAuthorName());
                employee.setOrdersCount(1);
                employee.setServeTime(entity.getTotalFullTime());
                employeeMap.put(employee.getUsername(), employee);
            } else {
                employee.setOrdersCount(employee.getOrdersCount() + 1);
                employee.setProfit(Double.sum(entity.getTotalFullPrice(), employee.getProfit()));
                employee.setServeTime(employee.getServeTime() + entity.getTotalFullTime());
            }
            log.info("price: {}", entity.getTotalFullPrice());
        });
    }

    @Override
    public ResponseReportByParamsDto getReportByParams(String username, LocalDate startDate, LocalDate endDate) {

        LocalDateTime startOfDay = LocalDateTime.of(startDate, LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.of(endDate, LocalTime.MAX);

        List<OrderWithPriceAndTime> orders = new ArrayList<>();
        if (username == null) {
            orders.addAll(orderRepository.findByCreatedAtBetween(startOfDay, endOfDay));
            orders.addAll(roomOrderRepository.getAllByBookedDayBetweenAndDeletedIsFalse(startDate, endDate));
        } else {
            orders.addAll(orderRepository.findByAuthorNameAndCreatedAtBetween(username, startOfDay, endOfDay));
            orders.addAll(roomOrderRepository.getAllByBookedDayBetweenAndDeletedIsFalseAndAuthorName(startDate, endDate, username));
        }

        ResponseReportByParamsDto responseReportByParamsDto = new ResponseReportByParamsDto(orders.size(), 0L, 0.0);
        orders.forEach(order -> {
            responseReportByParamsDto.setProfit(responseReportByParamsDto.getProfit() + order.getTotalFullPrice());
            responseReportByParamsDto.setTotalTime(responseReportByParamsDto.getTotalTime() + order.getTotalFullTime());
        });

        return responseReportByParamsDto;
    }

    @Override
    public ResponseReportProfitDto getReportProfitInRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = LocalDateTime.of(startDate, LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.of(endDate, LocalTime.MAX);

        List<OrderWithPriceAndTime> orders = new ArrayList<>();

        orders.addAll(orderRepository.findByCreatedAtBetween(startOfDay, endOfDay));
        orders.addAll(roomOrderRepository.getAllByBookedDayBetweenAndDeletedIsFalse(startDate, endDate));

        ResponseReportProfitDto result = new ResponseReportProfitDto(0.0, 0.0);

        orders.forEach(order -> result.setIncome(result.getIncome() + order.getTotalFullPrice()));

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
        log.info("prof: {}", requestCreateProfitDto);
        var profit = new Profit();
        profit.setProfit(requestCreateProfitDto.getProfit());
        profit.setReason(requestCreateProfitDto.getReason());
        profit.setType(requestCreateProfitDto.getIsExpense() ? ProfitTypes.EXPENSE : ProfitTypes.INCOME);
        profitRepository.save(profit);
    }


    @Override
    public byte[] getReportExcel(LocalDate startDate, LocalDate endDate) {
        List<OrderWithPriceAndTime> orders = new ArrayList<>();
        LocalDateTime startOfDay = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(endDate, LocalTime.MAX);

        orders.addAll(orderRepository.findByCreatedAtBetween(startOfDay, endOfDay));
        orders.addAll(roomOrderRepository.getAllByBookedDayBetweenAndDeletedIsFalse(startDate, endDate));

        try (var workbook = new XSSFWorkbook()) {
            String sheetName = String.format("Отчет о сотрудниках, %s - %s", startDate, endDate);
            log.info("sheetName: {}", sheetName);
            var sheet = workbook.createSheet(sheetName);
            sheet.setDefaultColumnWidth(25);
            ExcelUtil.createHeader(sheet, workbook);
            var style = ExcelUtil.createCellStyle(workbook);
            var excelRowMap = ExcelUtil.processOrders(orders);

            int rowCount = 1;

            for (var employee : excelRowMap.entrySet()) {
                Row row = sheet.createRow(rowCount++);
                for (var key : ExcelUtil.excelKeys) {
                    Cell cell = row.createCell(key.getKey().ordinal());
                    cell.setCellStyle(style);
                    var data = employee.getValue().getByKeyIndexType(key.getKey());

                    if (key.getKey() == KeyIndexType.MIN_TIME
                            || key.getKey() == KeyIndexType.MAX_TIME
                            || key.getKey() == KeyIndexType.FULL_TIME
                    ) {
                        long seconds = Long.parseLong(data.toString());
                        cell.setCellValue(getDuration(Duration.ofSeconds(seconds)));
                    } else if (key.getKey() == KeyIndexType.AVG_TIME) {
                        long seconds = (long) Double.parseDouble(data.toString());
                        cell.setCellValue(getDuration(Duration.ofSeconds(seconds)));
                    } else cell.setCellValue(employee.getValue().getByKeyIndexType(key.getKey()).toString());

                }
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write(stream);

            return stream.toByteArray();
        } catch (IOException e) {
            log.info("IOException: {}", e.toString());
            return null;
        }
    }

    @Override
    public List<ResponseReportDetailProfitDto> getReportProfitDetailInRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = LocalDateTime.of(startDate, LocalTime.MIN);

        LocalDateTime endOfDay = LocalDateTime.of(endDate, LocalTime.MAX);

        var profits = profitRepository.findAllByCreateAtBetween(startOfDay, endOfDay);

        List<ResponseReportDetailProfitDto> result = new ArrayList<>();

        profits.forEach(i -> {
            ResponseReportDetailProfitDto responseReportDetailProfitDto = new ResponseReportDetailProfitDto();
            responseReportDetailProfitDto.setTitle(i.getReason());
            responseReportDetailProfitDto.setProfit(i.getProfit());
            responseReportDetailProfitDto.setIncome(i.getType().ordinal() == 0);
            result.add(responseReportDetailProfitDto);
        });

        return result;
    }

    private String getDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%sч. %sм.", hours, minutes);
    }


}


