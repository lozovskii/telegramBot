package com.bot.service.Impl;

import com.bot.service.WeatherService;
import com.bot.util.NoSuchCityException;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final Integer CITY_ID_INDEX = 2;

    @Value("${jsonPath}")
    private String jsonPath;

    @Autowired
    private JavaSparkContext sc;

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

    private String makeCorrectCityNameFormat(String city){
        return Arrays.stream(city.split(" "))
                .map(i -> i.substring(0, 1).toUpperCase() + i.substring(1, i.length()))
                .collect(Collectors.joining(" "));
        }

}
