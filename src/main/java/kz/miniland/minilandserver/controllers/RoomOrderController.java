package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseBookedDayDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomOrderDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomOrderDto;
import kz.miniland.minilandserver.services.RoomOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/room-orders")
public class RoomOrderController {
    private final RoomOrderService roomOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDetailRoomOrderDto> getOrderDetailById(@PathVariable("id") Long id) {
        ResponseDetailRoomOrderDto order = roomOrderService.getOrderDetailById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/today")
    public ResponseEntity<List<ResponseCardRoomOrderDto>> getTodayActiveRooms() {
        List<ResponseCardRoomOrderDto> allActiveRooms = roomOrderService.getAllCurrentActiveRooms();
        return ResponseEntity.ok(allActiveRooms);
    }

    @GetMapping("/booked-days/{id}")
    public ResponseEntity<List<ResponseBookedDayDto>> getAllBookedDays(@PathVariable("id") Long id) {
        List<ResponseBookedDayDto> bookedDays = roomOrderService.getBookedDaysAfterToday(id);
        return ResponseEntity.ok(bookedDays);
    }

    @PostMapping()
    public ResponseEntity<Void> createRoom(@Valid @RequestBody RequestCreateRoomOrderDto requestCreateRoomOrderDto) {
        var token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        requestCreateRoomOrderDto.setAuthorName(token.getToken().getClaim("preferred_username"));
        roomOrderService.createRoomOrder(requestCreateRoomOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
