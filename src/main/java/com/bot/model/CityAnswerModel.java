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

}
