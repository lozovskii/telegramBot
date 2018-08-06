package com.bot.service.Impl;

import com.bot.model.CityAnswerModel;
import com.bot.service.WeatherRequestService;
import com.bot.util.NoConnection;
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
    @Value("${weatherToken}")
    private String weatherToken;

    private final Integer GETTING_FULL_SUBJSON = 0;

    @Override
    public CityAnswerModel getWeather(String cityId){
        String response = getWeatherConnection(cityId);
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
        setVisibilityForCity(cityAnswerModel, jsonResponse);
        return cityAnswerModel;
    }

    private void setVisibilityForCity(CityAnswerModel cityAnswerModel, JSONObject jsonResponse){
        try {
            cityAnswerModel.setVisibility(jsonResponse.get("visibility").toString());
        } catch (JSONException e) {
//            cityAnswerModel.setVisibility("unknown");
            e.printStackTrace();
        }
    }

    private String getWeatherConnection(String cityId){
        try {
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
            return response.toString();
        }catch (IOException e){
            throw new NoConnection("Failed to connection. ", e);
        }
    }

}
