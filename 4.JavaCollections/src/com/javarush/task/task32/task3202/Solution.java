package com.javarush.task.task32.task3202;

import java.io.*;

/* 
Читаем из потока
*/
public class Solution {

        public static void main (String[] args) throws Exception {

            String test = "Hi!\n My name is Richard\n I'm a photographer\n";
            System.out.println(test);
            InputStream inputStream = new ByteArrayInputStream(test.getBytes());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            BufferedInputStream bis = new BufferedInputStream(inputStream);
            while (bis.available() > 0)  {
                int data = bis.read();
                System.out.println(data);
                outputStream.write(data);
            }

            String result = new String(outputStream.toByteArray());
            System.out.println("Результат: " + result);


    }
}
