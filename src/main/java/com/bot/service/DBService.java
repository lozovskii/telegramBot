package com.bot.service;

import org.telegram.telegrambots.meta.api.objects.Contact;

public interface DBService {

    void addUserInfo(Contact contact, Long chatID);
    String searchQuickAnswer(String phrase);
    String searchEmoji(String weatherMood);

}
