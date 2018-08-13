package com.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityAnswerModel {

    private final String name;
    private String description;
    private String temp;
    private String pressure;
    private String humidity;
    private String visibility;
    private String country;
    private String windSpeed;

    public static class CityAnswerModelBuilder{

        private final String name;
        private String description;
        private String temp;
        private String pressure;
        private String humidity;
        private String visibility;
        private String country;
        private String windSpeed;

        public CityAnswerModelBuilder(String name){
            this.name = name;
        }

        public CityAnswerModelBuilder description(String description){
            this.description = description;
            return this;
        }

        public CityAnswerModelBuilder temp(String temp){
            this.temp = temp;
            return this;
        }

        public CityAnswerModelBuilder pressure(String pressure){
            this.pressure = pressure;
            return this;
        }

        public CityAnswerModelBuilder humidity(String humidity){
            this.humidity = humidity;
            return this;
        }

        public CityAnswerModelBuilder visibility(String visibility){
            this.visibility = visibility;
            return this;
        }

        public CityAnswerModelBuilder country(String country){
            this.country = country;
            return this;
        }

        public CityAnswerModelBuilder windSpeed(String windSpeed){
            this.windSpeed = windSpeed;
            return this;
        }

        public CityAnswerModel build(){
            return new CityAnswerModel(this);
        }

    }

    private CityAnswerModel(CityAnswerModelBuilder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.temp = builder.temp;
        this.pressure = builder.pressure;
        this.humidity = builder.humidity;
        this.visibility = builder.visibility;
        this.country = builder.country;
        this.windSpeed = builder.windSpeed;
    }

    @Override
    public String toString() {
        return "" +
                name + " (" + country + "): " +
                description + "\n" +
                "Temperature = " + temp + "\n" +
                "Pressure = " + pressure + "\n" +
                "Humidity = " + humidity + "\n" +
                "Visibility = " + visibility + "\n" +
                "Wind Speed = " + windSpeed + ';';
    }
}
