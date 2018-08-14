package com.bot.repository.Impl;

import com.bot.repository.DBRepository;
import com.bot.util.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Profile("prod")
@Repository
public class DBRepositoryPostgresImpl implements DBRepository {

    @Autowired
    private NamedParameterJdbcOperations jdbcTemplate;
    @Autowired
    private QueryService queryService;

    @Override
    public void addToDB(Contact contact, Long chatId) {

    }

    @Override
    public String searchQuickAnswer(String phrase) {
        String quickAnswerQuery = queryService.getQuery("getQuickAnswer");
        SqlParameterSource source = new MapSqlParameterSource("phrase", phrase);
        return jdbcTemplate.queryForObject(quickAnswerQuery, source, (resultSet, i) -> resultSet.getString("answer"));
    }

    @Override
    public String searchEmoji(String weatherMood) {
        String emojiQuery = queryService.getQuery("getEmoji");
        SqlParameterSource source = new MapSqlParameterSource("description", weatherMood);
        return jdbcTemplate.queryForObject(emojiQuery, source, (resultSet, i) -> resultSet.getString("emoji"));
    }

}
