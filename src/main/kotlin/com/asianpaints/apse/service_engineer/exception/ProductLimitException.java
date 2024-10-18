package com.asianpaints.apse.service_engineer.exception;

public class ProductLimitException extends RuntimeException{
    public ProductLimitException(String msg) {
        super(msg);
    }
}
