package com.example.saulpark.wqp;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MyXAxisValueFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // Simple version. You should use a DateFormatter to specify how you want to textually represent your date.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //return sdf.format(new Date(new Float(value).longValue()).toString());
            //return new Date(new Float(value).longValue()).toString();
            Date from = new Date(new Float(value).longValue());

            SimpleDateFormat transFormat = new SimpleDateFormat("MM-dd HH:mm");

            String to = transFormat.format(from);

            return to;
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }
}