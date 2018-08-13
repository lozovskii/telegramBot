package com.bot.service.Impl;

import com.bot.service.DBService;
import com.bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DBService dbService;

    @Profile("test")
    @Override
    public void addUserInfo(Message msg) {
        Long chatId = msg.getChatId();
        Contact contact = msg.getContact();
        dbService.addToDB(contact, chatId);
    }

}
