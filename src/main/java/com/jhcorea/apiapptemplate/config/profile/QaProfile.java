package com.jhcorea.apiapptemplate.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("qa")
@PropertySource({"classpath:qa/application.properties"})
public class QaProfile {
}
