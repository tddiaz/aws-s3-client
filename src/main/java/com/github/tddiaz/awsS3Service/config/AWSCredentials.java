package com.github.tddiaz.awsS3Service.config;

/**
 * Created by tristandiaz on 11/8/17.
 */
public class AWSCredentials {

    private final String accessKey;

    private final String secretAccessKey;

    public AWSCredentials(String accessKey, String secretAccessKey) {
        this.accessKey = accessKey;
        this.secretAccessKey = secretAccessKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

}
