package com.bot.util;

public class NoAnswerException extends RuntimeException {

    public NoAnswerException(String message, Throwable e) {
        super(message, e);
    }

}
