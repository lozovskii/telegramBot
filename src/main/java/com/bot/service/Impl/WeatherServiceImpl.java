package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.repository.DBRepository;
import com.bot.service.WeatherService;
import com.bot.service.WebService;
import com.bot.util.exception.NoSuchCityException;
import com.vdurmont.emoji.EmojiParser;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final Integer CITY_ID_INDEX = 2;
    private final Integer GETTING_FULL_SUBJSON = 0;

    @Autowired
    private DBRepository dbRepository;

    @Autowired
    private WebService webService;
    @Autowired
    private JavaSparkContext sc;

    @Value("${weatherToken}")
    private String weatherToken;
    @Value("${jsonPath}")
    private String jsonPath;

    @Override
    public String getCityId(String city) throws NoSuchCityException {
        SQLContext sqlContext = SQLContext.getOrCreate(sc.sc()).newSession();
        String correctCityNameFormat = makeCorrectCityNameFormat(city);
        Column columnName = new Column("name");
        Dataset<Row> json = sqlContext.read()
                .option("multiline", "true")
                .json(jsonPath)
                .filter(columnName.equalTo(correctCityNameFormat));
        Object rowEntity;
        try {
            rowEntity = json.first();
        } catch (NoSuchElementException e) {
            throw new NoSuchCityException("This city name is not correct or city with this name does not exist!", e);
        }
        return (((Row) rowEntity).get(CITY_ID_INDEX).toString());
    }

    @Override
    public CityAnswerModel getWeather(String cityId) throws MalformedURLException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + cityId +
                "&units=metric&appid=" + weatherToken);
        return parseResponse(url);
    }

    @Override
    public CityAnswerModel getWeatherByCoord(Message msg) throws MalformedURLException {
        Location location = msg.getLocation();
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude().toString() +
                "&lon=" + location.getLongitude().toString() + "&units=metric&appid=" + weatherToken);
        return parseResponse(url);
    }

    private CityAnswerModel parseResponse(URL url) {
        String response = webService.getResponse(url);
        JSONObject jsonResponse = new JSONObject(response);
        CityAnswerModel cityAnswerModel = new CityAnswerModel
                .CityAnswerModelBuilder(jsonResponse.get("name").toString())
                .description(addEmoji(jsonResponse.getJSONArray("weather")
                        .getJSONObject(GETTING_FULL_SUBJSON)
                        .get("description").toString()))
                .temp(jsonResponse.getJSONObject("main").get("temp").toString())
                .pressure(jsonResponse.getJSONObject("main").get("pressure").toString())
                .humidity(jsonResponse.getJSONObject("main").get("humidity").toString())
                .country(jsonResponse.getJSONObject("sys").get("country").toString())
                .windSpeed(jsonResponse.getJSONObject("wind").get("speed").toString())
                .build();
        if (jsonResponse.has("visibility") && !jsonResponse.isNull("visibility"))
            cityAnswerModel.setVisibility(jsonResponse.get("visibility").toString());
        return cityAnswerModel;
    }

    private String addEmoji(String description){
        String emoji = dbRepository.searchEmoji(description);
        return EmojiParser.parseToUnicode(description + emoji);
    }

    private String makeCorrectCityNameFormat(String city) {
        return Arrays.stream(city.split(" "))
                .map(i -> i.substring(0, 1).toUpperCase() + i.substring(1))
                .collect(Collectors.joining(" "));
    }

    private String parseDateFromUnixFormat(String unixDate) {
        Date date = new Date(Long.valueOf(unixDate) * 1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return jdf.format(date);
    }

}
