package org.exam;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.exam")
@PropertySource("classpath:aplication.properties")
public class AppConfig {
}
