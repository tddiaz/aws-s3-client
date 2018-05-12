package com.github.tddiaz.awsS3Service.exception;

public class S3ObjectStorageException extends RuntimeException {
    public S3ObjectStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
