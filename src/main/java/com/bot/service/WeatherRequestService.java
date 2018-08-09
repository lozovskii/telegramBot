package com.bot.service;

import com.bot.model.CityAnswerModel;

import java.io.IOException;
import java.net.MalformedURLException;

public interface WeatherRequestService {

    CityAnswerModel getWeather(String cityId) throws MalformedURLException;

}
