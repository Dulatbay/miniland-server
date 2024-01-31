package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateRoomDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardRoomDto;
import kz.miniland.minilandserver.dtos.response.ResponseDetailRoomDto;
import kz.miniland.minilandserver.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping()
    public List<ResponseCardRoomDto> getAllRooms() {
        return null;
    }

    @GetMapping("/today")
    public ResponseEntity<ResponseCardRoomDto> getTodayActiveRoom() {
        return null;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseDetailRoomDto> getDetailById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<Void> createRoom(@Valid @RequestBody RequestCreateRoomDto requestCreateRoomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
