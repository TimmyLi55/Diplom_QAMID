package ru.iteco.fmhandroid.ui.util;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

import io.qameta.allure.kotlin.Allure;

public class ViewActionWait {
    public static ViewAction waitDisplayed(final int viewId, final long millis) {
        return new ViewAction() {


            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Объект  <" + viewId + "> не появился в течении <" + millis + "> millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                Matcher<View> matchId = withId(viewId);
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchId.matches(child) && matchDisplayed.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    public static void waitView(int id, int ms) {
        Allure.step("Ожидание появления представления <" + id + "> в течении <" + ms + "> мс");
        onView(isRoot()).perform(waitDisplayed(id, ms));
    }


}
