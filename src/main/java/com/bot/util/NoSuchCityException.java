package com.bot.util;

// why is this a checked exception | consider extending Runtime
public class NoSuchCityException extends Exception {

    public NoSuchCityException(String message) {
        super(message);
    }

}
