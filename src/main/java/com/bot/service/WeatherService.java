package com.bot.service;

import com.bot.util.NoSuchCityException;
import org.apache.spark.api.java.JavaSparkContext;

public interface WeatherService {

    Long getCityId(String city, JavaSparkContext sc) throws NoSuchCityException;

}
