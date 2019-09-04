package com.bot.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeatherModel {

    private final String name;
    private String description;
    private String temp;
    private String pressure;
    private String humidity;
    private String visibility;
    private String country;
    private String windSpeed;
    private LocalDateTime date;

    public static class WeatherModelBuilder {

        private String name;
        private String description;
        private String temp;
        private String pressure;
        private String humidity;
        private String visibility;
        private String country;
        private String windSpeed;
        private LocalDateTime date;

        public WeatherModelBuilder(String name){
            this.name = name;
        }

        public WeatherModelBuilder description(String description){
            this.description = description;
            return this;
        }

        public WeatherModelBuilder temp(String temp){
            this.temp = temp;
            return this;
        }

        public WeatherModelBuilder pressure(String pressure){
            this.pressure = pressure;
            return this;
        }

        public WeatherModelBuilder humidity(String humidity){
            this.humidity = humidity;
            return this;
        }

        public WeatherModelBuilder visibility(String visibility){
            this.visibility = visibility;
            return this;
        }

        public WeatherModelBuilder country(String country){
            this.country = country;
            return this;
        }

        public WeatherModelBuilder windSpeed(String windSpeed){
            this.windSpeed = windSpeed;
            return this;
        }

        public WeatherModelBuilder date(LocalDateTime date){
            this.date = date;
            return this;
        }

        public WeatherModel build(){
            return new WeatherModel(this);
        }

    }

    private WeatherModel(WeatherModelBuilder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.temp = builder.temp;
        this.pressure = builder.pressure;
        this.humidity = builder.humidity;
        this.visibility = builder.visibility;
        this.country = builder.country;
        this.windSpeed = builder.windSpeed;
        this.date = builder.date;
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
                "Wind Speed = " + windSpeed + "\n" +
                "Date = " + date + ";";
    }
}
