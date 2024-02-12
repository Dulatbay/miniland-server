package kz.miniland.minilandserver.controllers;

import jakarta.validation.Valid;
import kz.miniland.minilandserver.dtos.request.RequestCreateMasterClassDto;
import kz.miniland.minilandserver.dtos.response.ResponseCardMasterClassDto;
import kz.miniland.minilandserver.services.MasterClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master-classes")
public class MasterClassController {
    private final MasterClassService masterClassService;

    @PostMapping("/{master-class-id}/add-to-order/{order-id}")
    public ResponseEntity<Void> addMasterClassToOrder(@PathVariable("master-class-id") Long masterClassId,
                                                      @PathVariable("order-id") Long orderId) {

        var token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        masterClassService.addMasterClassToOrder(token.getToken().getClaim("preferred_username"), orderId, masterClassId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createMasterClass(@ModelAttribute @Valid RequestCreateMasterClassDto requestCreateMasterClassDto) throws IOException {
        masterClassService.createMasterClass(requestCreateMasterClassDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMasterClass(@PathVariable("id") Long id) {
        masterClassService.disableMasterClass(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCardMasterClassDto>> getAllMasterClassCards(@RequestParam("enabled") boolean enabled) {
        var result = masterClassService.getAllMasterClasses(enabled);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCardMasterClassDto> getMasterClassById(@PathVariable("id") Long id) {
        ResponseCardMasterClassDto responseCardMasterClassDto = masterClassService.getMasterClassById(id);
        return ResponseEntity.ok(responseCardMasterClassDto);
    }
}
