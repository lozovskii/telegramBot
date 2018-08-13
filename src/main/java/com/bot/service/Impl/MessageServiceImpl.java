package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.service.CurrencyService;
import com.bot.service.MessageService;
import com.bot.service.WeatherService;
import com.bot.util.exception.NoSuchCityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger log = LogManager.getLogger("MessageServiceImpl");

    @Value("${help}")
    private String HELP;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private CurrencyService currencyService;

    @Override
    public String getAnswer(String phrase) throws IOException {
        log.debug("Input message: " + phrase);
        switch (phrase.toLowerCase()) {
            case "/start":
                return "Hello, world! This is simple bot!";
            case "yo":
            case "hello":
            case "hi":
                return "Bonjour!";
            case "what is your name?":
                return "My name test503503Bot";
            case "ccurr":
                return parseCryptoCurrency(currencyService.getTopCryptoCurrency().toString());
            case "curr":
                return parseCryptoCurrency(currencyService.getCurrencyInfo().toString());
            case "help":
                return HELP;
            default:
                if(phrase.startsWith("https://")){
                    weatherService.getWeatherByCoord(phrase);
                }
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
                .filter(x -> !x.contains("visibility = 'null'"))
                .collect(Collectors.joining("\n"));
    }

    private String parseCryptoCurrency(String cryptoCurrency){
        String substring = cryptoCurrency.substring(1, cryptoCurrency.length() - 1);
        return String.join("\n", substring.split(", "));
    }

}
