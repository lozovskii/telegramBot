package com.bot.service.Impl;

import com.bot.service.WeatherService;
import com.bot.util.NoSuchCityException;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${jsonPath}")
    private String jsonPath;

    @Override
    public Long getCityId(String city, JavaSparkContext sc) throws NoSuchCityException {
        SQLContext sqlContext = SQLContext.getOrCreate(sc.sc()).newSession();
        String correctCityNameFormat = city.substring(0, 1).toUpperCase() + city.substring(1, city.length()).toLowerCase();
        Column columnName = new Column("name");
        Dataset<Row> json = sqlContext.read()
                .option("multiline", "true")
                .json(jsonPath);
        json.show(false);
//                .filter(columnName.equalTo("\"" + correctCityNameFormat + "\""));
        Object rowEntity = json.first();
        if(rowEntity == null){
            System.out.println("Error here");
//            throw new NoSuchCityException("This city name is not correct or city with this name does not exist!");
        }
        return Long.valueOf(((Row) rowEntity).get(2).toString());
    }
}
