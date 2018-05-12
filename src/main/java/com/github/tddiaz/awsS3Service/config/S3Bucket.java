package com.github.tddiaz.awsS3Service.config;

/**
 * Created by tristandiaz on 11/8/17.
 */
public class S3Bucket {

    private final String name;

    private final String folderDirectory;

    public S3Bucket(String name, String folderDirectory) {
        this.name = name;
        this.folderDirectory = folderDirectory;
    }

    public String getName() {
        return name;
    }

    public String getFolderDirectory() {
        return folderDirectory;
    }
}
