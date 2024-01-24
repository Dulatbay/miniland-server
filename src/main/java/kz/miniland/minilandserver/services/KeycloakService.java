package kz.miniland.minilandserver.services;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    UserRepresentation getUserById(String username);
}
