package com.github.tddiaz.awss3client.exception;


public class S3ObjectStorageException extends RuntimeException {

    private static final long serialVersionUID = 5715098444159972437L;

    public S3ObjectStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
