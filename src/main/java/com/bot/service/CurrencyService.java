package com.bot.service;

import com.bot.model.CryptoCurrencyModel;
import com.bot.model.CurrencyModel;

import java.net.MalformedURLException;
import java.util.List;

public interface CurrencyService {

    List<CryptoCurrencyModel> getTopCryptoCurrency() throws MalformedURLException;

    List<CurrencyModel> getCurrencyInfo() throws MalformedURLException;

}
