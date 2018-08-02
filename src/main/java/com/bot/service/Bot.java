package com.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
@PropertySource("classpath:application-prod.properties")
public class Bot extends TelegramLongPollingBot {

    private MessageService messageService;

    @Value("${bot.name}")
    private String botName = System.getenv("BOT_NAME");

    @Value("${bot.token}")
    private String botToken = System.getenv("BOT_TOKEN");

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String text = msg.getText();
        String answer = null;
        try {
            answer = messageService.getAnswer(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMsg(msg, answer);
    }

    public String getBotUsername() {
        return botName;
    }

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
}
