package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateRoomDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomTariffDto;
import kz.miniland.minilandserver.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/orders/today")
    public ResponseEntity<ResponseCardRoomOrderDto> getTodayActiveRooms() {
        return null;
    }

    @GetMapping("/tariffs/{id}")
    public ResponseEntity<ResponseCardRoomTariffDto> getRoomTariffById(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/tariffs")
    public ResponseEntity<List<ResponseCardRoomTariffDto>> getAllRoomTariffs(@PathParam("enabled") Boolean enabled) {
        return null;
    }

    @GetMapping("/tariffs/booked-days")
    public ResponseEntity<List<String>> getAllBookedDays() {
        return null;
    }

    @PostMapping("/tariffs")
    public ResponseEntity<Void> createRoom(@Valid @RequestBody RequestCreateRoomDto requestCreateRoomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
