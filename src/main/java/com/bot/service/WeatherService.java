package com.bot.service;

import com.bot.model.CityAnswerModel;
import com.bot.util.exception.NoSuchCityException;

import java.net.MalformedURLException;

public interface WeatherService {

    String getCityId(String city) throws NoSuchCityException;

    CityAnswerModel getWeather(String cityId) throws MalformedURLException;

    CityAnswerModel getWeatherByCoord(String phrase) throws MalformedURLException;

}
