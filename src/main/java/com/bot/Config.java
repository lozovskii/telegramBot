package com.bot;

import java.io.*;
import java.util.Properties;

public class Config {

    public static final String BOT_PROPERTIES_FILE = "./src/main/resources/bot.properties";

    private static final String BOT_NAME = "bot.name";
    private static final String BOT_TOKEN = "bot.token";

    public static String botName;
    public static String botToken;

    public static void loadProp(){
        Properties prop = new Properties();

        try(InputStream inputStreamReadProp = new FileInputStream(new File(BOT_PROPERTIES_FILE))){
            prop.load(inputStreamReadProp);
            inputStreamReadProp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        botName = prop.getProperty(BOT_NAME);
        botToken = prop.getProperty(BOT_TOKEN);
    }

}
