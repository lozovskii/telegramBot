package com.bot.model;

public class UrlBuilderModel {

    private final StringBuffer sb;

    public UrlBuilderModel(String root) {
        this.sb = new StringBuffer();
        sb.append(root);
    }

    public UrlBuilderModel param(String name, String value){
        String param = name + "=" + value;
        if(!sb.toString().contains("?"))
            sb.append("?").append(param);
        else
            sb.append("&").append(param);
        return this;
    }

    @Override
    public String toString() {
        return new String(sb);
    }
}
