package com.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
public class Bot extends TelegramLongPollingBot {

    private MessageService messageService;

    @Value("${botName}")
    private String botName;

    @Value("${botToken}")
    private String botToken;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String text = msg.getText();
        String answer = null;
        // its better to extract try/catch clause in a separate method
        // like String answer = getAnswer(text);
        try {
            answer = messageService.getAnswer(text);
        } catch (IOException e) {
            // design proper exception handling
            // example: throw new MyCustomRuntimeException("Failed to get answer for text " + text, e);
            e.printStackTrace();
        }
        sendMsg(msg, answer);
    }

    // what is the purpose of this method | consider removing
    public String getBotUsername() {
        return botName;
    }

    // what is the purpose of this method | consider removing
    public String getBotToken() {
        return botToken;
    }

    private void sendMsg(Message msg, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(msg.getChatId());
        message.setText(text);
        // same as for onUpdateReceived method
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
