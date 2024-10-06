package org.example.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${questions.file.path}")
    private String questionsCsvPath;

    @Bean
    public Resource csvResource() {
        //return new ClassPathResource("questions.csv");
        return new ClassPathResource(questionsCsvPath.substring("classpath:".length()));
    }

}
