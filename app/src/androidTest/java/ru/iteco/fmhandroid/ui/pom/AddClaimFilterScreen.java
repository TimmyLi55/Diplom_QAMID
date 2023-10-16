package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AddClaimFilterScreen {
    public static final int itemFilterOpenID = R.id.item_filter_open;
    public static final int itemFilterCancelledID = R.id.item_filter_cancelled;
    public static final int itemFilterExecutedID = R.id.item_filter_executed;
    public static final int itemFilterInProgressID = R.id.item_filter_in_progress;
    public static final int claimListFilterOkButtonID = R.id.claim_list_filter_ok_material_button;

    public static void clickCheckBoxOpen() {
        Allure.step("Выбор чек бокса \"Открыта\"");
        onView(allOf(withId(itemFilterOpenID)))
                .perform(scrollTo(), click());
    }

    public static void clickOkButton() throws InterruptedException {
        Allure.step("Нажатие кнопки \"ОК\"");
        onView(allOf(withId(claimListFilterOkButtonID)))
                .perform(scrollTo(), click());
        Thread.sleep(5000);
    }
}
