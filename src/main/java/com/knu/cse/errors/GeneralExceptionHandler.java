package com.knu.cse.errors;

import com.knu.cse.utils.ApiUtils;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.knu.cse.utils.ApiUtils.error;

@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(message, status), headers, status);
    }

    // 필요한 경우 적절한 예외타입을 선언하고 newResponse 메소드를 통해 응답 생성

    @ExceptionHandler({
        NoHandlerFoundException.class,
        NotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(Exception e) {
        return newResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
        IllegalArgumentException.class,
        IllegalStateException.class,
        ConstraintViolationException.class,
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        if (e instanceof MethodArgumentNotValidException) {
            return newResponse(
                ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                HttpStatus.BAD_REQUEST
            );
        }
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

