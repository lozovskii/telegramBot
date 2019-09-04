package com.bot.service.impl;

import com.bot.model.CityAnswerModel;
import com.bot.service.CurrencyService;
import com.bot.service.DBService;
import com.bot.service.MessageService;
import com.bot.service.WeatherService;
import com.bot.util.impl.GuiServiceUtilImpl;
import com.bot.util.PropertyService;
import com.bot.util.exception.NoSuchCityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private DBService dbService;
    @Autowired
    private GuiServiceUtilImpl guiServiceUtil;
    @Autowired
    private PropertyService propertyService;

    @Override
    public String getAnswer(Message msg) throws IOException {
        String phrase = "";
        Assert.notNull(msg, "msg is null");

        if (msg.getText() != null)
            phrase = msg.getText().toLowerCase();

        if (msg.getLocation() != null)
            return weatherService.getWeatherByCoord(msg).toString();

        String quickAnswer = dbService.searchQuickAnswer(phrase);
        if (quickAnswer != null)
            return quickAnswer;
        else
            return fetchAnswer(phrase);

    }

    private String fetchAnswer(String phrase) throws IOException {
        switch (phrase) {
            case "/start":
                return "Hello, world! This is simple bot!";
            case "crypto currency":
                return currencyService.parseCryptoCurrency(currencyService.getTopCryptoCurrency().toString());
            case "currency":
                return currencyService.parseCryptoCurrency(currencyService.getCurrencyInfo().toString());
            default:
                try {
                    String cityId = weatherService.getCityId(phrase);
                    CityAnswerModel weather = weatherService.getCurrentWeather(cityId);
                    return weatherService.parseWeather(weather.toString());
                } catch (NoSuchCityException e) {
                    return "Sorry, but i don't understand...";
                }
        }
    }

    @Override
    public SendMessage prepareForSend(Long chatId, String text) {
        SendMessage message = new SendMessage();
        List<String> buttons = propertyService.getProperties("buttonsCurrency");
        guiServiceUtil.drawKeyBoard(message, buttons);
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

}
