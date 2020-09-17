package com.infe.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({H2ServerConfiguration.class, MariaDBServerConfiguration.class})
@Configuration
public class ProfileConfig {

}
    