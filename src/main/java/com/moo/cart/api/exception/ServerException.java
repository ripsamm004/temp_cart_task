package com.moo.cart.api.exception;

import com.moo.cart.api.ErrorEnum;

public class ServerException extends BaseHTTPException {

    public ServerException(String logMessage, ErrorEnum error){
        super(logMessage, error);
    }
}
