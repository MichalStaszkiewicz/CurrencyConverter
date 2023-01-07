package com.company.app.utils;

import java.math.BigDecimal;


public class Utils {


    public Utils() {


    }


    public String formatPrice(float price) {
        BigDecimal value = new BigDecimal(Float.toString(price));


        String result = "";
        int zeros = 0;
        for (int i = 0; i < value.toString().length(); i++) {
            result += value.toString().charAt(i);
            if (value.toString().charAt(i) == '0') {
                zeros++;
            }
            if (result.length() >= 4 && zeros < result.length() - 1) {
                break;

            }


        }
        return result;


    }

}
