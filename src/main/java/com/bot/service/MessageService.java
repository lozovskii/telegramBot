package com.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public interface MessageService {

    String getAnswer(Message mesage) throws IOException;

    SendMessage prepareForSend(Long chatId, String text);

}
