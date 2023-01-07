package com.company.app.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class YAxisFormatter extends ValueFormatter {
    public YAxisFormatter() {
        super();
    }

    @Override
    public String getFormattedValue(float value) {
        return new Utils().formatPrice(value);
    }
}
