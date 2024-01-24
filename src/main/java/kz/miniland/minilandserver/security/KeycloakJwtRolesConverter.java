package kz.miniland.minilandserver.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakJwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String CLAIM_REALM_ACCESS = "realm_access";
    private static final String CLAIM_ROLES = "roles";


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Map<String, Collection<String>> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);

        if (realmAccess != null && !realmAccess.isEmpty()) {
            Collection<String> roles = realmAccess.get(CLAIM_ROLES);
            if (roles != null && !roles.isEmpty()) {
                Collection<GrantedAuthority> realmRoles = new ArrayList<>();
                grantedAuthorities.addAll(realmRoles);
            }
        }
        return grantedAuthorities;
    }
}
