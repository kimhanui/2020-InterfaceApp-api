package com.infe.app.web;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.TimeoutException;

/**
 * @RestControllerAdvice:
 * @ControllerAdvice와 동일한 역할 즉, 예외를 잡아 핸들링 할 수 있도록 하는 기능을 수행하면서 @ResponseBody를 통해 객체를 리턴할 수도 있다
 * 패키지단위로 예외제한할 수도 있다. @RestControllerAdvice("com.example.demo.login.controller")
 **/
@Log
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * RuntimeException.class, Exception.class 또는 Custom Exception을 인자로
     *
     * @ExceptionHandler(XXException.class) 라고 작성한 경우,
     * @ControllerAdvice에서 명시한 클래스에서 throw new XXException( .. )이 발생하면
     * 핸들러는 이를 감지하고 해당 메소드를 수행한다.
     **/
    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warning("IllegalArgumentException: " + e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TimeoutException.class})
    protected ResponseEntity<String> handleTimeoutException(TimeoutException e) {
        log.warning("TimeoutException: " + e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NullPointerException.class})
    protected ResponseEntity<String> handleTimeoutException(NullPointerException e) {
        log.warning("NullPointerException: " + e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<String> handleException(Exception e) {
        log.warning("Exception: " + e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
