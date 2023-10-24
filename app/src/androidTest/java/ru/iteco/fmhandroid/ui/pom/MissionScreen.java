package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class MissionScreen {
    private final int ourMissionTitleID = R.id.our_mission_title_text_view;
    private final int ourMissionCardList = R.id.our_mission_item_list_recycler_view;

    private final String textMission = "Главное - жить любя";

    public String getTextMission() {
        return textMission;
    }

    public int getOurMissionTitleID() {

        return ourMissionTitleID;
    }

    public int getOurMissionCardList() {

        return ourMissionCardList;
    }

    public void checkingTextInView(int view, String text) {
        Allure.step("Проверка открытия страницы \"Наша миссия\"");
        onView(allOf(withId(view), withText(text),
                isDisplayed())).check(matches(withText(text)));
    }

}
