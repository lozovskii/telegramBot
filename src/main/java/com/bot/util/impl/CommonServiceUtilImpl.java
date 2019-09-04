package com.bot.util.impl;

import com.bot.model.UrlBuilderModel;
import com.bot.util.CommonServiceUtil;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CommonServiceUtilImpl implements CommonServiceUtil {

    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDateTime.parse(date, formatter);
    }

    @Override
    public URL parseToURL(UrlBuilderModel urlBuilderModel) {
        URL url = null;
        try {
            url = new URL(urlBuilderModel.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
