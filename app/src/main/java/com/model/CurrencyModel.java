package com.model;

import android.widget.ImageView;

public class CurrencyModel {
    private String shortName;
    private String fullName;
    private String image;
    private String price;
    private String conversionPrice;

    public CurrencyModel(String shortName, String fullName, String image, String price,String conversionPrice) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.image = image;
        this.price = price;
this.conversionPrice =conversionPrice;
    }
public String getConversionPrice(){
        return conversionPrice;
}
    public String getShortName() {
        return shortName;
    }

    public String getImage() {
        return image;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPrice() {
        return price;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;

    }

    public void setFullName(String fullName) {
        this.fullName = fullName;

    }

    public void setImage(String image) {
        this.image = image;

    }

    public void setPrice(String price) {
        this.price = price;

    }
}
