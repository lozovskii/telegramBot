package com.bot.model;

public class UrlBuilderModel {

    private final StringBuffer sb;

    public UrlBuilderModel() {
        this.sb = new StringBuffer();
    }

    public UrlBuilderModel root(String url){
        sb.append(url);
        return this;
    }

    public UrlBuilderModel param(String name, String value){
        String param = name + "=" + value;
        if(!sb.toString().contains("?"))
            sb.append("?").append(param);
        else
            sb.append("&").append(param);
        return this;
    }

}
