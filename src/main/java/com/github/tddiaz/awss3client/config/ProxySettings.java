package com.github.tddiaz.awss3client.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProxySettings {
    private String host;
    private int port;
}
