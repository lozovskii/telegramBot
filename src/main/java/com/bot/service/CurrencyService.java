package com.bot.service;

import com.bot.model.CryptoCurrencyModel;

import java.net.MalformedURLException;
import java.util.List;

public interface CurrencyService {

    List<CryptoCurrencyModel> getTopCryptoCurrency() throws MalformedURLException;

}
