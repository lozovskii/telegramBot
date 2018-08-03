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

    @Value("${weatherToken}")
    private String weatherToken;

    // why does thiis method throw IOException | consider handling it in try/catch
    @Override
    public CityAnswerModel getWeather(String cityId) throws IOException {
        // its better to extract string constants
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + cityId +
                "&units=metric&appid=" + weatherToken);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        // this should be in try/catch with resources
        // consider creating separate method
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        ///////////////////////////////////////////////////////

        JSONObject jsonResponse = new JSONObject(response.toString());
        CityAnswerModel cityAnswerModel = new CityAnswerModel();
        cityAnswerModel.setName(jsonResponse.get("name").toString());
        // it is not obvius why 0 index is used here | consider refactoring
        cityAnswerModel.setDescription(jsonResponse.getJSONArray("weather")
                .getJSONObject(0).get("description").toString());
        cityAnswerModel.setTemp(jsonResponse.getJSONObject("main")
                .get("temp").toString());
        cityAnswerModel.setPressure(jsonResponse.getJSONObject("main")
                .get("pressure").toString());
        cityAnswerModel.setHumidity(jsonResponse.getJSONObject("main")
                .get("humidity").toString());
        // separate method for try/catch
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
