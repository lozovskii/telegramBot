package com.bot.util.exception;

public class NoConnection extends RuntimeException{

    public NoConnection(String message, Throwable e) {
        super(message, e);
    }

}
