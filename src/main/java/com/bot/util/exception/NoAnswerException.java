package com.bot.util.exception;

public class NoAnswerException extends RuntimeException {

    public NoAnswerException(String message, Throwable e) {
        super(message, e);
    }

}
