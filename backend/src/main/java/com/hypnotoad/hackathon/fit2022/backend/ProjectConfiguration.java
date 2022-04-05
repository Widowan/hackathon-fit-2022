package com.hypnotoad.hackathon.fit2022.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:datasourceSecrets.properties")
public class ProjectConfiguration {
    // TODO: Redefine spring default error bean
}
