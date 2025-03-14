package edu.bbte.idde.kzim2149.controlleradvice;

import edu.bbte.idde.kzim2149.exception.BadRequestException;
import edu.bbte.idde.kzim2149.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class InvalidParameterErrorHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMyException(BadRequestException e) {
        log.debug("BadRequestException occurred", e);
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleMyException(NotFoundException e) {
        log.debug("NotFoundException occurred", e);
        return Map.of("message", e.getMessage());
    }
}
