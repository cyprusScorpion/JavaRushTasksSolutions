package com.javarush.task.task34.task3404;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Locale;

/*
Рекурсия для мат. выражения
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recursion("sin(2*(-5+1.5*4)+28)", 0); //expected output 0.5 6
    }

    public void recursion(final String expression, int countOperation) {
        //implement
        Locale.setDefault(Locale.ENGLISH);
        DecimalFormat df = new DecimalFormat("#.##");
        String value = expression.replaceAll(" ", "");
        int currentCountOperation = countOperation + 1;
        int inside = 0;
        int p1 = -1, p2 = -1, p3 = -1;
        char[] s = value.toCharArray();
        for (int i = s.length - 1; i >= 0; i--) {
            switch (s[i]) {
                case '^':
                    if (inside == 0 && p3 == -1) p3 = i;
                    break;
                case '*':
                case '/':
                    if (inside == 0 && p2 == -1) p2 = i;
                    break;
                case '+':
                case '-':
                    if (inside == 0 && p1 == -1) p1 = i;
                    break;
                case '(':
                    inside++;
                    break;
                case ')':
                    inside--;
                    break;
            }
        }
        if (p1 != -1) p2 = p1;
        if (p2 != -1) p3 = p2;
        if (p3 != -1) {
            PrintStream oldStream = System.out;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream newStream = new PrintStream(outputStream);
            System.setOut(newStream);
            recursion(value.substring(0, p3), currentCountOperation);
            String[] part1 = outputStream.toString().split("\\s");
            currentCountOperation = currentCountOperation < Integer.parseInt(part1[1]) ? Integer.parseInt(part1[1]) : currentCountOperation;
            outputStream.reset();
            recursion(value.substring(p3 + 1), currentCountOperation);
            String[] part2 = outputStream.toString().split("\\s");
            currentCountOperation = currentCountOperation < Integer.parseInt(part2[1]) ? Integer.parseInt(part2[1]) : currentCountOperation;
            System.setOut(oldStream);
            switch (s[p3]) {
                case '^':
                    customPrint(df, Math.rint(100.0 *(Math.pow(Double.parseDouble(part1[0]), Double.parseDouble(part2[0]))))/100, countOperation, currentCountOperation);
                    return;
                case '*':
                    customPrint(df,  Math.rint(100.0 *(Double.parseDouble(part1[0]) * Double.parseDouble(part2[0])))/100, countOperation, currentCountOperation);
                    return;
                case '/':
                    customPrint(df,  Math.rint(100.0 *(Double.parseDouble(part1[0]) / Double.parseDouble(part2[0])))/100, countOperation, currentCountOperation);
                    return;
                case '+':
                    customPrint(df,  Math.rint(100.0 *(Double.parseDouble(part1[0]) + Double.parseDouble(part2[0])))/100, countOperation, currentCountOperation);
                    return;
                case '-':
                    customPrint(df,  Math.rint(100.0 *(Double.parseDouble(part1[0]) - Double.parseDouble(part2[0])))/100, countOperation, currentCountOperation);
                    return;
            }
        }
        if (s.length > 0 && s[0] == '(' && s[s.length - 1] == ')') {
            recursion(value.substring(1, s.length - 1), countOperation);
            return;
        }
        if (s.length > 5 && Character.isAlphabetic(s[0]) && s[3] == '(' && s[s.length - 1] == ')') {
            String funcName = value.substring(0, 3);
            PrintStream oldStream = System.out;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream newStream = new PrintStream(outputStream);
            System.setOut(newStream);
            recursion(value.substring(4, s.length - 1), currentCountOperation);
            String[] part = outputStream.toString().split("\\s");
            currentCountOperation = currentCountOperation < Integer.parseInt(part[1]) ? Integer.parseInt(part[1]) : currentCountOperation;
            System.setOut(oldStream);
            if ("sin".equals(funcName)) {
                customPrint(df,  Math.rint(100.0 *(Math.sin(Math.toRadians(Double.parseDouble(part[0])))))/100, countOperation, currentCountOperation);
                return;
            }
            if ("cos".equals(funcName)) {
                customPrint(df,  Math.rint(100.0 *(Math.cos(Math.toRadians(Double.parseDouble(part[0])))))/100, countOperation, currentCountOperation);
                return;
            }
            if ("tan".equals(funcName)) {
                customPrint(df,  Math.rint(100.0 *(Math.tan(Math.toRadians(Double.parseDouble(part[0])))))/100, countOperation, currentCountOperation);
                return;
            }
        }
        double n = 0d;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length && (Character.isDigit(s[i]) || s[i] == '.'); i++) {
            sb.append(s[i]);
        }
        if (sb.length() > 0) {
            n = Double.parseDouble(sb.toString());
        }
        customPrint(df, n, countOperation, countOperation);
    }
    private void customPrint(DecimalFormat df, double v, int countOperation, int currentCountOperation) {
        v = (Math.rint(100.0 * v) / 100);
        String rez = String.valueOf(v);
        if (rez.endsWith(".0"))
        {
            rez = rez.replace(".0","");
        }

        if (countOperation == 0) {
            System.out.println(rez + " " + currentCountOperation);
        } else {
            System.out.println(rez + " " + currentCountOperation);
        }
    }

    public Solution() {
        //don't delete
    }
}
