package com.infe.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({ProfileLocal.class, ProfileDevelop.class, ProfileProduction.class})
@Configuration
public class ProfileConfig {
}