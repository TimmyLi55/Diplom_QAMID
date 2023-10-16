package ru.iteco.fmhandroid.ui.util;

import java.io.IOException;

public class AppDataCleaner {


    public static void clearAppData() {
        String packageName = "ru.iteco.fmhandroid";
        try {
            Process process = Runtime.getRuntime().exec("adb shell pm clear " + packageName);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Данные приложения " + packageName + " успешно очищены.");
            } else {
                System.err.println("Ошибка при очистке данных приложения " + packageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
