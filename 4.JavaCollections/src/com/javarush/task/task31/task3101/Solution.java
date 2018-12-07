package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        File newFile = new File(resultFileAbsolutePath.getParent() + "/allFilesContent.txt");
        FileUtils.renameFile(resultFileAbsolutePath, newFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        ArrayList<File> list = new ArrayList<>();
        for (File file : selectFiles(path, list)) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
            writer.newLine();
            //reader.close();
        }
        writer.close();
    }

    public static ArrayList<File> selectFiles(File path, ArrayList<File> list) {
        //File[] files = path.listFiles();
        for (File file : path.listFiles()) {
            if (file.isDirectory()) {
                selectFiles(file, list);
            } else if (file.length() > 50) {
                FileUtils.deleteFile(file);
            } else {
                list.add(file);
            }
        }
        Collections.sort(list, Comparator.comparing(File::getName));
        return list;
    }
}
