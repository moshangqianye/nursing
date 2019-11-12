package com.jqsoft.nursing.helper;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.jqsoft.nursing.base.Constants;

import java.text.DecimalFormat;

public class IntValueFormatter implements IValueFormatter
{

    /**
     * DecimalFormat for formatting
     */
    protected DecimalFormat mFormat;


    /**
     * Constructor that specifies to how many digits the value should be
     * formatted.
     *
     */
    public IntValueFormatter() {
        setup();
    }

    /**
     * Sets up the formatter with a given number of decimal digits.
     *
     */
    public void setup() {

        mFormat = new DecimalFormat(Constants.INT_NUMBER_FORMATTER_STRING);
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        // put more logic here ...
        // avoid memory allocations here (for performance reasons)

        return mFormat.format(value);
    }

}
