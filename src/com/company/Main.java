package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        String path = "C://Games/savegames/";

        List<GameProgress> gameProgress = Arrays.asList(
                new GameProgress(70, 20, 10, 90.5),
                new GameProgress(60, 10, 7, 30.5),
                new GameProgress(50, 5, 3, 60.5)
        );
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < gameProgress.size(); i++) {
            String datPath = path + "game" + i + ".dat";
            if (saveGame(datPath, gameProgress.get(i)))
                arrayList.add(datPath);
        }

        zipFiles(path + "zip.zip", arrayList);
        deleteFiles(arrayList);
    }

    public static Boolean saveGame(String path, GameProgress x) {
        try (FileOutputStream stream = new FileOutputStream(path);
             ObjectOutputStream outputStream = new ObjectOutputStream(stream)) {
            outputStream.writeObject(x);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static void zipFiles(String path, List<String> arrayList) throws Exception {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path))) {

            for (String sf : arrayList) {
                try (FileInputStream fileInputStream = new FileInputStream(sf)) {
                    ZipEntry entry = new ZipEntry(new File(sf).getName());
                    zipOutputStream.putNextEntry(entry);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    zipOutputStream.write(buffer);
                    zipOutputStream.closeEntry();
                }
            }
        }
    }

    private static void deleteFiles(List<String> arrayList) {
        for (String x : arrayList) {
            File fileToDel = new File(x);
            if (fileToDel.delete()) {
                System.out.println("Файл " + x + " удален");

            }
        }
    }
}