package com.github.tddiaz.awss3client.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProxySettings {
    private final String host;
    private final int port;
}
