package com.bot;

import com.bot.config.AppConfig;
import com.bot.model.CityAnswerModel;
import com.bot.model.CryptoCurrencyModel;
import com.bot.service.MessageService;
import com.bot.service.WeatherService;
import com.bot.util.exception.NoSuchCityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Value("${jsonPath}")
    private String jsonPath;

    @Ignore
    @Test
    public void getCityId() throws NoSuchCityException {
        String kievId = weatherService.getCityId("Kiev");
        Assert.assertEquals(kievId, "703448");
    }

    @Ignore
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

    @Ignore
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

    @Ignore
    @Test
    public void getWeather() throws IOException {
        CityAnswerModel weather = weatherService.getWeather("703448");

        log.debug(weather);
    }

    @Ignore
    @Test
    public void testParsCryptoCurrency() throws JSONException {
        String response = "{\n" +
                "    \"data\": {\n" +
                "        \"1\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Bitcoin\",\n" +
                "            \"symbol\": \"BTC\",\n" +
                "            \"website_slug\": \"bitcoin\",\n" +
                "            \"rank\": 1,\n" +
                "            \"circulating_supply\": 17199262.0,\n" +
                "            \"total_supply\": 17199262.0,\n" +
                "            \"max_supply\": 21000000.0,\n" +
                "            \"quotes\": {\n" +
                "                \"USD\": {\n" +
                "                    \"price\": 6430.36791515,\n" +
                "                    \"volume_24h\": 4651816716.43908,\n" +
                "                    \"market_cap\": 110597582529.0,\n" +
                "                    \"percent_change_1h\": 2.2,\n" +
                "                    \"percent_change_24h\": -1.26,\n" +
                "                    \"percent_change_7d\": -15.26\n" +
                "                }\n" +
                "            },\n" +
                "            \"last_updated\": 1533822991\n" +
                "        },\n" +
                "        \"2246\": {\n" +
                "            \"id\": 2246,\n" +
                "            \"name\": \"CyberMiles\",\n" +
                "            \"symbol\": \"CMT\",\n" +
                "            \"website_slug\": \"cybermiles\",\n" +
                "            \"rank\": 100,\n" +
                "            \"circulating_supply\": 700298400.0,\n" +
                "            \"total_supply\": 1000000000.0,\n" +
                "            \"max_supply\": null,\n" +
                "            \"quotes\": {\n" +
                "                \"USD\": {\n" +
                "                    \"price\": 0.0919244418,\n" +
                "                    \"volume_24h\": 10698513.4442638,\n" +
                "                    \"market_cap\": 64374540.0,\n" +
                "                    \"percent_change_1h\": 3.1,\n" +
                "                    \"percent_change_24h\": -3.29,\n" +
                "                    \"percent_change_7d\": -21.0\n" +
                "                }\n" +
                "            },\n" +
                "            \"last_updated\": 1533823000\n" +
                "        }" +
                "    },\n" +
                "    \"metadata\": {\n" +
                "        \"timestamp\": 1533822549,\n" +
                "        \"num_cryptocurrencies\": 1784,\n" +
                "        \"error\": null\n" +
                "    }\n" +
                "}";
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject data = jsonResponse.getJSONObject("data");
        Iterator data1 = jsonResponse.getJSONObject("data").keys();
        List<String> keys = new ArrayList<>();
        List<CryptoCurrencyModel> responseCurrencyList = new ArrayList<>();
        while(data1.hasNext()){
            keys.add(data1.next().toString());
        }
        for (String key: keys){
            JSONObject keyElement = jsonResponse.getJSONObject("data").getJSONObject(key);
            JSONObject quotes = keyElement.getJSONObject("quotes");
            Iterator quotesKeys = quotes.keys();
            String currency = null;
            while(quotesKeys.hasNext()){
                currency = quotesKeys.next().toString();
            }
            String price = quotes.getJSONObject(currency).get("price").toString();
            CryptoCurrencyModel cryptoCurrencyModel = new CryptoCurrencyModel.CryptoCurrencyModelBuilder(
                    keyElement.get("name").toString(), price, currency)
                    .id(key)
                    .symbol(keyElement.get("symbol").toString())
                    .rank(keyElement.get("rank").toString())
                    .build();
            responseCurrencyList.add(cryptoCurrencyModel);
        }
        System.out.println(responseCurrencyList);
        Assert.assertTrue(responseCurrencyList.size() > 0);
//        System.out.println(jsonResponse.getJSONObject("data").getJSONObject("1").get("circulating_supply").toString());
    }

}
