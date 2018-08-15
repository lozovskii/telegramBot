package com.bot.service;

import com.bot.util.exception.NoAnswerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Slf4j
@Service
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private MessageService messageService;
    @Autowired
    private DBService dbService;

    @Value("${botName}")
    private String botName;

    @Value("${botToken}")
    private String botToken;


    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        log.info("contact", msg.getContact());
        dbService.addUserInfo(msg.getContact(), msg.getChatId());
        String answer = getAnswer(msg);
        sendMsg(msg, answer);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void sendMsg(Message msg, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(msg.getChatId());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getAnswer(Message message){
        try {
            return messageService.getAnswer(message);
        } catch (IOException e) {
            throw new NoAnswerException("Failed to get answer for text: " + message.getText(), e);
        }
    }
}
