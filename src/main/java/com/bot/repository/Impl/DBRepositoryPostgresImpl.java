package com.bot.repository.Impl;

import com.bot.repository.DBRepository;
import com.bot.util.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("prod")
@Repository
public class DBRepositoryPostgresImpl implements DBRepository {

    @Autowired
    private NamedParameterJdbcOperations jdbcTemplate;
    private QueryService queryService;

    @Override
    public void addToDB(Contact contact, Long chatId) {

    }

    @Override
    public String searchAnswer(String phrase) {
       return null;
    }

    @Override
    public String searchEmoji(String weatherMood) {
        String emojiQuery = queryService.getQuery("getEmoji");
        SqlParameterSource source = new MapSqlParameterSource("phrase", weatherMood);
        List emoji = jdbcTemplate.query(emojiQuery, source, new EmojiMapper());
        return emoji.get(0).toString();
    }

    private class EmojiMapper implements RowMapper{
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString("emoji");
        }
    }
}
