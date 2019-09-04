package com.bot.util.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MongoDataBase {

    @Value("${dataBase}")
    private String DATA_BASE;

    @Bean
    public MongoDatabase getDataBase() {//db
        MongoClient mongoClient = new MongoClient();
        return mongoClient.getDatabase(DATA_BASE);
    }

}
