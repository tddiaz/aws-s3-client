package com.github.tddiaz.awss3client.exception;

public class S3ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8210974335633082370L;

    public S3ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
