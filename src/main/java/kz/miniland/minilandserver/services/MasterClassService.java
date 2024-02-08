package kz.miniland.minilandserver.services;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface MasterClassService {
    void addMasterClassToOrder(String token, Long orderId, Long masterClassId);
}
