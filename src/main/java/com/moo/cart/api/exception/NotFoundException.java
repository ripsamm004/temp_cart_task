package com.moo.cart.api.exception;


import com.moo.cart.api.ErrorEnum;

public class NotFoundException extends BaseHTTPException {
    public NotFoundException(String logMessage, ErrorEnum error){
        super(logMessage, error);
    }
}
