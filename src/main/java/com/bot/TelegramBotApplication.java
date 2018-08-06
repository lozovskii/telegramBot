package com.bot;

import com.bot.service.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication
public class TelegramBotApplication {

    @Autowired
    private Bot bot;

    @Bean
    CommandLineRunner runBot(){
        return (args) -> {
            TelegramBotsApi botApi = new TelegramBotsApi();
            botApi.registerBot(bot);
        };
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}