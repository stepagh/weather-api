package org.stepagh.weatherapi.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public record WeatherApiResponse(
        int queryCost,
        double latitude,
        double longitude,
        String resolvedAddress,
        String address,
        String timezone,
        double tzoffset,
        List<DayDto> days,
        Map<String, StationInfo> stations // Словарь станций, где ключ — код (например, "KIAD")
) {

    public record DayDto(
            LocalDate datetime, // LocalDate автоматически распарсит строку "2025-06-01"
            double tempmax,
            double tempmin,
            double temp,
            double humidity,
            double precip,
            double windspeed,
            double pressure,
            double cloudcover,
            LocalTime sunrise,  // LocalTime автоматически распарсит "05:45:46"
            LocalTime sunset,   // LocalTime автоматически распарсит "20:29:24"
            String conditions,
            String icon,
            List<String> stations, // Ссылки на коды станций, которые работали в этот день
            List<HourDto> hours    // Почасовой прогноз (может отсутствовать в некоторых запросах)
    ) {}

    public record HourDto(
            LocalTime datetime, // Время часа, например "00:00:00"
            double temp,
            double humidity,
            Double dew,         // Используем обертку Double на случай, если поле придет null
            double windspeed,
            double pressure,
            double cloudcover,
            String conditions,
            String icon
    ) {}

    public record StationInfo(
            String name,
            double latitude,
            double longitude,
            double distance
    ) {}
}