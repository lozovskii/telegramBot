package com.bot.util.exception;

public class NoSuchCityException extends RuntimeException {

    public NoSuchCityException(String message, Throwable e) {
        super(message, e);
    }

}
