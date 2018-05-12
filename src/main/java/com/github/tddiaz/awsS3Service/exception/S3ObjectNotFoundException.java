package com.github.tddiaz.awsS3Service.exception;

public class S3ObjectNotFoundException extends RuntimeException {
    public S3ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
