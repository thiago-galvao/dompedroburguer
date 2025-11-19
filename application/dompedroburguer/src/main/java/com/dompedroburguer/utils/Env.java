package com.dompedroburguer.utils;

public class Env {
    
    private Env(){}

    public static String get(String key){
        // First try environment variables
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }
        // Fallback to system properties
        return System.getProperty(key);
    }
}
