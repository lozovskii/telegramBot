package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.model.UrlBuilderModel;
import com.bot.service.DBService;
import com.bot.service.WeatherService;
import com.bot.service.WebService;
import com.bot.util.CommonServiceUtil;
import com.bot.util.exception.NoSuchCityException;
import com.vdurmont.emoji.EmojiParser;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final Integer CITY_ID_INDEX = 2;
    private final Integer GETTING_FULL_SUBJSON = 0;
    private final Integer WEATHER_FORECAST_FOR_HALF_DAY = 4;

    @Autowired
    private DBService dbService;
    @Autowired
    private WebService webService;
    @Autowired
    private JavaSparkContext sc;
    @Autowired
    private CommonServiceUtil commonServiceUtil;

    @Value("${weatherToken}")
    private String weatherToken;
    @Value("${jsonPath}")
    private String jsonPath;
    @Value("${weatherWeatherURL}")
    private String weatherWeatherUrl;
    @Value("${weatherForecastURL}")
    private String weatherForecastUrl;

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
            throw new NoSuchCityException("This city name is not correct or city with this name does not exist! " +
                    "Or phrase is empty", e);
        }
        return (((Row) rowEntity).get(CITY_ID_INDEX).toString());
    }

    @Override
    public CityAnswerModel getCurrentWeather(String cityId) {
        UrlBuilderModel urlBuilderModel = new UrlBuilderModel(weatherWeatherUrl)
                .param("id", cityId)
                .param("units", "metric")
                .param("appid", weatherToken);
        String response = getResponse(urlBuilderModel);
        return parseResponse(response);
    }

    @Override
    public List<CityAnswerModel> getWeatherForecast(String cityId) {
        UrlBuilderModel urlBuilderModel = new UrlBuilderModel(weatherForecastUrl)
                .param("id", cityId)
                .param("units", "metric")
                .param("appid", weatherToken);
        String response = getResponse(urlBuilderModel);
        return parseResponseForecast(response);
    }

    @Override
    public CityAnswerModel getWeatherByCoord(Message msg) {
        Location location = msg.getLocation();
        UrlBuilderModel urlBuilderModel = new UrlBuilderModel(weatherWeatherUrl)
                .param("lat", location.getLatitude().toString())
                .param("lon", location.getLongitude().toString())
                .param("units", "metric")
                .param("appid", weatherToken);
        String response = getResponse(urlBuilderModel);
        return parseResponse(response);
    }

    public String parseWeather(String weather) {
        return Arrays.stream(weather.split("\\n"))
                .filter(x -> !x.contains("Visibility = null"))
                .collect(Collectors.joining("\n"));
    }

    private List<CityAnswerModel> parseResponseForecast(String response)  {
        JSONObject jsonResponse = new JSONObject(response);
        String name = jsonResponse.getJSONObject("city").get("name").toString();
        String country = jsonResponse.getJSONObject("city").get("country").toString();
        JSONArray list = jsonResponse.getJSONArray("list");
        List<CityAnswerModel> result = new ArrayList<>();

        list.forEach(x -> {
            if(result.size() < WEATHER_FORECAST_FOR_HALF_DAY){
                JSONObject member = (JSONObject) x;
                String date = member.get("dt_txt").toString();
                String temperature = member.getJSONObject("main").get("temp").toString();
                String description = member.getJSONArray("weather").getJSONObject(0).get("description").toString();

                LocalDateTime formatDate = commonServiceUtil.parseDate(date);

                CityAnswerModel cityAnswerModel = new CityAnswerModel.CityAnswerModelBuilder(name)
                        .date(formatDate)
                        .temp(temperature)
                        .description(description)
                        .country(country)
                        .build();

                result.add(cityAnswerModel);
            }
        });

        return result;
    }

    private CityAnswerModel parseResponse(String response) {
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

    private String addEmoji(String description) {
        String emoji = dbService.searchEmoji(description);
        return EmojiParser.parseToUnicode(description + emoji);
    }

    private String makeCorrectCityNameFormat(String city) {
        return Arrays.stream(city.split(" "))
                .map(i -> i.substring(0, 1).toUpperCase() + i.substring(1))
                .collect(Collectors.joining(" "));
    }

    private String getResponse(UrlBuilderModel urlBuilderModel){
        URL url = commonServiceUtil.parseToURL(urlBuilderModel);
        return webService.sendRequest(url);
    }

}
