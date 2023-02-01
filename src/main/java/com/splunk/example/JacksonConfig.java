package com.splunk.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class JacksonConfig {

   /* @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setObjectMapper(objectMapper);
        return objectMapper;
    }*/
}
