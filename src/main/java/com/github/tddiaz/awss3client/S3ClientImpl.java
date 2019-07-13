package com.github.tddiaz.awss3client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.tddiaz.awss3client.config.S3Bucket;
import com.github.tddiaz.awss3client.exception.S3ObjectNotFoundException;
import com.github.tddiaz.awss3client.exception.S3ObjectStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Provides a simple interface to access amazon s3 for storing and getting s3 objects
 *
 *
 * @author Tristan Diaz
 */
@Slf4j
@RequiredArgsConstructor
class S3ClientImpl implements S3Client {

    /**
     * Holds bucket information; bucket name and folder path
     *
     * @see S3Bucket
     */
    private final S3Bucket s3Bucket;


    /**
     *  Interface for accessing Amazon S3
     */
    private final AmazonS3 s3Client;



    /**
     * Uploads file to specified S3 bucket
     *
     *
     * @param s3File holds data of the file to be uploaded
     * @see S3File
     */
    @Override
    public void uploadFile(S3File s3File) {
        Objects.requireNonNull(s3File, "S3File argument cannot be null.");

        try (var inputStream = s3File.getInputStream()) {

            var metadata = createMetaData(s3File);
            var objectKey = buildObjectKey(s3File.getName(), s3File.getParentFolderName());

            LOGGER.info("S3 object key of file {}: {}", s3File.getName(), objectKey);

            s3Client.putObject(new PutObjectRequest(s3Bucket.getName(), objectKey, inputStream, metadata));

        } catch (Exception e) {
            throw new S3ObjectStorageException("Error Saving file to S3. File name: " + s3File.getName(), e);
        }

        LOGGER.info("Successfully stored file in S3: {}", s3File.getName());
    }


    /**
     * Retrieves file from specified S3 bucket
     *
     *
     * @param fileName name of the file
     * @param parentFolderName name of the parent folder if any
     *
     * @return S3File holds data of the file retrieved from S3
     */
    @Override
    public S3File getFile(String fileName, String parentFolderName) {
        Objects.requireNonNull(fileName, "fileName cannot be null");

        try {
            var objectKey = buildObjectKey(fileName, parentFolderName);
            var s3Object = s3Client.getObject(new GetObjectRequest(s3Bucket.getName(), objectKey));

            return S3File.builder()
                    .contentType(s3Object.getObjectMetadata().getContentType())
                    .name(fileName)
                    .parentFolderName(parentFolderName)
                    .data(IOUtils.toByteArray(s3Object.getObjectContent()))
                    .build();

        } catch (Exception e) {
            throw new S3ObjectNotFoundException("Error getting file from S3. File name: " + fileName, e);
        }
    }

    private ObjectMetadata createMetaData(S3File s3File) {
        var metadata = new ObjectMetadata();
        metadata.setContentLength(s3File.getData().length);
        metadata.setContentType(s3File.getContentType());

        return metadata;
    }

    private String buildObjectKey(String fileName, String parentFolderName) {

        var folderNames = new ArrayList<String>(3);
        folderNames.add(s3Bucket.getFolderPath());

        if (StringUtils.isNotBlank(parentFolderName)) {
            folderNames.add(parentFolderName);
        }

        folderNames.add(fileName);

        return String.join("/", folderNames);
    }

}
