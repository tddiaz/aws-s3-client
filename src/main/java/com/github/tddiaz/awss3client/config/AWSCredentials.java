package com.github.tddiaz.awss3client.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AWSCredentials {
    private final String accessKey;
    private final String secretAccessKey;
}
