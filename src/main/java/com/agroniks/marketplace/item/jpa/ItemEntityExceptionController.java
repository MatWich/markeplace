package com.agroniks.marketplace.item.jpa;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ItemEntityExceptionController {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    String itemAlreadyExists(ItemAlreadyExists ex) {
        return ex.getMessage();
    }
}
