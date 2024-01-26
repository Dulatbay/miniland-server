package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.ResponseReportProfitDto;
import kz.miniland.minilandserver.dtos.ResponseTableReportDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportService {
    ResponseTableReportDto getTableReport();

    ResponseReportByParamsDto getReportByParams(String username, LocalDate startDate, LocalDate endDate);

    ResponseReportProfitDto getReportProfitInRange(LocalDate startDate, LocalDate endDate);

    void createProfit(RequestCreateProfitDto requestCreateProfitDto);
}
