package com.bot.service;

import org.telegram.telegrambots.meta.api.objects.Contact;

public interface DBService {

    void addToDB(Contact contact, Long chatID);

    String searchAnswer(String phrase);

}
