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
    AddNewsScreen addNewsScreen = new AddNewsScreen();
    private final int newsDateStartTextInputID = R.id.news_item_publish_date_start_text_input_edit_text;
    private final int newsDateEndTextInputID = R.id.news_item_publish_date_end_text_input_edit_text;
    private final int filterNewsInactiveMaterialCheckBox = R.id.filter_news_inactive_material_check_box;
    private final int filterNewsActiveMaterialCheckBox = R.id.filter_news_active_material_check_box;
    private final int filterButton = R.id.filter_button;

    public void inputTextInFiledType(String text) {
        onView(
                allOf(withId(addNewsScreen.getNewsItemCategoryID()),
                        isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public void setDateOnStartDateFiled(String date) {
        onView(allOf(withId(newsDateStartTextInputID),
                isDisplayed()))
                .perform(replaceText(date));

    }

    public void setDateOnEndDateFiled(String date) {
        onView(allOf(withId(newsDateEndTextInputID),
                isDisplayed()))
                .perform(replaceText(date));

    }

    public void clickInactiveCheckBox() {
        onView(allOf(withId(filterNewsInactiveMaterialCheckBox), withText("Не активна"),
                isDisplayed()))
                .perform(click());
    }

    public void clickActiveCheckBox() {
        onView(allOf(withId(filterNewsActiveMaterialCheckBox), withText("Активна"),
                isDisplayed()))
                .perform(click());
    }

    public void clickFilterButton() {
        onView(allOf(withId(filterButton), withText("Фильтровать"),
                isDisplayed()))
                .perform(click());
    }
}
