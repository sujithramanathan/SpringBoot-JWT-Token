package com.reports.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class ReportUtil {

    public long getTimeStamp(String date) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date).getTime();
    }

    public double roundOff(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(value));
    }

}
