package com.bot;

import com.bot.config.AppConfig;
import com.bot.model.CityAnswerModel;
import com.bot.service.MessageService;
import com.bot.service.WeatherRequestService;
import com.bot.service.WeatherService;
import com.bot.util.NoSuchCityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestClass {
    private static final Logger log = LogManager.getLogger("TestClass");

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private WeatherRequestService weatherRequestService;

    @Value("${jsonPath}")
    private String jsonPath;

    @Test
    public void getCityId() throws NoSuchCityException {
        String kievId = weatherService.getCityId("Kiev");
        Assert.assertEquals(kievId, "703448");
    }

    @Test
    public void testHttp() throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=703448&units=metric&appid=0b9500b929ea466a01d56bf6f86441af");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        log.debug(response.toString());
    }

    @Test
    public void jsonTest() throws JSONException {
        String json = "{\n" +
                "    \"coord\": {\n" +
                "        \"lon\": 30.52,\n" +
                "        \"lat\": 50.43\n" +
                "    },\n" +
                "    \"weather\": [\n" +
                "        {\n" +
                "            \"id\": 800,\n" +
                "            \"main\": \"Clear\",\n" +
                "            \"description\": \"clear sky\",\n" +
                "            \"icon\": \"01d\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"base\": \"stations\",\n" +
                "    \"main\": {\n" +
                "        \"temp\": 27,\n" +
                "        \"pressure\": 1021,\n" +
                "        \"humidity\": 47,\n" +
                "        \"temp_min\": 27,\n" +
                "        \"temp_max\": 27\n" +
                "    },\n" +
                "    \"visibility\": 10000,\n" +
                "    \"wind\": {\n" +
                "        \"speed\": 4,\n" +
                "        \"deg\": 50\n" +
                "    },\n" +
                "    \"clouds\": {\n" +
                "        \"all\": 0\n" +
                "    },\n" +
                "    \"dt\": 1533132000,\n" +
                "    \"sys\": {\n" +
                "        \"type\": 1,\n" +
                "        \"id\": 7358,\n" +
                "        \"message\": 0.0026,\n" +
                "        \"country\": \"UA\",\n" +
                "        \"sunrise\": 1533090374,\n" +
                "        \"sunset\": 1533145275\n" +
                "    },\n" +
                "    \"id\": 703448,\n" +
                "    \"name\": \"Kiev\",\n" +
                "    \"cod\": 200\n" +
                "}";
        JSONObject object = new JSONObject(json).getJSONArray("weather").getJSONObject(0);
        log.debug(object.get("description"));
    }

    @Test
    public void getWeather() throws IOException {
        CityAnswerModel weather = weatherRequestService.getWeather("703448");

        log.debug(weather);
    }

}
