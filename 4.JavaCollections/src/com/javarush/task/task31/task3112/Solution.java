package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile(
                "https://javarush.ru/testdata/secretPasswords.txt", Paths.get("/home/user/Загрузки"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method
        URL url = new URL(urlString);
        Path tmpFile = Files.createTempFile("tmp-", ".tmp");
        InputStream inputStream = url.openStream();
        //Files.copy(inputStream, downloadFile);
        Files.copy(inputStream, tmpFile, StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();
        String fileName = urlString.substring(urlString.lastIndexOf("/"));
        Path downloadFile = Paths.get(downloadDirectory.toString(),fileName);
        Files.move(tmpFile, downloadFile, StandardCopyOption.REPLACE_EXISTING);
        return downloadFile;
    }
}
