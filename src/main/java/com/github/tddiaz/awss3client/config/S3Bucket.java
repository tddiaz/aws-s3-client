package com.github.tddiaz.awss3client.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Holds necessary information for accessing specified S3 Bucket and performing operations on {@link com.github.tddiaz.awss3client.S3Client}
 *
 * @author Tristan Diaz
 */
@Data
@AllArgsConstructor
public class S3Bucket {

    /**
     * name of the bucket
     *
     */
    private final String name;

    /**
     * folder path or directory which serves as a location where to upload a given file
     *
     */
    private final String folderPath;
}
