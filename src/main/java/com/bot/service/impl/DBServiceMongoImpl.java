package com.bot.service.impl;

import com.bot.repository.DBRepository;
import com.bot.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;

@Profile("test")
@Service
public class DBServiceMongoImpl implements DBService {

    @Autowired
    private DBRepository dbRepository;

    @Override
    public void addUserInfo(Contact contact, Long chatId) {
        dbRepository.addUserInfo(contact, chatId);
    }

    @Override
    public String searchQuickAnswer(String phrase) {
        return dbRepository.searchQuickAnswer(phrase);
    }

    @Override
    public String searchEmoji(String weatherMood) {
        return dbRepository.searchEmoji(weatherMood);
    }

}
