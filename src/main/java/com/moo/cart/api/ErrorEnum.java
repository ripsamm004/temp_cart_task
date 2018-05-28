package com.moo.cart.api;

public enum ErrorEnum {


    // SERVER ERRORS
    INTERNAL_ERROR                          ("M0001","WE ARE EXPERIENCING SOME ISSUES, PLEASE TRY LATER"),
    NO_FOUND_EXCEPTION                      ("M0002","NO FOUND EXCEPTION"),
    BAD_REQUEST_EXCEPTION                   ("M0003","BAD REQUEST EXCEPTION"),
    FORBIDDEN_EXCEPTION                     ("M0004","FORBIDDEN EXCEPTION"),
    SERVER_EXCEPTION                        ("M0005","SERVER EXCEPTION"),

    // API/CONFIG ERRORS
    API_ERROR_CART_NOT_FOUND                ("A0001","CART NOT FOUND"),
    API_ERROR_PRODUCT_CODE_NOT_CORRECT      ("A0002","PRODUCT CODE NOT CORRECT"),
    API_ERROR_REQUEST_BODY_FORMAT_INVALID   ("A0003","REQUEST BODY FORMAT INVALID"),
    API_ERROR_INVALID_REQUEST_BODY          ("A0004","INVALID REQUEST BODY")
    ;

    private String code;
    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code+","+message;
    }

    public static ErrorEnum getByCode(String code) {
        if (code == null) return null;
        for(ErrorEnum ec : values()) {
            if( (ec.code).equalsIgnoreCase(code)){
                return ec;
            }
        }
        return INTERNAL_ERROR;
    }
}
