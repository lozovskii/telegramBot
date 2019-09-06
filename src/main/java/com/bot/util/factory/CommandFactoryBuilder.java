package com.bot.util.factory;

import java.util.function.Supplier;

public interface CommandFactoryBuilder {

    void addCommand(String commandName, Supplier<String> supplier);

}
