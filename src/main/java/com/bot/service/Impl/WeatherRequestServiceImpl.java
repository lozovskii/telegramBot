package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.service.WeatherRequestService;
import com.bot.service.WebService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
@PropertySource("classpath:weather.properties")
public class WeatherRequestServiceImpl implements WeatherRequestService {

    private final Integer GETTING_FULL_SUBJSON = 0;
    @Autowired
    private WebService webService;
    @Value("${weatherToken}")
    private String weatherToken;

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

}
