package com.github.tddiaz.awss3client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.github.tddiaz.awss3client.config.S3Bucket;
import com.github.tddiaz.awss3client.exception.S3ObjectNotFoundException;
import com.github.tddiaz.awss3client.exception.S3ObjectStorageException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class S3ClientImplTest {

    private S3ClientImpl s3ClientImpl;

    private AmazonS3 s3Client;

    @Before
    public void setup() {
        this.s3Client = mock(AmazonS3.class);
        this.s3ClientImpl = new S3ClientImpl(new S3Bucket("my-bucket", "my-folder"), s3Client);
    }

    @Test
    public void testUploadFile() {

        var s3File = S3File.builder()
                .contentType("image/png")
                .data("abc".getBytes())
                .name("my-file.png")
                .parentFolderName("sub-folder")
                .build();

        s3ClientImpl.uploadFile(s3File);

        var putObjectArgument = ArgumentCaptor.forClass(PutObjectRequest.class);

        verify(s3Client).putObject(putObjectArgument.capture());

        var putObjectRequest = putObjectArgument.getValue();
        assertThat(putObjectRequest.getBucketName()).isEqualTo("my-bucket");
        assertThat(putObjectRequest.getInputStream()).isNotNull();
        assertThat(putObjectRequest.getKey()).isEqualTo("my-folder/sub-folder/my-file.png");
        assertThat(putObjectRequest.getMetadata().getContentType()).isEqualTo("image/png");
        assertThat(putObjectRequest.getMetadata().getContentLength()).isEqualTo(s3File.getData().length);
    }

    @Test(expected = S3ObjectStorageException.class)
    public void testUploadFile_error() {
        when(s3Client.putObject(any(PutObjectRequest.class))).thenThrow(RuntimeException.class);

        var s3File = S3File.builder()
                .contentType("image/png")
                .data("abc".getBytes())
                .name("my-file")
                .parentFolderName("sub-folder")
                .build();

        s3ClientImpl.uploadFile(s3File);
    }

    @Test
    public void testGetFile() {

        var objectMedataData = new ObjectMetadata();
        objectMedataData.setContentType("image/png");

        var s3Object = new S3Object();
        s3Object.setObjectMetadata(objectMedataData);
        s3Object.setObjectContent(new ByteArrayInputStream("abc".getBytes()));

        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);

        var s3File = s3ClientImpl.getFile("my-file.png", "sub-folder");
        assertThat(s3File.getName()).isEqualTo("my-file.png");
        assertThat(s3File.getParentFolderName()).isEqualTo("sub-folder");
        assertThat(s3File.getData()).isEqualTo("abc".getBytes());
        assertThat(s3File.getContentType()).isEqualTo(objectMedataData.getContentType());

        var argumentCaptor = ArgumentCaptor.forClass(GetObjectRequest.class);

        verify(s3Client).getObject(argumentCaptor.capture());

        var getObjectRequest = argumentCaptor.getValue();
        assertThat(getObjectRequest.getBucketName()).isEqualTo("my-bucket");
        assertThat(getObjectRequest.getKey()).isEqualTo("my-folder/sub-folder/my-file.png");
    }

    @Test(expected = S3ObjectNotFoundException.class)
    public void testGetFile_error() {
        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(RuntimeException.class);
        s3ClientImpl.getFile("my-file.png", "sub-folder");
    }
}