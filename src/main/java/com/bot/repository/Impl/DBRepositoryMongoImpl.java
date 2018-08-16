package com.bot.repository.Impl;

import com.bot.repository.DBRepository;
import com.bot.util.MongoDataBase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Contact;

@Profile("test")
@Repository
public class DBRepositoryMongoImpl implements DBRepository {

    private final String WEATHER_MOOD = "weather_mood";
    private final String DIALOG_PHRASE = "dialog_phrase";

    @Override
    public void addUserInfo(Contact contact, Long chatId) {
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
    public String searchQuickAnswer(String phrase) {
        MongoDatabase db = new MongoDataBase().getDataBase();
        MongoCollection<Document> collection = db.getCollection(DIALOG_PHRASE);
        return getValue(phrase, collection);
    }

    @Override
    public String searchEmoji(String weatherMood) {
        MongoDatabase db = new MongoDataBase().getDataBase();
        MongoCollection<Document> collection = db.getCollection(WEATHER_MOOD);
        return getValue(weatherMood, collection);
    }

    private String getValue(String key,  MongoCollection<Document> collectionName){
        for (Document doc : collectionName.find()) {
            JSONObject json = new JSONObject(doc.toJson());
            if(json.has(key)){
                return json.get(key).toString();
            }
        }
        return "";
    }
}
