package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.service.CurrencyService;
import com.bot.service.MessageService;
import com.bot.service.WeatherRequestService;
import com.bot.service.WeatherService;
import com.bot.util.exception.NoSuchCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherRequestService weatherRequestService;
    @Autowired
    private CurrencyService currencyService;

    @Override
    public String getAnswer(String phrase) throws IOException {
        switch (phrase.toLowerCase()) {
            case "/start":
                return "Hello, world! This is simple bot!";
            case "yo":
            case "hello":
            case "hi":
                return "Bonjour!";
            case "what is your name?":
                return "My name test503503Bot";
            case "curr":
                return currencyService.getTopCryptoCurrency().toString();
            default:
                return currencyService.getTopCryptoCurrency().toString();
//                try {
//                    String cityId = weatherService.getCityId(phrase);
//                    CityAnswerModel weather = weatherRequestService.getWeather(cityId);
//                    return parseWeather(weather.toString());
//                } catch (NoSuchCityException e) {
//                    return "Sorry, but i don't understand...";
//                }
        }
    }

    private String parseWeather(String weather) {
        return Arrays.stream(weather.split("\\n"))
                .filter(x -> !x.contains("visibility = 'null'"))
                .collect(Collectors.joining("\n"));
    }

}
