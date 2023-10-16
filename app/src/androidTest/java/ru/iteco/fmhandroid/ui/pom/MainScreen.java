package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static ru.iteco.fmhandroid.ui.util.GeneralModules.childAtPosition;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

public class MainScreen {
    private static final int newsListID = R.id.container_list_news_include_on_fragment_main;
    private static final int claimListID = R.id.container_list_claim_include_on_fragment_main;

    private static final int ourMissionID = R.id.our_mission_image_button;

    private static final int mainMenuID = R.id.main_menu_image_button;
    private static final int newsListRecViewID = R.id.news_list_recycler_view;
    private static final int claimListRecViewID = R.id.claim_list_recycler_view;

    private static final int expandMaterialButtonID = R.id.expand_material_button;
    public static final int allNewsButtonID = R.id.all_news_text_view;
    public static final int allClaimsButtonID = R.id.all_claims_text_view;

    public static final int addNewClaimButtonID = R.id.add_new_claim_material_button;

    public static int getExpandMaterialButtonID() {
        return expandMaterialButtonID;
    }

    public static int getClaimListRecViewID() {

        return claimListRecViewID;
    }

    public static int getNewsListRecViewID() {

        return newsListRecViewID;
    }

    public static int getNewsList() {

        return newsListID;
    }

    public static int getClaimList() {

        return claimListID;
    }

    public static void checkingTextInView(int view, String text) {
        Allure.step("Проверка текста <" + text + "> в представлении <" + view + ">");
        onView(
                allOf(withText(text),
                        withParent(withParent(withId(view))),
                        isDisplayed())).check(matches(withText(text)));

    }

    public static void clickMainMenu() {
        Allure.step("Нажатие кнопки \"Главное меню\"");
        onView(withId(mainMenuID))
                .check(matches(isDisplayed()))
                .perform(click());

    }

    public static void clickOurMission() {
        Allure.step("Нажатие кнопки \"Наша миссия\"");
        onView(withId(ourMissionID))
                .check(matches(isDisplayed()))
                .perform(click());


    }

    public static void clickContentMenuAboutApp() {
        Allure.step("Нажатие кнопки \"О приложении\"");
        ViewInteraction content = onView(allOf(withId(android.R.id.title), withText("О приложении")));
        content.check(matches(isDisplayed())).perform(click());
    }

    public static void goToNews() {
        Allure.step("Переход в окно новостей");
        ViewActionWait.waitView(MainScreen.getNewsListRecViewID(), 50000);
        MainScreen.clickMainMenu();
        MainScreen.clickContentMenuNews();
        NewsScreen.checkingTextInViewAtTitle();
    }

    public static void goToClaims() {
        Allure.step("Переход в окно заявок");
        ViewActionWait.waitView(MainScreen.getNewsListRecViewID(), 50000);
        MainScreen.clickMainMenu();
        MainScreen.clickContentMenuClaims();
        ClaimsScreen.checkingTextInViewAtTitle();
        ViewActionWait.waitView(ClaimsScreen.getClaimListRecyclerViewID(), 50000);
    }

    public static void clickContentMenuNews() {
        Allure.step("Нажатие кнопки \"Новости\"");
        ViewInteraction content = onView(allOf(withId(android.R.id.title), withText("Новости")));
        content.check(matches(isDisplayed())).perform(click());
    }

    public static void clickContentMenuClaims() {
        Allure.step("Нажатие кнопки \"Боковое меню\"");
        try {
            ViewInteraction content = onView(allOf(withId(android.R.id.title), withText("Заявки")));
            content.check(matches(isDisplayed())).perform(click());

        } catch (NoMatchingViewException e) {
            System.out.println("Объект" + android.R.id.title + " с текстом" + "Новости" + "не найден на экране");
        }
    }

    public static void scrollByText(int id, String text) {
        onView(withId(id))
                .perform(scrollTo(hasDescendant(withText(text))))
                .check(matches(isDisplayed()));
    }

    public static void swipeUpScreen() {
        Allure.step("Свайп по экрану вверх");
        onView(withId(MainScreen.getNewsListRecViewID())).perform(swipeUp());
    }

    public static void clickCollapseNewsButton() {
        Allure.step("Свернуть вкладку новости");
        onView(allOf(withId(expandMaterialButtonID),
                childAtPosition(childAtPosition(withId(newsListID), 0), 4), isDisplayed()))
                .perform(click());
    }

    public static void clickCollapseClaimButton() {
        Allure.step("Свернуть вкладку заявки");
        onView(allOf(withId(expandMaterialButtonID),
                childAtPosition(childAtPosition(withId(claimListID), 0), 3), isDisplayed()))
                .perform(click());
    }

    public static void clickButtonAllNews() {
        Allure.step("Нажатие кнопки \"Все новости\"");
        onView(allOf(withId(allNewsButtonID), withText("Все новости"),
                childAtPosition(
                        allOf(withId(newsListID),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        1),
                isDisplayed()))
                .perform(click());

    }

    public static void clickButtonAllClaims() {
        Allure.step("Нажатие кнопки \"Все заявки\"");
        onView(allOf(withId(allClaimsButtonID), withText("Все заявки"),
                childAtPosition(
                        allOf(withId(claimListID),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1)),
                        1),
                isDisplayed()))
                .perform(click());

    }

    public static void clickButtonAddNewClaim() {
        Allure.step("Нажатие кнопки \"Новая заявка\"");

        onView(allOf(withId(addNewClaimButtonID), withContentDescription("Кнопка добавления новой заявки"),
                childAtPosition(
                        childAtPosition(
                                withId(claimListID),
                                0),
                        2),
                isDisplayed()))
                .perform(click());
    }
}

