package com.javarush.task.task26.task2613;


import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleHelper {
    private static ResourceBundle res = ResourceBundle
            .getBundle(CashMachine.RESOURCE_PATH + "common_en");

    private static final BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));


    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String message = "";
        try {
            message = bis.readLine();
            if (message.equalsIgnoreCase("EXIT"))
                throw new InterruptOperationException();
        } catch (IOException ignored) {
        }
        return message;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        String currencyCode;
        writeMessage(res.getString("choose.currency.code"));
        while (true) {
            currencyCode = readString();
            if (currencyCode.length() == 3) {
                break;
            } else {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return currencyCode.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] validTwoDigits;
        writeMessage(String
                .format(res.getString("choose.denomination.and.count.format"), currencyCode));
        while (true) {
            String s = readString();
            validTwoDigits = s.split(" ");
            int k = 0;
            int l = 0;
            try {
                k = Integer.parseInt(validTwoDigits[0]);
                l = Integer.parseInt(validTwoDigits[1]);
            } catch (Exception e) {
                writeMessage(res.getString("invalid.data"));
            }
            if (k <= 0 || l <= 0 || validTwoDigits.length > 2) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return validTwoDigits;
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString("choose.operation") + " \n" +
        res.getString("operation.INFO") + ": 1;\n" +
        res.getString("operation.DEPOSIT") + ": 2;\n" +
        res.getString("operation.WITHDRAW") + ": 3;\n" +
        res.getString("operation.EXIT") + ": 4");
        while (true) {
            String line = readString();
            if (checkWithRegExp(line)) {
                return Operation.getAllowableOperationByOrdinal(Integer.parseInt(line));
            } else {
                writeMessage(res.getString("invalid.data"));
            }
        }
    }

    private static boolean checkWithRegExp(String Name) {
        Pattern p = Pattern.compile("^[1-4]$");
        Matcher m = p.matcher(Name);
        return m.matches();
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}