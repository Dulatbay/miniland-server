package kz.miniland.minilandserver.controllers;

import kz.miniland.minilandserver.dtos.response.ResponseBaseAbonementDto;
import kz.miniland.minilandserver.services.BaseAbonementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/base-abonements")
public class BaseAbonementController {

    private final BaseAbonementService baseAbonementService;

    @GetMapping()
    public ResponseEntity<List<ResponseBaseAbonementDto>> getAllBaseAbonementsByEnabled(Boolean enabled){

        return ResponseEntity
                .ok(baseAbonementService.getAllByEnabled(enabled));

    }



}
