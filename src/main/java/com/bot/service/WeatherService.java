package com.bot.service;

import com.bot.model.CityAnswerModel;
import com.bot.util.exception.NoSuchCityException;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.MalformedURLException;
import java.util.List;

public interface WeatherService {

    String getCityId(String city) throws NoSuchCityException;

    CityAnswerModel getCurrentWeather(String cityId) throws MalformedURLException;

    List<CityAnswerModel> getWeatherForecast(String cityId) throws MalformedURLException;

    CityAnswerModel getWeatherByCoord(Message msg) throws MalformedURLException;

    String parseWeather(String weather);

}
