package com.company.app.utils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class XAxisFormatter extends ValueFormatter {
    private ArrayList<String> days = new ArrayList<String>();



    public XAxisFormatter(ArrayList<String> days){
        this.days = days;

    }


    @Override
    public String getFormattedValue(float value) {
        int index = (int) value;
        ArrayList<String> list =days;
        return list.get(index);
    }




}
