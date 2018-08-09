package com.bot.service;

import com.bot.model.CurrencyModel;

import java.net.MalformedURLException;
import java.util.List;

public interface CurrencyService {

    List<CurrencyModel> getTopCryptoCurrency() throws MalformedURLException;

}
