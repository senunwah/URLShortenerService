package com.interswitch.URLShorteningService.services;

import java.security.SecureRandom;

public class ShortenLink {

    static final String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    static SecureRandom sr = new SecureRandom();

    public static String generateShortUrl(int Length){
        StringBuilder sb = new StringBuilder(Length);
        for (int i = 0; i < Length; i++){
            sb.append(source.charAt(sr.nextInt(source.length())));
        }
        return sb.toString();
    }
}
