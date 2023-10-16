package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AddNewsFilterScreen {

    public static final int newsDateStartTextInputID = R.id.news_item_publish_date_start_text_input_edit_text;
    public static final int newsDateEndTextInputID = R.id.news_item_publish_date_end_text_input_edit_text;

    public static final int filterNewsInactiveMaterialCheckBox = R.id.filter_news_inactive_material_check_box;
    public static final int filterNewsActiveMaterialCheckBox = R.id.filter_news_active_material_check_box;

    public static final int filterButton = R.id.filter_button;

    public static void inputTextInFiledType(String text) {
        onView(
                allOf(withId(AddNewsScreen.newsItemCategoryID),
                        isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public static void setDateOnStartDateFiled(String date) {
        onView(allOf(withId(newsDateStartTextInputID),
                isDisplayed()))
                .perform(replaceText(date));

    }

    public static void setDateOnEndDateFiled(String date) {
        onView(allOf(withId(newsDateEndTextInputID),
                isDisplayed()))
                .perform(replaceText(date));

    }

    public static void clickInactiveCheckBox() {
        onView(allOf(withId(filterNewsInactiveMaterialCheckBox), withText("Не активна"),
                isDisplayed()))
                .perform(click());
    }

    public static void clickActiveCheckBox() {
        onView(allOf(withId(filterNewsActiveMaterialCheckBox), withText("Активна"),
                isDisplayed()))
                .perform(click());
    }

    public static void clickFilterButton() {
        onView(allOf(withId(filterButton), withText("Фильтровать"),
                isDisplayed()))
                .perform(click());
    }
}
