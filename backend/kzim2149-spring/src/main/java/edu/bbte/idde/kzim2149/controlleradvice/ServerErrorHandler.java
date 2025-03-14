package edu.bbte.idde.kzim2149.controlleradvice;

import edu.bbte.idde.kzim2149.exception.RepoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ServerErrorHandler {
    @ExceptionHandler(RepoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRepoException(RepoException e) {
        log.debug("RepoException occured, ", e);
        return Map.of("message", e.getMessage());
    }
}
