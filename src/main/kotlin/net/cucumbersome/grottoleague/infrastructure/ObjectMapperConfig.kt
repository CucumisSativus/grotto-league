package net.cucumbersome.grottoleague.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import java.text.DateFormat
import java.text.SimpleDateFormat

class ObjectMapperConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
            .registerKotlinModule()
        mapper.dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return mapper
    }
}