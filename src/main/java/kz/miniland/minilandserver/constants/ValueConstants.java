package kz.miniland.minilandserver.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValueConstants {
    public static ZoneId ZONE_ID = ZoneId.of("UTC+06:00"); // Almaty, Kazakhstan
    public static String KEYCLOAK_REALM = "miniland";
    public static String UPLOADED_FOLDER = "upload-dir/";
}
