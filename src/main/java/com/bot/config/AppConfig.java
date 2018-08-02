package com.bot.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.bot")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JavaSparkContext sc(){
        SparkConf sparkConf = new SparkConf()
                .setAppName("SparkNameTest")
                .setMaster("local[*]")
                .set("spark.driver.memory", "250m");
        return new JavaSparkContext(sparkConf);
    }

}
