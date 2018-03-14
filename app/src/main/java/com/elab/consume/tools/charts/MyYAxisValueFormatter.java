package com.elab.consume.tools.charts;

import com.elab.consume.tools.Globals;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by AYOUB on 12/03/2018.
 */

public class MyYAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mFormat;
    private Calendar cal;

    public MyYAxisValueFormatter(Calendar calendar) {
        cal=calendar;
        // format values to 1 decimal digit
        mFormat = new DecimalFormat("##0.0");
    }

    public MyYAxisValueFormatter() {

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        cal.set(Calendar.DAY_OF_MONTH,(int) value);
        return Globals.DAY_DATE_FORMAT.format(cal.getTime());
    }
}
