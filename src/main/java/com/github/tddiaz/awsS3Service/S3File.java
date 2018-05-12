package com.github.tddiaz.awsS3Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class S3File {

    private String name;
    private String contentType;
    private byte[] data;
    private String parentFolderName;

    public S3File() {}

    public S3File(String name, String parentFolderName) {
        this.name = name;
        this.parentFolderName = parentFolderName;
    }

    public byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

    public String getName() {
        return name;
    }

    public String getParentFolderName() {
        return parentFolderName;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.data);
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentFolderName(String parentFolderName) {
        this.parentFolderName = parentFolderName;
    }
}
