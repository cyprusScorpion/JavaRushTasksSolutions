package com.javarush.task.task29.task2907;

import java.math.BigDecimal;

/* 
Этот странный BigDecimal
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getValue(1.1d, 1.2d));
    }

    public static BigDecimal getValue(double v1, double v2) {
        BigDecimal result;
        BigDecimal bdOne = new BigDecimal(v1 + "");
        BigDecimal bdTwo = new BigDecimal(v2 + "");
        result = bdOne.add(bdTwo);
        return result;
    }
}