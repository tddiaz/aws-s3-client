package com.github.tddiaz.awsS3Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.tddiaz.awsS3Service.config.AWSCredentials;
import com.github.tddiaz.awsS3Service.config.ProxySettings;

import java.util.Objects;

public class AWSS3ClientFactory {

    private AWSS3ClientFactory() {}

    public static AWSS3ClientFactory create() {
        return AWSS3ClientFactoryInstance.INSTANCE;
    }

    public AmazonS3 createWithCredentials(AWSCredentials awsCredentials) {
        return createWithCredentials(awsCredentials, null);
    }

    public AmazonS3 createWithCredentials(AWSCredentials awsCredentials, ProxySettings proxySettings) {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsCredentials.getAccessKey(), awsCredentials.getSecretAccessKey());
        return builds3Client(new AWSStaticCredentialsProvider(basicAWSCredentials), proxySettings);
    }

    public AmazonS3 createWithIamRoleEnabled() {
        return createWithIamRoleEnabled(null);
    }

    public AmazonS3 createWithIamRoleEnabled(ProxySettings proxySettings) {
        return builds3Client(new DefaultAWSCredentialsProviderChain(), proxySettings);
    }

    private AmazonS3 builds3Client(AWSCredentialsProvider awsCredentialsProvider, ProxySettings proxySettings) {
        final AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard();
        s3ClientBuilder.withCredentials(awsCredentialsProvider);

        if (Objects.nonNull(proxySettings)) {
            configureProxy(s3ClientBuilder, proxySettings);
        }

        return s3ClientBuilder.build();
    }

    private void configureProxy(AmazonS3ClientBuilder s3ClientBuilder, ProxySettings proxySettings) {
        final ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProxyHost(proxySettings.getHost());
        clientConfiguration.setProxyPort(proxySettings.getPort());

        s3ClientBuilder.withClientConfiguration(clientConfiguration);
    }

    private static class AWSS3ClientFactoryInstance {
        private static final AWSS3ClientFactory INSTANCE = new AWSS3ClientFactory();
    }
}
