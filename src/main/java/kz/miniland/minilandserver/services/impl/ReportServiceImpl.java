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
import kz.miniland.minilandserver.services.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
}


