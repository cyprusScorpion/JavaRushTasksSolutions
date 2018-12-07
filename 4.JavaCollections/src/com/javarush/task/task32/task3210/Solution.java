package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        byte[] buffer = new byte[text.length()];
        raf.seek(number);
        raf.read(buffer, 0, buffer.length);
        String bufferToString = convertToString(buffer);
        String addToFile = bufferToString.equals(text) ? "true" : "false";
        raf.seek(raf.length());
        raf.write(addToFile.getBytes());
    }

    private static String convertToString(byte[] readByte){
        return new String(readByte);
    }
}