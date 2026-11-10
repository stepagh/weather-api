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
        Map<String, StationInfo> stations
) {

    public record DayDto(
            LocalDate datetime,
            double tempmax,
            double tempmin,
            double temp,
            double humidity,
            double precip,
            double windspeed,
            double pressure,
            double cloudcover,
            LocalTime sunrise,
            LocalTime sunset,
            String conditions,
            String icon,
            List<String> stations,
            List<HourDto> hours
    ) {}

    public record HourDto(
            LocalTime datetime,
            double temp,
            double humidity,
            Double dew,
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