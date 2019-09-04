package com.bot.repository.impl;

import com.bot.repository.DBRepository;
import com.bot.util.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;

@Profile("prod")
@Repository
public class DBRepositoryPostgresImpl implements DBRepository {

    @Autowired
    private NamedParameterJdbcOperations jdbcTemplate;
    @Autowired
    private PropertyService propertyService;

    @Override
    public void addUserInfo(Contact contact, Long chatId) {
        String addUserInfo = propertyService.getProperty("saveUserInfo");
        SqlParameterSource source = new MapSqlParameterSource("user_id", contact.getUserID())
                .addValue("chat_id", chatId)
                .addValue("phone_number", contact.getPhoneNumber())
                .addValue("first_name", contact.getFirstName())
                .addValue("last_name", contact.getLastName())
                .addValue("v_card", contact.getVCard());
        jdbcTemplate.query(addUserInfo, source, (resultSet, i) -> null);
    }

    @Override
    public String searchQuickAnswer(String phrase) {
        String quickAnswerQuery = propertyService.getProperty("getQuickAnswer");
        SqlParameterSource source = new MapSqlParameterSource("phrase", phrase);
        List<String> answer = jdbcTemplate.query(quickAnswerQuery, source, (resultSet, i) -> resultSet.getString("answer"));
        return answer.isEmpty() ? null : answer.get(0);
    }

    @Override
    public String searchEmoji(String weatherMood) {
        String emojiQuery = propertyService.getProperty("getEmoji");
        SqlParameterSource source = new MapSqlParameterSource("description", weatherMood);
        List<String> emoji = jdbcTemplate.query(emojiQuery, source, (resultSet, i) -> resultSet.getString("emoji"));
        return emoji.isEmpty() ? null : emoji.get(0);
    }

}
