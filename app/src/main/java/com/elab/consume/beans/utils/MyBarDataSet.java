package com.elab.consume.beans.utils;


import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

/**
 * Created by AYOUB on 11/03/2018.
 */

public class MyBarDataSet extends BarDataSet {

    public MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        /*
        if(getEntryForXIndex(index).getVal() < 95)
            return mColors.get(0);
        else if(getEntryForXIndex(index).getVal() < 100)
            return mColors.get(1);
        else // greater or equal than 100 red
            return mColors.get(2);
            */
        return 0;
    }
}
