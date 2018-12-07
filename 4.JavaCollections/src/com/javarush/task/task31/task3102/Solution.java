package com.javarush.task.task31.task3102;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/* 
Находим все файлы
*/

public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> list = new ArrayList<>();
        Files.walk(Paths.get(root))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList())
                .forEach(i->list.add(i.toString()));
        return list;
    }

    public static void main(String[] args) throws IOException {
        String root = "/home/user/JavaRushTasks/JavaRushTasks/.idea";
        System.out.println(getFileTree(root));
    }
}