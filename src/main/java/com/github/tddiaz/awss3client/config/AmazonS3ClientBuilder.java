package com.github.tddiaz.awss3client.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;

/**
 * @author Tristan
 */
public final class AmazonS3ClientBuilder {

    private com.amazonaws.services.s3.AmazonS3ClientBuilder s3ClientBuilder = com.amazonaws.services.s3.AmazonS3ClientBuilder.standard();

    public static AmazonS3ClientBuilder builder() {
        return new AmazonS3ClientBuilder();
    }

    public AmazonS3ClientBuilder awsCredentials(AWSCredentials awsCredentials) {
        var basicAWSCredentials = new BasicAWSCredentials(awsCredentials.getAccessKey(), awsCredentials.getSecretAccessKey());
        s3ClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials));

        return this;
    }

    public AmazonS3ClientBuilder iamRoleEnabled() {
        s3ClientBuilder.withCredentials(new DefaultAWSCredentialsProviderChain());

        return this;
    }

    public AmazonS3ClientBuilder configureProxy(ProxySettings proxySettings) {
        final ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProxyHost(proxySettings.getHost());
        clientConfiguration.setProxyPort(proxySettings.getPort());

        s3ClientBuilder.withClientConfiguration(clientConfiguration);

        return this;
    }

    public AmazonS3 build() {
        return s3ClientBuilder.build();
    }


    private AmazonS3ClientBuilder() {
        // private constructor
    }
}
