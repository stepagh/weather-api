package org.stepagh.weatherapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.stepagh.weatherapi.dto.WeatherApiResponse;
import org.stepagh.weatherapi.exceptions.ExternalClientException;
import org.stepagh.weatherapi.exceptions.ExternalServiceException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final RestClient weatherClient;

    @Cacheable(value = "weatherCache", key = "#city.toLowerCase() + ':' + #date")
    public WeatherApiResponse fetchWeatherFromApi(String city, LocalDate date) {
        log.info("Метод вызван: идет запрос к внешнему API");
        return weatherClient.get()
                .uri("/{city}{date}", city, date)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    String errorBody = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

                    throw new ExternalClientException(
                            String.format("Client error when requesting weather data [%s]: %s", response.getStatusCode(), errorBody),
                            response.getStatusCode()
                    );
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new ExternalServiceException(
                            "Weather service Visual Crossing temporarly unavailable. Статус: " + response.getStatusCode(),
                            response.getStatusCode()
                    );
                })
                .body(WeatherApiResponse.class);
    }
}
