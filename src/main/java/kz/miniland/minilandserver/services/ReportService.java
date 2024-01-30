package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseTableReportDto;

import java.time.LocalDate;

public interface ReportService {
    ResponseTableReportDto getTableReport();

    ResponseReportByParamsDto getReportByParams(String username, LocalDate startDate, LocalDate endDate);

    ResponseReportProfitDto getReportProfitInRange(LocalDate startDate, LocalDate endDate);

    void createProfit(RequestCreateProfitDto requestCreateProfitDto);
}
