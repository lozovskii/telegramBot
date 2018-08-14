package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.repository.DBRepository;
import com.bot.service.CurrencyService;
import com.bot.service.MessageService;
import com.bot.service.WeatherService;
import com.bot.util.exception.NoSuchCityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private DBRepository dbRepository;

    @Override
    public String getAnswer(Message msg) throws IOException {
        String phrase = msg.getText().toLowerCase();
        if (msg.getLocation() != null) {
            return weatherService.getWeatherByCoord(msg).toString();
        }
        String quickAnswer = dbRepository.searchQuickAnswer(phrase);
        switch (quickAnswer) {
            case "/start":
                return "Hello, world! This is simple bot!";
            case "ccurr":
                return parseCryptoCurrency(currencyService.getTopCryptoCurrency().toString());
            case "curr":
                return parseCryptoCurrency(currencyService.getCurrencyInfo().toString());
            default:
                try {
                    String cityId = weatherService.getCityId(phrase);
                    CityAnswerModel weather = weatherService.getWeather(cityId);
                    return parseWeather(weather.toString());
                } catch (NoSuchCityException e) {
                    return "Sorry, but i don't understand...";
                }
        }
    }

    private String parseWeather(String weather) {
        return Arrays.stream(weather.split("\\n"))
                .filter(x -> !x.contains("Visibility = 'null'"))
                .collect(Collectors.joining("\n"));
    }

    private String parseCryptoCurrency(String cryptoCurrency) {
        String substring = cryptoCurrency.substring(1, cryptoCurrency.length() - 1);
        return String.join("\n", substring.split(", "));
    }

}
