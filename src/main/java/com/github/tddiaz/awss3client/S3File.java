package com.github.tddiaz.awss3client;

import lombok.Builder;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Data
@Builder
public class S3File {

    private String name;
    private String contentType;
    private byte[] data;
    private String parentFolderName;

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.data);
    }
}
