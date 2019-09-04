package com.bot.util.impl;

import com.bot.util.GuiServiceUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuiServiceUtilImpl implements GuiServiceUtil {

    @Override
    public void drawKeyBoard(SendMessage message, List<String> keyBoardName) {
//        createInlineKeyBoard(message);
        initKeyBoard(message, keyBoardName);
    }

    private void initKeyBoard(SendMessage message, List<String> buttons) {
        ReplyKeyboardMarkup keyBoard = createReplyKeyBoard(message);
        KeyboardRow keyBoardRow = createAndFillKeyBoardRow(buttons);
        List<KeyboardRow> listRows = new ArrayList<>();
        listRows.add(keyBoardRow);
        keyBoard.setKeyboard(listRows);
    }

    private void createInlineKeyBoard(SendMessage message) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton()
                .setText("Update message text")
                .setCallbackData("update_msg_text"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return;
    }

    private ReplyKeyboardMarkup createReplyKeyBoard(SendMessage message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    private KeyboardRow createAndFillKeyBoardRow(List<String> buttonsName) {
        KeyboardRow keyboardRow = new KeyboardRow();
        for (String button : buttonsName)
            keyboardRow.add(new KeyboardButton(button));
        return keyboardRow;
    }

}
