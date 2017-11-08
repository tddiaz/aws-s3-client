package com.github.tddiaz.awsS3Service.settings;

/**
 * Created by tristandiaz on 11/8/17.
 */
public class S3Bucket {

    private final String bucketName;

    private final String folderPrefix;

    public S3Bucket(String bucketName, String folderPrefix) {
        this.bucketName = bucketName;
        this.folderPrefix = folderPrefix;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getFolderPrefix() {
        return folderPrefix;
    }
}
