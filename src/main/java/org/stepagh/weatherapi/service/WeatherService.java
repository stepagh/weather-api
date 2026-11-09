package org.stepagh.weatherapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.stepagh.weatherapi.dto.WeatherApiResponse;
import org.stepagh.weatherapi.exceptions.BadRequestException;
import org.stepagh.weatherapi.exceptions.ExternalClientException;
import org.stepagh.weatherapi.exceptions.ExternalServiceException;
import org.stepagh.weatherapi.exceptions.NotFoundException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final RestClient weatherClient;


    public WeatherApiResponse fetchWeatherFromApi(String city, LocalDate date) {
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
