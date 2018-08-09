package com.bot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyModel {
    private final String name;
    private final String price;
    private final String symbol;
    private String rank;

    public static class CurrencyModelBuilder {
        private final String name;
        private final String price;
        private final String symbol;
        private String rank;

        public CurrencyModelBuilder(String name, String price, String symbol) {
            this.name = name;
            this.price = price;
            this.symbol = symbol;
        }

        public CurrencyModelBuilder rank(String rank) {
            this.rank = rank;
            return this;
        }

        public CurrencyModel build() {
            return new CurrencyModel(this);
        }
    }

    public CurrencyModel(CurrencyModelBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.symbol = builder.symbol;
        this.rank = builder.rank;
    }

    @Override
    public String toString() {
        return "" +
                name + ": " +
                price + " " + symbol + ';' +
                "rank=" + rank;
    }
}
