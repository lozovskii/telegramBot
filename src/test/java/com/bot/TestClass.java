package com.bot;

import com.bot.config.AppConfig;
import com.bot.service.MessageService;
import com.bot.service.WeatherService;
import com.bot.util.NoSuchCityException;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestClass {

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MessageService messageService;

    @Value("${jsonPath}")
    private String jsonPath;

    @Test
    public void getCityId() throws NoSuchCityException {
        Long kievId = weatherService.getCityId("Kiev");
        Assert.assertEquals(kievId, Long.valueOf("703448"));
    }

}
