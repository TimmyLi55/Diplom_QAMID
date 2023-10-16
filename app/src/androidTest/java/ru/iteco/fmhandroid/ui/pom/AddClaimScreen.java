package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static ru.iteco.fmhandroid.ui.util.GeneralModules.childAtPosition;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.core.IsInstanceOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AddClaimScreen {
    private static final int customTitleTextViewID = R.id.custom_app_bar_sub_title_text_view;
    public static final int containerFragmentCreateClaimID = R.id.container_custom_app_bar_include_on_fragment_create_edit_claim;
    public static final int titleEditTextID = R.id.title_edit_text;


    public static final int dateInPlanTextInputID = R.id.date_in_plan_text_input_edit_text;

    public static final int timeInPlanTextInputID = R.id.time_in_plan_text_input_edit_text;

    public static final int descriptionEditTextID = R.id.description_edit_text;

    public static final int saveButtonID = R.id.save_button;

    public static final int executorDropMenuID = R.id.executor_drop_menu_auto_complete_text_view;

    public static void checkingTextInViewOnTitle() {
        Allure.step("Проверка текста \"Заявки\"");

        onView(allOf(withId(customTitleTextViewID), withText("Заявки"),
                withParent(allOf(withId(containerFragmentCreateClaimID),
                        withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                isDisplayed()))
                .check(matches(withText("Заявки")));
    }

    public static void inputTextInFiledTitle(String text) {
        Allure.step("Ввод текста в поле \"Тема\": " + text);
        onView(allOf(withId(titleEditTextID),
                isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public static void inputTextInFiledPerformer(String text) {
        Allure.step("Ввод текста в поле \"Исполнитель\": " + text);
        onView(allOf(withId(executorDropMenuID),
                isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public static void setDateOnDateFiled(String date) {
        Allure.step("Ввод текста в поле \"Дата\": " + date);
        onView(allOf(withId(dateInPlanTextInputID),
                isDisplayed()))
                .perform(replaceText(date));

    }

    public static void setTimeOnTimeFiled(String time) {
        Allure.step("Ввод текста в поле \"Время\": " + time);
        onView(allOf(withId(timeInPlanTextInputID),
                isDisplayed()))
                .perform(replaceText(time));
    }

    public static void inputTextInFiledDescription(String text) {
        Allure.step("Ввод текста в поле \"Описание\": " + text);
        onView(allOf(withId(descriptionEditTextID),
                isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public static void clickSaveButton() {
        Allure.step("Нажатие \"Сохранить\"");
        onView(allOf(withId(saveButtonID)))
                .perform(scrollTo(), click());
    }

    public static void checkTextInFiled(String text) {
        Allure.step("Проверка текста: <" + text + "> в поле: " + titleEditTextID);
        onView(allOf(withId(titleEditTextID), isDisplayed()))
                .check(matches(withText(text)));
    }

}
