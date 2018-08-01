package com.bot.service.Impl;

import com.bot.service.MessageService;
import com.bot.service.WeatherService;
import com.bot.util.NoSuchCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private WeatherService weatherService;

    @Override
    public String getAnswer(String phrase) {
        switch (phrase.toLowerCase()) {
            case "/start":
                return "Hello, world! This is simple bot!";
            case "yo":
            case "hello":
            case "hi":
                return "Bonjour!";
            case "what is your name?":
                return "My name test503503Bot";
            default:
                try{
                    Long cityId = weatherService.getCityId(phrase);
                    return cityId.toString();
                } catch (NoSuchCityException e) {
                    return "Sorry, but i don't understand...";
                }
        }
    }
}
