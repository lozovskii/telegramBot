package com.bot.model;

import lombok.Data;

@Data
public class CityAnswerModel {

    private String name;
    private String description;
    private String temp;
    private String pressure;
    private String humidity;
    private String visibility;
    private String windSpeed;

    @Override
    public String toString() {
        return "" +
                name + ": " +
                description + "\n" +
                "temperature = '" + temp + '\'' + "\n" +
                "pressure = '" + pressure + '\'' + "\n" +
                "humidity = '" + humidity + '\'' + "\n" +
                "visibility = '" + visibility + '\'' + "\n" +
                "windSpeed = '" + windSpeed + '\'';
    }
}
