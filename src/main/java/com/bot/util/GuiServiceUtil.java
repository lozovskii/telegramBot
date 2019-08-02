package com.bot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface GuiServiceUtil {

    void draw(SendMessage message, List<String> keyBoardName);

}
