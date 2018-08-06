package com.bot.util;

public class NoSuchCityException extends RuntimeException {

    public NoSuchCityException(String message, Throwable e) {
        super(message, e);
    }

}
