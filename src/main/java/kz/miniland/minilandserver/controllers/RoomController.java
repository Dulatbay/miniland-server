package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateRoomDto;
import kz.miniland.minilandserver.dtos.response.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDetailOrderDto> getOrderDetailById(@PathVariable("id") Long id){
        ResponseDetailOrderDto order = roomService.getOrderDetailById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders/today")
    public ResponseEntity<List<ResponseCardRoomOrderDto>> getTodayActiveRooms() {
        List<ResponseCardRoomOrderDto> allActiveRooms = roomService.getAllCurrentActiveRooms();
        return ResponseEntity.ok(allActiveRooms);
    }

    @GetMapping("/tariffs/{id}")
    public ResponseEntity<ResponseCardRoomTariffDto> getRoomTariffById(@PathVariable("id") Long id) {
        ResponseCardRoomTariffDto tariff = roomService.getTariffById(id);
        return ResponseEntity.ok(tariff);
    }

    @GetMapping("/tariffs")
    public ResponseEntity<List<ResponseCardRoomTariffDto>> getAllRoomTariffs(@PathParam("enabled") Boolean enabled) {
        List<ResponseCardRoomTariffDto> allTariffs = roomService.getAllTariffsByEnabled(enabled);
        return ResponseEntity.ok(allTariffs);
    }

    @GetMapping("/tariffs/booked-days")
    public ResponseEntity<List<ResponseBookedDayDto>> getAllBookedDays() {
        List<ResponseBookedDayDto> bookedDays = roomService.getBookedDaysAfterDay();
        return ResponseEntity.ok(bookedDays);
    }

    @PostMapping("/tariffs")
    public ResponseEntity<Void> createRoom(@Valid @RequestBody RequestCreateRoomDto requestCreateRoomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
