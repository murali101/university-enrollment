package com.university.enrollment.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Bean configurations for university application
 * @author murali
 */

@Configuration
@EnableSwagger2
public class UniversityBeanConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.university.enrollment"))
                .paths(PathSelectors.any())
                .build().apiInfo(metaInfo());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

    private ApiInfo metaInfo() {
        Collection<VendorExtension> vendorExtensions = new ArrayList<>();
        return new ApiInfo(
                "University Student Enrollment API",
                "The users of the system will consist of both school administrators and students.  " +
                        "School administrators will create student identities, and students will be able to enroll themselves into classes before each term.",
                "1.0",
                "Terms of Service",
                new Contact("Murali Krishna Pinjala", null, "murali.27@yahoo.com"),
                "LGPL",
                "https://www.apache.org/licenses/", vendorExtensions);
    }
}
