package com.jhcorea.apiapptemplate.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("local")
@PropertySource({"classpath:local/application.properties", "classpath:local/application.yml"})
public class LocalProfile {
}
