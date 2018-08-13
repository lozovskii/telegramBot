package com.bot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {

    void addUserInfo(Message msg);

}
