package com.moo.cart.api.exception;


import com.moo.cart.api.ErrorEnum;

public class ForbiddenException extends BaseHTTPException {

    public ForbiddenException(String logMessage, ErrorEnum error){
        super(logMessage, error);
    }

}
