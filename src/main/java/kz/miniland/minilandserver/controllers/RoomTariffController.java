package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.services.RoomOrderService;
import kz.miniland.minilandserver.services.RoomTariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
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
    public ResponseEntity<List<ResponseCardRoomTariffDto>> getAllRoomTariffs(@PathParam("enabled") Boolean enabled) {
        List<ResponseCardRoomTariffDto> allTariffs = roomTariffService.getAllTariffsByEnabled(enabled);
        return ResponseEntity.ok(allTariffs);
    }
}