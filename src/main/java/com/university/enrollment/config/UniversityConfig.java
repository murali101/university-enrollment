package com.university.enrollment.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * property configurations for university application
 * @author murali
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "university")
public class UniversityConfig {

    private List<String> userTypes;

}
