package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.service.WeatherService;
import com.bot.service.WebService;
import com.bot.util.exception.NoSuchCityException;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:weather.properties")
public class WeatherServiceImpl implements WeatherService {
    private final Integer CITY_ID_INDEX = 2;
    private final Integer GETTING_FULL_SUBJSON = 0;

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
        URL url = buildURL(cityId);
        String response = webService.getResponse(url);
        JSONObject jsonResponse = new JSONObject(response);
        CityAnswerModel cityAnswerModel = new CityAnswerModel
                .CityAnswerModelBuilder(jsonResponse.get("name").toString())
                .description(jsonResponse.getJSONArray("weather")
                        .getJSONObject(GETTING_FULL_SUBJSON)
                        .get("description").toString())
                .temp(jsonResponse.getJSONObject("main").get("temp").toString())
                .pressure(jsonResponse.getJSONObject("main").get("pressure").toString())
                .humidity(jsonResponse.getJSONObject("main").get("humidity").toString())
                .date(parseDate(jsonResponse.get("dt").toString()))
                .country(jsonResponse.getJSONObject("sys").get("country").toString())
                .windSpeed(jsonResponse.getJSONObject("wind").get("speed").toString())
                .build();
        if (jsonResponse.has("visibility") && !jsonResponse.isNull("visibility"))
            cityAnswerModel.setVisibility(jsonResponse.get("visibility").toString());
        return cityAnswerModel;
    }

    private URL buildURL(String cityId) throws MalformedURLException {
        return new URL("http://api.openweathermap.org/data/2.5/weather?id=" + cityId +
                "&units=metric&appid=" + weatherToken);
    }

    private String makeCorrectCityNameFormat(String city){
        return Arrays.stream(city.split(" "))
                .map(i -> i.substring(0, 1).toUpperCase() + i.substring(1))
                .collect(Collectors.joining(" "));
        }

    private String parseDate(String unixDate){
        Date date = new Date(Long.valueOf(unixDate)*1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return jdf.format(date);
    }
}
