package kz.miniland.minilandserver.controllers;


import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportDetailProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseTableReportDto;
import kz.miniland.minilandserver.services.KeycloakService;
import kz.miniland.minilandserver.services.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;
    private final KeycloakService keycloakService;

    @GetMapping("/table-report")
    public ResponseEntity<ResponseTableReportDto> getMainDirectorReport() {
        return ResponseEntity.ok(reportService.getTableReport());
    }

    @GetMapping("/report")
    public ResponseEntity<ResponseReportByParamsDto> getReportByParams(@RequestParam("start_date") LocalDate startDate,
                                                                       @RequestParam("end_date") LocalDate endDate,
                                                                       @RequestParam(value = "username", required = false) String username
    ) {
        return ResponseEntity.ok(reportService.getReportByParams(username, startDate.atStartOfDay(ZONE_ID).toLocalDate(), endDate.atStartOfDay(ZONE_ID).toLocalDate()));
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> getReportExcel(@RequestParam("start_date") LocalDate startDate,
                                                 @RequestParam("end_date") LocalDate endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", String.format("%s - %s", startDate, endDate) + ".xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportService.getReportExcel(startDate, endDate));
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<String>> getUsernames() {
        return ResponseEntity.ok(keycloakService.getUsernames());
    }

    @GetMapping("/profits")
    public ResponseEntity<ResponseReportProfitDto> getReportProfit(@RequestParam("start_date") LocalDate startDate,
                                                                   @RequestParam("end_date") LocalDate endDate) {
        return ResponseEntity.ok(reportService.getReportProfitInRange(startDate.atStartOfDay(ZONE_ID).toLocalDate(), endDate.atStartOfDay(ZONE_ID).toLocalDate()));
    }

    @GetMapping("/profits/details")
    public ResponseEntity<List<ResponseReportDetailProfitDto>> getReportProfitDetail(@RequestParam("start_date") LocalDate startDate,
                                                                               @RequestParam("end_date") LocalDate endDate) {
        return ResponseEntity.ok(reportService.getReportProfitDetailInRange(startDate.atStartOfDay(ZONE_ID).toLocalDate(), endDate.atStartOfDay(ZONE_ID).toLocalDate()));
    }

    @PostMapping("/profits")
    public ResponseEntity<Void> createProfit(@RequestBody @Valid RequestCreateProfitDto requestCreateProfitDto) {
        reportService.createProfit(requestCreateProfitDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}


