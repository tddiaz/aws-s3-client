package com.github.tddiaz.awsS3Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.github.tddiaz.awsS3Service.config.S3Bucket;
import com.github.tddiaz.awsS3Service.exception.S3ObjectNotFoundException;
import com.github.tddiaz.awsS3Service.exception.S3ObjectStorageException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.InputStream;

public class S3Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    private final S3Bucket s3Bucket;

    private final AmazonS3 s3Client;

    public S3Service(S3Bucket s3Bucket, AmazonS3 s3Client) {
        this.s3Bucket = s3Bucket;
        this.s3Client = s3Client;
    }

    public void storeFile(S3File s3File) {
        Assert.notNull(s3File, "S3File argument cannot be null.");

        try (final InputStream inputStream = s3File.getInputStream()) {

            final ObjectMetadata metadata = createMetaData(s3File);
            final String objectKey = buildObjectKey(s3File.getName(), s3File.getParentFolderName());

            LOGGER.info("S3 object key of file {}: {}", s3File.getName(), objectKey);

            s3Client.putObject(new PutObjectRequest(s3Bucket.getName(), objectKey, inputStream, metadata));

        } catch (Exception e) {
            throw new S3ObjectStorageException("Error Saving PDF to S3. File name: " + s3File.getName(), e);
        }

        LOGGER.info("Stored new file in S3: {}", s3File.getName());
    }

    public InputStream getInputStream(String fileName, String parentFolderName) {
        Assert.notNull(fileName, "fileName cannot be null");
        LOGGER.info("Getting content of file {} from S3", fileName);

        try {
            final String objectKey = buildObjectKey(fileName, parentFolderName);

            final S3Object s3Object = s3Client.getObject(new GetObjectRequest(s3Bucket.getName(), objectKey));

            return s3Object.getObjectContent();

        } catch (Exception e) {
            throw new S3ObjectNotFoundException("Error getting file from S3. File name: " + fileName, e);
        }
    }

    private ObjectMetadata createMetaData(S3File s3File) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(s3File.getData().length);
        metadata.setContentType(s3File.getContentType());

        return metadata;
    }

    private String buildObjectKey(String fileName, String parentFolderName) {
        final StringBuilder stringBuilder = new StringBuilder(255).append(s3Bucket.getFolderDirectory());
        if (StringUtils.isNotBlank(parentFolderName)) {
            stringBuilder.append(parentFolderName);
        }
        stringBuilder.append(fileName);

        return stringBuilder.toString();
    }

}
