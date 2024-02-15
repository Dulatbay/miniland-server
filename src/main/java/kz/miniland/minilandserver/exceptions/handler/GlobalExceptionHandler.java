package kz.miniland.minilandserver.exceptions.handler;

import kz.miniland.minilandserver.dtos.response.ResponseErrorDto;
import kz.miniland.minilandserver.exceptions.DbObjectNotFoundException;
import kz.miniland.minilandserver.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.NotFoundException;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DbObjectNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> handlePositionNotFoundException(DbObjectNotFoundException ex) {
        log.error("DbObjectNotFoundException exception: ", ex);
        ResponseErrorDto errorResponse = new ResponseErrorDto(ex.getError(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseErrorDto> argumentExceptionHandler(IllegalArgumentException e) {
        log.error("Argument exception: ", e);
        var errorResponse = new ResponseErrorDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseErrorDto> notFoundException(NotFoundException ex) {
        log.error("NotFoundException exception: ", ex);
        ResponseErrorDto errorResponse = new ResponseErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException exception: ", ex);
        String error = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ResponseErrorDto errorResponse = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), error, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseErrorDto> handleIOException(IOException ex) {
        log.error("IOException exception: ", ex);
        ResponseErrorDto errorResponse = new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Something get error...", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ResponseErrorDto> handleFileSizeLimitExceededException(FileSizeLimitExceededException ex) {
        log.error("FileSizeLimitExceededException: ", ex);
        ResponseErrorDto errorResponse = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), "The field image exceeds its maximum permitted size of 100MB", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ResponseErrorDto> handleStorageException(StorageException ex) {
        log.error("StorageException: ", ex);
        ResponseErrorDto errorResponse = new ResponseErrorDto(ex.getError(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
