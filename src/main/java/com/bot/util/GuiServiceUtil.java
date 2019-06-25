package com.bot.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class GuiServiceUtil {

    private final String[] buttons = new String[]{"Crypto currency", "Currency"};

    public void drow(SendMessage message) {
        initKeyBoard(message, buttons);
    }

    private void initKeyBoard(SendMessage message, String... buttons){
        ReplyKeyboardMarkup keyBoard = createKeyBoard(message);
        KeyboardRow keyBoardRow1 = createAndFillKeyBoardRow(buttons);
        List<KeyboardRow> listRows = new ArrayList<>();
        listRows.add(keyBoardRow1);
        keyBoard.setKeyboard(listRows);
    }

    private ReplyKeyboardMarkup createKeyBoard(SendMessage message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    private KeyboardRow createAndFillKeyBoardRow(String... buttonsName) {
        KeyboardRow keyboardRow = new KeyboardRow();
        for (String button : buttonsName)
            keyboardRow.add(new KeyboardButton(button));
        return keyboardRow;
    }

}
