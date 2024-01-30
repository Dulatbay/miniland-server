package kz.miniland.minilandserver.controllers;


import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportByParamsDto;
import kz.miniland.minilandserver.dtos.response.ResponseReportProfitDto;
import kz.miniland.minilandserver.dtos.response.ResponseTableReportDto;
import kz.miniland.minilandserver.services.KeycloakService;
import kz.miniland.minilandserver.services.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static kz.miniland.minilandserver.constants.ValueConstants.ZONE_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reports/")
public class ReportController   {
    private final ReportService reportService;
    private final KeycloakService keycloakService;
    @GetMapping("/get-table-report")
    public ResponseEntity<ResponseTableReportDto> getMainDirectorReport(){
        return ResponseEntity.ok(reportService.getTableReport());
    }

    @GetMapping("/get-report")
    public ResponseEntity<ResponseReportByParamsDto> getReportByParams(@RequestParam("start_date") LocalDate startDate,
                                                                       @RequestParam("end_date") LocalDate endDate,
                                                                       @RequestParam(value = "username", required = false) String username
                                                                       ){
        log.info("{}", LocalDateTime.now(ZONE_ID));
        return ResponseEntity.ok(reportService.getReportByParams(username, startDate.atStartOfDay(ZONE_ID).toLocalDate(), endDate.atStartOfDay(ZONE_ID).toLocalDate()));
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<String>> getUsernames(){
        return ResponseEntity.ok(keycloakService.getUsernames());
    }

    @GetMapping("/profits/get-by-params")
    public ResponseEntity<ResponseReportProfitDto> getReportProfit(@RequestParam("start_date") LocalDate startDate,
                                                                   @RequestParam("end_date") LocalDate endDate){
        return ResponseEntity.ok(reportService.getReportProfitInRange(startDate.atStartOfDay(ZONE_ID).toLocalDate(), endDate.atStartOfDay(ZONE_ID).toLocalDate()));
    }

    @PostMapping("/profits/create")
    public ResponseEntity<Void> createProfit(@RequestBody @Valid RequestCreateProfitDto requestCreateProfitDto){
        reportService.createProfit(requestCreateProfitDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}


