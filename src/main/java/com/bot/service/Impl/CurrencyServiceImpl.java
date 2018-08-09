package com.bot.service.Impl;

import com.bot.model.CurrencyModel;
import com.bot.service.CurrencyService;
import com.bot.service.WebService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final String TOP_CRYPT_CURR_URL= "https://api.coinmarketcap.com/v2/ticker/?limit=";
    private final String LIMIT_TOP = "15";
    @Autowired
    private WebService webService;

    @Override
    public List<CurrencyModel> getTopCryptoCurrency() throws MalformedURLException {
        String response = webService.getResponse(new URL(TOP_CRYPT_CURR_URL));
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject data = jsonResponse.getJSONObject("data");
        System.out.println(jsonResponse.getJSONArray("data"));
//        CurrencyModel currencyModel = new CurrencyModel.CurrencyModelBuilder()
        return null;
    }
}
