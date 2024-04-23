package com.workintech.s17d2.tax;

public class DeveloperTax implements Taxable {
    public static final double SIMPLE_TAX_RATE = 15.0;
    public static final double MIDDLE_TAX_RATE = 25.0;
    public static final double UPPER_TAX_RATE = 35.0;

    @Override
    public double getSimpleTaxRate() {
        return SIMPLE_TAX_RATE;

    }

    @Override
    public double getMiddleTaxRate() {
        return MIDDLE_TAX_RATE;
    }

    @Override
    public double getUpperTaxRate() {
        return UPPER_TAX_RATE;
    }
}
