package com.hypnotoad.hackathon.fit2022.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:datasourceSecrets.properties")
public class ProjectConfiguration {
    @Bean
    @Value("${url.url}")
    public String hackathonUrl(String url) {
        return url;
    }

    @Bean
    @Value("${url.staticPath}")
    public String staticPath(String staticPath) {
        return staticPath;
    }

    @Bean
    public String staticUrl(String hackathonUrl, String staticPath) {
        return hackathonUrl + staticPath;
    }
}
