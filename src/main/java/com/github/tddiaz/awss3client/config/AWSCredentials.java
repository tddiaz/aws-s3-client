package com.github.tddiaz.awss3client.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AWSCredentials {
    private final String accessKey;
    private final String secretAccessKey;
}
