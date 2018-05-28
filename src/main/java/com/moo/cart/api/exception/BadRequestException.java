package com.moo.cart.api.exception;


import com.moo.cart.api.ErrorEnum;

public class BadRequestException extends BaseHTTPException {

    public BadRequestException(String logMessage, ErrorEnum error){
        super(logMessage, error);
    }

}
