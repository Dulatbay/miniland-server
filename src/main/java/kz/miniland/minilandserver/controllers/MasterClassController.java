package kz.miniland.minilandserver.controllers;

import kz.miniland.minilandserver.services.MasterClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master-classes")
public class MasterClassController {
    private final MasterClassService masterClassService;

    @PostMapping("/add-to-order")
    public ResponseEntity<Void> addMasterClassToOrder(@RequestParam("master-class-id") Long masterClassId,
                                                      @RequestParam("order-id") Long orderId) {

        var token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        masterClassService.addMasterClassToOrder(token.getName(), orderId, masterClassId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
