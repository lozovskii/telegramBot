package com.bot.util.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface CommandFactory {
    Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);

    String call(String commandName);

    static CommandFactory factory(Consumer<CommandFactoryBuilder> consumer) {
        Map<String, Supplier<String>> commandMap = new HashMap<>();
        consumer.accept(commandMap::put);
        return name -> {
            if (commandMap.get(name) != null) {
                return commandMap.get(name).get();
            } else {
                LOGGER.debug("Can't find method to call");
                return null;
            }
        };
    }

}
