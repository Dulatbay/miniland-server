package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.services.RoomTariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room-tariffs")
public class RoomTariffController {
    private final RoomTariffService roomTariffService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCardRoomTariffDto> getRoomTariffById(@PathVariable("id") Long id) {
        ResponseCardRoomTariffDto tariff = roomTariffService.getTariffById(id);
        return ResponseEntity.ok(tariff);
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCardRoomTariffDto>> getAllRoomTariffs(@RequestParam(value = "enabled") Boolean enabled) {
        List<ResponseCardRoomTariffDto> allTariffs = roomTariffService.getAllTariffsByEnabled(enabled);
        return ResponseEntity.ok(allTariffs);
    }

    @PostMapping()
    public ResponseEntity<Void> createTariff(@RequestBody @Valid RequestCreateTariffDto requestCreateTariffDto) {
        roomTariffService.create(requestCreateTariffDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
