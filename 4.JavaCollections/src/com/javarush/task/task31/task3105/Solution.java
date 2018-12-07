package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        String zipArchive = args[1];
        File file = new File(fileName);
        Map<String, ByteArrayOutputStream> archiveFiles = new HashMap<>();
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(zipArchive))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = zis.read(buffer)) != - 1) {
                    baos.write(buffer, 0, count);
                }
                archiveFiles.put(entry.getName(), baos);
            }
        }

        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipArchive))) {
            for (Map.Entry<String, ByteArrayOutputStream> pair : archiveFiles.entrySet()) {
                if(pair.getKey().substring(
                        pair.getKey().lastIndexOf("/") + 1).equals(file.getName())) continue;
                zos.putNextEntry(new ZipEntry(pair.getKey()));
                zos.write(pair.getValue().toByteArray());
            }

            ZipEntry zip = new ZipEntry("new/" + file.getName());
            zos.putNextEntry(zip);
            Files.copy(file.toPath(), zos);
        }
    }
}
