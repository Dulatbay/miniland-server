package kz.miniland.minilandserver.exceptions.handler;

import kz.miniland.minilandserver.dtos.ResponseErrorDto;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DbObjectNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> handlePositionNotFoundException(DbObjectNotFoundException ex) {
        log.error("DbObjectNotFoundException exception: ", ex);
        ResponseErrorDto errorResponse = new ResponseErrorDto(ex.getMessage(), ex.getError(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseErrorDto> argumentExceptionHandler(IllegalArgumentException e) {
        log.error("Argument exception: ", e);
        var errorResponse = new ResponseErrorDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
