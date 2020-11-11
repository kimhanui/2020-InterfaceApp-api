package com.infe.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value="develop")
@PropertySource({"classpath:application-develop.properties"})
public class ProfileDevelop {
}