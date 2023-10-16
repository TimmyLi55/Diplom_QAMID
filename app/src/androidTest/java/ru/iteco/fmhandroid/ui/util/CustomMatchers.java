package ru.iteco.fmhandroid.ui.util;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Root;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import io.qameta.allure.kotlin.Allure;

public class CustomMatchers {

    public static Matcher<View> withItemCount(final int count) {


        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            String result;

            @Override
            public void describeTo(Description description) {
                description.appendText("Количество: " + count + "\n");
                description.appendText(result);

            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                int items = recyclerView.getAdapter().getItemCount();
                result = "Фактически " + items;
                return count == items;
            }

        };
    }

    public static void checkItemCountRV(int id, int count) {
        Allure.step("Проверка кол-ва объектов <" + count + "> внутри объекта <" + id + ">");
        onView(withId(id)).check(matches(CustomMatchers.withItemCount(count)));
    }

    public static Matcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {
            @Override
            public void describeTo(Description description) {

                description.appendText("is toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    return windowToken == appToken;
                }
                return false;
            }
        };
    }

    public static void checkToast(String message) {
        Allure.step("Проверка Toast сообщения: <" + message + ">");
        onView(withText(message))
                .inRoot(CustomMatchers.isToast())
                .check(matches(isDisplayed()));
    }

    public static Matcher<View> hasItemAtPosition(final Matcher<View> matcher, final int position) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                matcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                return matcher.matches(viewHolder.itemView);
            }
        };
    }

    public static void checkTextAtPosition(String text, int position) {
        Allure.step("Проверка наличия текста <" + text + "> на позиции <" + position + ">");
        onView(hasItemAtPosition(hasDescendant(withText(text)), position)).check(matches(isDisplayed()));
    }


}