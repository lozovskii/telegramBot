package com.bot.service.Impl;

import com.bot.model.CryptoCurrencyModel;
import com.bot.service.CurrencyService;
import com.bot.service.WebService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final String TOP_CRYPT_CURR_URL = "https://api.coinmarketcap.com/v2/ticker/?limit=";
    private final String LIMIT_TOP = "15";

    @Autowired
    private WebService webService;

    @Override
    public List<CryptoCurrencyModel> getTopCryptoCurrency() throws MalformedURLException {
        String response = webService.getResponse(new URL(TOP_CRYPT_CURR_URL + LIMIT_TOP));
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject dataNode = jsonResponse.getJSONObject("data");
        Iterator dataNodeKyes = dataNode.keys();
        List<String> keys = new ArrayList<>();
        List<CryptoCurrencyModel> responseCurrencyList = new ArrayList<>();
        while (dataNodeKyes.hasNext()) {
            keys.add(dataNodeKyes.next().toString());
        }
        for (String key : keys) {
            JSONObject keyElement = dataNode.getJSONObject(key);
            JSONObject quotes = keyElement.getJSONObject("quotes");
            Iterator quotesKeys = quotes.keys();
            String currency = null;
            while (quotesKeys.hasNext()) {
                currency = quotesKeys.next().toString();
            }
            String price = quotes.getJSONObject(currency).get("price").toString();
            String priceRound = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).toString();
            CryptoCurrencyModel cryptoCurrencyModel = new CryptoCurrencyModel.CryptoCurrencyModelBuilder(
                    keyElement.get("name").toString(), priceRound, currency)
                    .id(key)
                    .symbol(keyElement.get("symbol").toString())
                    .rank(keyElement.get("rank").toString())
                    .build();
            responseCurrencyList.add(cryptoCurrencyModel);
        }
        responseCurrencyList.sort(Comparator.comparingInt((CryptoCurrencyModel x) -> Integer.parseInt(x.getRank())));
        return responseCurrencyList;
    }
}
