package com.bot.repository.Impl;

import com.bot.repository.DBRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Contact;

@Profile("prod")
@Repository
public class DBRepositoryPostgresImpl implements DBRepository {
    @Override
    public void addToDB(Contact contact, Long chatId) {

    }

    @Override
    public String searchAnswer(String phrase) {
        return null;
    }
}
