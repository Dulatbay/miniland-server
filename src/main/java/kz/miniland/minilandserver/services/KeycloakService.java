package kz.miniland.minilandserver.services;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public interface KeycloakService {
    Optional<UserRepresentation> getUserByUsername(String username);
    List<String> getUsernames();
}
