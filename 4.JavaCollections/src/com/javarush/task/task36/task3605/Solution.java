package com.javarush.task.task36.task3605;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        File file = new File(args[0]);

        String text = reader(file);


        List<String> stringList = Arrays.asList(text.toLowerCase().split(""));
        TreeSet<String> treeSet = new TreeSet<>();

        for (int i = 0; i < stringList.size(); i++) {
            String s = stringList.get(i);
            if((s.matches("\\w")) && (s.matches("[^0-9]")))
                treeSet.add(stringList.get(i));
        }


        Iterator iterator = treeSet.iterator();
        if (treeSet.size() >= 5) {

            int count = 0;
            while (iterator.hasNext() && count < 5) {
                System.out.print(iterator.next());
                count ++;
            }

        } else {
            while (iterator.hasNext()) {
                System.out.print(iterator.next());
            }
        }


    }

    public static String reader(File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        String res = "";
        int b = raf.read();
        while(b != -1){
            res = res + (char)b;
            b = raf.read();
        }
        raf.close();
        return res;
    }
}
