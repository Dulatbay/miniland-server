package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomTariffDto;
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
    public ResponseEntity<ResponseDetailRoomTariffDto> getRoomTariffById(@PathVariable("id") Long id) {
        var tariff = roomTariffService.getTariffById(id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableRoomTariff(@PathVariable("id") Long id) {
        roomTariffService.disableRoomTariffById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
