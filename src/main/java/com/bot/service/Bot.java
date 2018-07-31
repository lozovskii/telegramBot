package com.bot.service;

import com.bot.config.Config;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage(); // Это нам понадобится
        String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, "Hello, world! This is simple bot!");
        }else if(txt.equals("yo") || txt.equals("hello") || txt.equals("hi")){
            sendMsg(msg, "Bonjour!");
        }else if(txt.equals("What is your name?") || txt.equals("what is your name?")){
            sendMsg(msg, "My name test503503Bot");
        }else{
            sendMsg(msg, "Sorry, but i don't understand :( \nHope study it");
        }
    }

    public String getBotUsername() {
        return Config.botName;
    }

    public String getBotToken() {
        return Config.botToken;
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
