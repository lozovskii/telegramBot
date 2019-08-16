package com.bot.service.Impl;

import com.bot.repository.DBRepository;
import com.bot.service.DBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.objects.Contact;

@Profile("prod")
@Slf4j
@Service
public class DBServicePostgresImpl implements DBService {

    @Autowired
    private DBRepository dbRepository;

    @Override
    public void addUserInfo(Contact contact, Long chatId) {
//        Assert.notNull(contact.toString(),"The contact must not be null");
        Assert.notNull(chatId,"The chatId must not be null");
        dbRepository.addUserInfo(contact, chatId);
    }

    @Override
    public String searchQuickAnswer(String phrase) {
        String quickAnswer = dbRepository.searchQuickAnswer(phrase);
        log.info("Quick answer is: " + quickAnswer);
        return quickAnswer;
    }

    @Override
    public String searchEmoji(String weatherMood) {
        String emoji = dbRepository.searchEmoji(weatherMood);
        return emoji == null ? "" : emoji;
    }

}
