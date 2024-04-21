package kz.miniland.minilandserver.services.impl;

import kz.miniland.minilandserver.services.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kz.miniland.minilandserver.constants.ValueConstants.KEYCLOAK_REALM;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {
    private final Keycloak keycloak;

    @Override
    public Optional<UserRepresentation> getUserByUsername(String username) {
        var listOfUsers = keycloak.realm(KEYCLOAK_REALM).users().search(username);
        if (listOfUsers.isEmpty())
            return Optional.empty();
        return Optional.of(listOfUsers.getFirst());
    }

    @Override
    public List<String> getUsernames() {
        List<String> usernames = keycloak.realm(KEYCLOAK_REALM).users().list().stream().map(UserRepresentation::getUsername).collect(Collectors.toList());
        log.info("Usernames list size: {}", usernames.size());
        return usernames;
    }
}
