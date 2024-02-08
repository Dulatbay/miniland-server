package kz.miniland.minilandserver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DbObjectNotFoundException extends RuntimeException {
    private final String error;
    private final String message;
}
