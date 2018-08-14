package com.bot.repository.Impl;

import com.bot.repository.DBRepository;
import com.bot.util.MongoDataBase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Contact;

@Profile("test")
@Repository
public class DBRepositoryMongoImpl implements DBRepository {
    @Override
    public void addToDB(Contact contact, Long chatId) {
        MongoDatabase db = new MongoDataBase().getDataBase();
        MongoCollection<Document> collection = db.getCollection("user_info");
        Document user = new Document("userId", contact.getUserID())
                .append("chatId", chatId)
                .append("phoneNumber", contact.getPhoneNumber())
                .append("firstName", contact.getFirstName())
                .append("lastName", contact.getLastName())
                .append("vCard", contact.getVCard());
        collection.insertOne(user);
    }

    @Override
    public String searchAnswer(String phrase) {
        return null;
    }

    @Override
    public String searchEmoji(String weatherMood) {
        return null;
    }
}
