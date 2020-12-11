package com.netcracker.taskmanager.config;

import com.netcracker.taskmanager.converters.ZonedDateTimeReadConverter;
import com.netcracker.taskmanager.converters.ZonedDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;


public class ConverterConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                Arrays.asList(
                        new ZonedDateTimeReadConverter(),
                        new ZonedDateTimeWriteConverter()));
    }
}
