package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.dtos.request.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportDetailProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseTableReportDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    ResponseTableReportDto getTableReport();

    ResponseReportByParamsDto getReportByParams(String username, LocalDate startDate, LocalDate endDate);

    ResponseReportProfitDto getReportProfitInRange(LocalDate startDate, LocalDate endDate);

    void createProfit(RequestCreateProfitDto requestCreateProfitDto);

    byte[] getReportExcel(LocalDate startDate, LocalDate endDate);

    List<ResponseReportDetailProfitDto> getReportProfitDetailInRange(LocalDate localDate, LocalDate localDate1);
}
