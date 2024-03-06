package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateBaseAbonementDto;
import kz.miniland.minilandserver.dtos.response.ResponseBaseAbonementDto;
import kz.miniland.minilandserver.services.BaseAbonementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping()
    public ResponseEntity<Void> createBaseAbonement(
            @RequestBody @Valid RequestCreateBaseAbonementDto requestCreateBaseAbonementDto){

        baseAbonementService.createBaseAbonement(requestCreateBaseAbonementDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseAbonementById(@PathVariable("id") Long id){

        baseAbonementService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



}
