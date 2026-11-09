package org.stepagh.weatherapi.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class WeatherClientConfig {
    @Bean
    public RestClient weatherApiClient(@Value("${weather.api.key}") String apiKey) {
        return RestClient.builder()
                .baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("key", apiKey)
                .build();
    }
}
