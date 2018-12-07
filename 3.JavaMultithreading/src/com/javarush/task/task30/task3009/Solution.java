package com.javarush.task.task30.task3009;

import java.util.HashSet;
import java.util.Set;

/* 
Палиндром?
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getRadix("112"));        //expected output: [3, 27, 13, 15]
        System.out.println(getRadix("123"));        //expected output: [6]
        System.out.println(getRadix("5321"));       //expected output: []
        System.out.println(getRadix("1A"));         //expected output: []
        System.out.println(Integer.toString(Integer.parseInt("112"), 3));
        System.out.println(Integer.toString(Integer.parseInt("112"), 27));
        System.out.println(Integer.toString(Integer.parseInt("112"), 13));
        System.out.println(Integer.toString(Integer.parseInt("112"), 15));
        System.out.println(Integer.toString(Integer.parseInt("123"), 6));
    }

    private static Set<Integer> getRadix(String number) {
        Set<Integer> getRadix = new HashSet<>();
        //number = number.replaceAll("[^A-Za-z0-9]", "");
        StringBuilder stringBuilder;
        try {
            int numberInt = Integer.parseInt(number);
            for(int i = 2; i < 37; i++) {
                stringBuilder = new StringBuilder(Integer.toString(numberInt, i));
                if (stringBuilder.toString().equals(stringBuilder.reverse().toString())) {
                    getRadix.add(i);
                }
            }

        } catch (NumberFormatException e) {
        }
        return getRadix;
    }
}