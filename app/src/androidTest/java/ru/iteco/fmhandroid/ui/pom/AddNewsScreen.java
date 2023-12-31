package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import static ru.iteco.fmhandroid.ui.util.GeneralModules.childAtPosition;

import android.view.View;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;

import org.hamcrest.core.IsInstanceOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AddNewsScreen {
    private final int customTitleTextViewID = R.id.custom_app_bar_sub_title_text_view;
    private final int containerFragmentCreateClaimID = R.id.container_custom_app_bar_include_on_fragment_create_edit_claim;
    private final int newsItemCategoryID = R.id.news_item_category_text_auto_complete_text_view;
    private final int newsItemCategoryTextInputID = R.id.news_item_category_text_input_layout;
    private final int newsTitleTextInputID = R.id.news_item_title_text_input_edit_text;
    private final int newsDateTextInputID = R.id.news_item_publish_date_text_input_edit_text;
    private final int newsTimeTextInputID = R.id.news_item_publish_time_text_input_edit_text;
    private final int saveButton = R.id.save_button;

    private final String toastEmptyFiled = "Заполните пустые поля";

    private final String toastElapsedDate = "Выбор прошедшей даты невозможен!";
    private final String toastElapsedTime = "Выбор прошедшего времени невозможен!";

    public String getToastElapsedTime() {
        return toastElapsedTime;
    }

    public String getToastElapsedDate() {
        return toastElapsedDate;
    }

    public String getToastEmptyFiled() {
        return toastEmptyFiled;
    }

    public int getNewsItemCategoryID() {
        return newsItemCategoryID;
    }

    public void clickSaveButton() {
        onView(
                allOf(withId(saveButton), withText("Сохранить"), withContentDescription("Сохранить"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                                        0),
                                6)))
                .perform(scrollTo(), click());
    }

    public void inputTextInFiledType(String text) {
        Allure.step("Ввод текста <" + text + "> в поле тип");
        onView(
                allOf(withId(newsItemCategoryID),
                        isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public void inputTextInFiledTitle(String text) {
        Allure.step("Ввод текста <" + text + "> в поле наименование");
        onView(
                allOf(withId(newsTitleTextInputID),
                        isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public void inputTextInFiledDescription(String text) {
        Allure.step("Ввод текста <" + text + "> в поле описание");
        onView(
                allOf(withId(R.id.news_item_description_text_input_edit_text),
                        isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }


    public void setDateOnDateFiled(String date) {
        Allure.step("Ввод текста <" + date + "> в поле дата");
        onView(allOf(withId(newsDateTextInputID),
                isDisplayed()))
                .perform(replaceText(date));

    }

    public void setTimeOnTimeFiled(String time) {
        Allure.step("Ввод текста <" + time + "> в поле время");
        onView(allOf(withId(newsTimeTextInputID),
                isDisplayed()))
                .perform(replaceText(time));
    }
}
