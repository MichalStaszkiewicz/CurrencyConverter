package com.model;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class ExpandableContent {

    private ArrayList<Entry> points;
    private ArrayList<String> date;
    private String converted_price;
    private String error_state;


    public ExpandableContent(ArrayList<Entry> points, ArrayList<String> date, String converted_price, String error_state) {
        this.error_state = error_state;
        this.converted_price = converted_price;
        this.points = points;
        this.date = date;


    }
    public void setErrorState(String error_state) {

       this.error_state = error_state;
    }
    public String getErrorState() {

        return error_state;
    }

    public void setConverted_price(String converted_price) {
        this.converted_price = converted_price;
    }

    public ArrayList<Entry> getPoints() {


        return this.points;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public String getConverted_price() {
        return this.converted_price;
    }


}
