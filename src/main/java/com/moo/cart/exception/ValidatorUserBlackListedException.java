package com.moo.cart.exception;

import com.gamesys.registrationservice.api.ErrorEnum;

public class ValidatorUserBlackListedException extends ValidatorGenericException {

    public ValidatorUserBlackListedException(String fieldName, ErrorEnum errorCode){
        super(fieldName, errorCode);
    }
}
