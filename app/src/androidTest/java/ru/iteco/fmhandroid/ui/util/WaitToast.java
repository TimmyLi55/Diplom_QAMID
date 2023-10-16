package ru.iteco.fmhandroid.ui.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.SystemClock;

import androidx.test.platform.app.InstrumentationRegistry;

import io.qameta.allure.kotlin.Allure;

public class WaitToast {
    public static boolean waitForAnyToast(long timeoutMillis) {
        Allure.step("Ожидание любого Toast сообщения " + timeoutMillis + " мс");
        long endTime = SystemClock.uptimeMillis() + timeoutMillis;

        while (SystemClock.uptimeMillis() <= endTime) {
            if (isAnyToastVisible()) {
                return true; // Toast сообщение было найдено
            }
            SystemClock.sleep(1000); // Подождать 1 секунду перед следующей проверкой
        }

        return false; // Toast сообщение не было найдено в заданный период времени
    }

    private static boolean isAnyToastVisible() {
        try {
            Activity toast = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(
                    new ToastMonitor(), 7000); // Подождать Toast сообщение до 5 секунд
            return toast != null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static class ToastMonitor extends Instrumentation.ActivityMonitor {
        @Override
        public Instrumentation.ActivityResult onStartActivity(Intent intent) {
            return null;
        }

    }
}
