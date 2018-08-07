package com.bot.service;

import com.bot.util.exception.NoSuchCityException;

public interface WeatherService {

    String getCityId(String city) throws NoSuchCityException;

}
