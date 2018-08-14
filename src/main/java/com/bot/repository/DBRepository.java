package com.bot.repository;

import org.telegram.telegrambots.meta.api.objects.Contact;

public interface DBRepository {

    void addUserInfo(Contact contact, Long chatId);
    String searchQuickAnswer(String phrase);
    String searchEmoji(String weatherMood);

}
