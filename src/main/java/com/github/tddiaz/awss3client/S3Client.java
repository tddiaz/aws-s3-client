package com.github.tddiaz.awss3client;

/**
 * Provides a simple interface to access amazon s3 for storing and getting s3 objects
 */
public interface S3Client {


    /**
     * Uploads file to specified S3 bucket
     *
     *
     * @param s3File holds data of the file to be uploaded
     * @see S3File
     */
    void uploadFile(S3File s3File);


    /**
     * Retrieves file from specified S3 bucket
     *
     *
     * @param fileName name of the file
     * @param parentFolderName name of the parent folder if any
     *
     * @return S3File holds data of the file retrieved from S3
     */
    S3File getFile(String fileName, String parentFolderName);
}
