package com.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoCurrencyModel {
    private final String name;
    private final String price;
    private final String currency;
    private String id;
    private String symbol;
    private String rank;

    public static class CryptoCurrencyModelBuilder {
        private final String name;
        private final String price;
        private final String currency;
        private String id;
        private String symbol;
        private String rank;

        public CryptoCurrencyModelBuilder(String name, String price, String currency) {
            this.name = name;
            this.price = price;
            this.currency = currency;
        }

        public CryptoCurrencyModelBuilder id(String id){
            this.id = id;
            return this;
        }

        public CryptoCurrencyModelBuilder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public CryptoCurrencyModelBuilder rank(String rank) {
            this.rank = rank;
            return this;
        }

        public CryptoCurrencyModel build() {
            return new CryptoCurrencyModel(this);
        }
    }

    public CryptoCurrencyModel(CryptoCurrencyModelBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.currency = builder.currency;
        this.id = builder.id;
        this.symbol = builder.symbol;
        this.rank = builder.rank;
    }

    @Override
    public String toString() {
        return rank + ".) " + name + " (" + symbol + ") " +" : " + price + " " + currency + ';';
    }
}
