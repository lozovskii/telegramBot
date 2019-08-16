package com.bot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface GuiServiceUtil {

    void drawKeyBoard(SendMessage message, List<String> keyBoardName);

}
