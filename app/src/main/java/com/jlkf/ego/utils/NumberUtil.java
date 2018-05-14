package com.jlkf.ego.utils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public class NumberUtil {

    public static String fomater(double v) {
        DecimalFormat dateFormat = new DecimalFormat("#######0.00");
        return dateFormat.format(v);
    }



    public static String fomaterToOne(double v) {
        DecimalFormat dateFormat = new DecimalFormat("##0.0");
        return dateFormat.format(v);
    }
}
