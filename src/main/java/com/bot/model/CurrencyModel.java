package com.bot.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CurrencyModel {

    private String ccy;
    private String baseCcy;
    private String buy;
    private String sale;

    @Override
    public String toString() {
        return "Privat Bank: \n" + ccy + " - "+ buy + " " + baseCcy + " " + sale + " " + baseCcy;
    }
}
