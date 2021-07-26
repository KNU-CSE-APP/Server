package com.knu.cse.errors;

import com.knu.cse.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GeneralExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        BindException.class,
        MethodArgumentTypeMismatchException.class
    })
    @ResponseBody
    protected ApiUtils.ApiResult handleMethodArgumentNotValidException(Exception e) {
        log.error(e.getMessage());
        return ApiUtils.error(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    protected ApiUtils.ApiResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return ApiUtils.error(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    protected ApiUtils.ApiResult handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage());
        return ApiUtils.error(e, HttpStatus.FORBIDDEN);
    }

    /**
     * 비즈니스 로직 수행 도중, 사용자의 요청 파라미터가 적절하지 않을 때 발생
     * 비즈니스 로직 수행 도중, 해당 도메인 객체의 상태가 로직을 수행할 수 없을 때 발생
     */
    @ExceptionHandler({
        IllegalStateException.class,
        IllegalArgumentException.class
    })
    @ResponseBody
    protected ApiUtils.ApiResult handleIllegalStatementException(Exception e){
        log.error(e.getMessage());
        return ApiUtils.error(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 비즈니스 로직 수행 도중, 객체를 찾을 수 없는 상태일 때 발생. 이 때 404 status code와 함께 반환한다.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    protected ApiUtils.ApiResult handleNotFoundException(Exception e) {
        log.error(e.getMessage());
        return ApiUtils.error(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 여기서 작성하지 않은 다른 모든 예외에 대해 처리한다. 이 때 500 status code와 함께 반환한다.
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ApiUtils.ApiResult handleException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return ApiUtils.error(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
