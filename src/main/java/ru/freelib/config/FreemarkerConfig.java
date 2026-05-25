package ru.freelib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class FreemarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates/");
        configurer.setDefaultEncoding("UTF-8");

        Properties settings = new Properties();
        settings.setProperty("template_exception_handler", "rethrow");
        settings.setProperty("url_escaping_charset", "UTF-8");
        settings.setProperty("datetime_format", "dd.MM.yyyy HH:mm");
        settings.setProperty("date_format", "dd.MM.yyyy");
        settings.setProperty("time_format", "HH:mm");
        configurer.setFreemarkerSettings(settings);

        return configurer;
    }
}