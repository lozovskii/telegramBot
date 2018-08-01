package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.service.WeatherRequestService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@PropertySource("classpath:weather.properties")
public class WeatherRequestServiceImpl implements WeatherRequestService {

    @Value("${weather.token}")
    private String weatherToken;

    @Override
    public CityAnswerModel getWeather(String cityId) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + cityId +
                "&units=metric&appid=" + weatherToken);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        CityAnswerModel cityAnswerModel = new CityAnswerModel();
        cityAnswerModel.setName(jsonResponse.get("name").toString());
        cityAnswerModel.setDescription(jsonResponse.getJSONArray("weather")
                .getJSONObject(0).get("description").toString());
        cityAnswerModel.setTemp(jsonResponse.getJSONObject("main")
                .get("temp").toString());
        cityAnswerModel.setPressure(jsonResponse.getJSONObject("main")
                .get("pressure").toString());
        cityAnswerModel.setHumidity(jsonResponse.getJSONObject("main")
                .get("humidity").toString());
        try {
            cityAnswerModel.setVisibility(jsonResponse.get("visibility").toString());
        } catch (JSONException e) {
            cityAnswerModel.setVisibility("unknown");
        }
        cityAnswerModel.setWindSpeed(jsonResponse.getJSONObject("wind")
                .get("speed").toString());
        return cityAnswerModel;
    }
}
