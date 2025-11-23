package com.dompedroburguer.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
    private static Dotenv dotenv;
    private Env(){}

    public static String get(String key){
        if(dotenv == null){
            dotenv = Dotenv.load();
            try {
                dotenv = Dotenv.configure().directory("./application/dompedroburguer").load();
            } catch (io.github.cdimascio.dotenv.DotenvException e){
                dotenv = Dotenv.load();
            }
        }
        return dotenv.get(key);
    }
}