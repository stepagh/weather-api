package org.stepagh.weatherapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stepagh.weatherapi.dto.WeatherApiResponse;
import org.stepagh.weatherapi.service.WeatherService;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    //вспомнить про основы в аннотациях
    @GetMapping("/weather/{city}/{date}")
    public WeatherApiResponse getWeather(@PathVariable String city, @PathVariable LocalDate date) {
        return weatherService.fetchWeatherFromApi(city, date);
    }
}
