package com.tspoon.stylight.utils;

import java.text.NumberFormat;

public class Formatter {

    private static NumberFormat numberFormat = NumberFormat.getInstance();

    static {
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
    }

    public static String formatCurrency(double n) {
        return "€" + numberFormat.format(n);
    }

    public static String formatCurrencyOnSale(double n, double salePercent) {
        double percent = 1.0 - salePercent;
        n *= percent;
        return "€" + numberFormat.format(n) + "   ";
    }
}
