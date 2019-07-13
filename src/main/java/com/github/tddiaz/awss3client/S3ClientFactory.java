package com.github.tddiaz.awss3client;

import com.amazonaws.services.s3.AmazonS3;
import com.github.tddiaz.awss3client.config.S3Bucket;

public class S3ClientFactory {
    public static S3Client create(S3Bucket s3Bucket, AmazonS3 awsS3Client) {
        return new S3ClientImpl(s3Bucket, awsS3Client);
    }
}
