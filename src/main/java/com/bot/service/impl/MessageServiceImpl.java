package com.bot.service.impl;

import com.bot.model.WeatherModel;
import com.bot.service.CurrencyService;
import com.bot.service.DBService;
import com.bot.service.MessageService;
import com.bot.service.WeatherService;
import com.bot.util.factory.CommandFactory;
import com.bot.util.impl.GuiServiceUtilImpl;
import com.bot.util.PropertyService;
import com.bot.util.exception.NoSuchCityException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private static Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

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
    private CommandFactory commandFactory;

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

    @Override
    public SendMessage prepareForSend(Long chatId, String text) {
        SendMessage message = new SendMessage();
        List<String> buttons = propertyService.getProperties("buttonsCurrency");
        guiServiceUtil.drawKeyBoard(message, buttons);
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

    private String fetchAnswer(String phrase) throws IOException {
        String answer = commandFactory.call(phrase);
        if (answer == null) {
            try {
                String cityId = weatherService.getCityId(phrase);
                WeatherModel weather = weatherService.getCurrentWeather(cityId);
                answer = weatherService.parseWeather(weather.toString());
            } catch (NoSuchCityException e) {
                answer = "Sorry, but i don't understand...";
            }
        }
        return answer;
    }

    @PostConstruct
    private void postConstruct() {
        commandFactory = CommandFactory.factory(map -> {
                    map.addCommand("Currency", () -> {
                        String result = null;
                        try {
                            result = currencyService.parseCryptoCurrency(currencyService.getCurrencyInfo().toString());
                        } catch (MalformedURLException e) {
                            LOGGER.debug("While try to execute Currency command, throw: {}", e);
                        }
                        return result;
                    });
                    map.addCommand("CryptoCurrency", () -> {
                        String result = null;
                        try {
                            result = currencyService.parseCryptoCurrency(currencyService.getTopCryptoCurrency().toString());
                        } catch (MalformedURLException e) {
                            LOGGER.debug("While try to execute CryptoCurrency command, throw: {}", e);
                        }
                        return result;
                    });
                    map.addCommand("/start", () -> "Hello, world! This is simple bot!");
                }
        );
    }

    //todo try to change code above, don't copy paste yourself
    private Supplier<String> process(){

        return null;
    }

}
