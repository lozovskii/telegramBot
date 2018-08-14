package com.bot.service.Impl;

import com.bot.repository.DBRepository;
import com.bot.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;

@Profile("prod")
@Service
public class DBServicePostgresImpl implements DBService {

    @Autowired
    private DBRepository dbRepository;

    @Override
    public void addToDB(Contact contact, Long chatID) {
        dbRepository.addToDB(contact, chatID);
    }

    @Override
    public String searchAnswer(String phrase) {
        return dbRepository.searchQuickAnswer(phrase);
    }


}