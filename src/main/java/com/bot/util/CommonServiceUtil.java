package com.bot.util;

import com.bot.model.UrlBuilderModel;

import java.net.URL;
import java.time.LocalDateTime;

public interface CommonServiceUtil {

    LocalDateTime parseDate(String date);

    URL parseToURL(UrlBuilderModel urlBuilderModel);

}
