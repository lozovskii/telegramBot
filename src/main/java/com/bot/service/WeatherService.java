package com.bot.service;

import com.bot.model.WeatherModel;
import com.bot.util.exception.NoSuchCityException;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.MalformedURLException;
import java.util.List;

public interface WeatherService {

    String getCityId(String city) throws NoSuchCityException;

    WeatherModel getCurrentWeather(String cityId) throws MalformedURLException;

    List<WeatherModel> getWeatherForecast(String cityId) throws MalformedURLException;

    WeatherModel getWeatherByCoord(Message msg) throws MalformedURLException;

    String parseWeather(String weather);

}
