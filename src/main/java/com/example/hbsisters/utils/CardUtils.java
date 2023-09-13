package com.example.hbsisters.utils;

import org.jetbrains.annotations.NotNull;

public class CardUtils {
    public static int getCvv() {
        return (int) (Math.random() * (999 - 100)) + 100;
    }

    @NotNull
    public static String getCardNumber() {
        String newNumberCard= ((int)(Math.random() * (9999 - 1000)) + 1000) +"-"+((int)(Math.random() * (9999 - 1000)) + 1000)+"-"+((int)(Math.random() * (9999 - 1000)) + 1000)+"-"+((int)(Math.random() * (9999 - 1000)) + 1000)+"-"+((int)(Math.random() * (9999 - 1000)) + 1000);
        return newNumberCard;
    }
}
