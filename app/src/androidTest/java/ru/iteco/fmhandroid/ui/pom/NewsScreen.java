package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
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

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import java.util.List;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.adapter.NewsListAdapter;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.util.GeneralModules;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;
import ru.iteco.fmhandroid.ui.viewdata.NewsViewData;

public class NewsScreen {
    private static final int containerListNewsID = R.id.container_list_news_include;
    public static final int editNewsButton = R.id.edit_news_material_button;
    public static final int addNewsImageView = R.id.add_news_image_view;
    public static final int newsListRecyclerViewID = R.id.news_list_recycler_view;
    public static final int filterNewsMaterialButton = R.id.filter_news_material_button;
    public static final int sortNewsMaterialButton = R.id.sort_news_material_button;

    public static int getContainerListNewsID() {

        return containerListNewsID;
    }

    public static int getNewsListRecyclerViewID() {

        return newsListRecyclerViewID;
    }

    public static void checkingTextInViewAtTitle() {
        Allure.step("Проверка перехода в окно \"Новости\"");
        onView(
                allOf(withText("Новости"),
                        withParent(withParent(withId(containerListNewsID))),
                        isDisplayed())).check(matches(withText("Новости")));

    }

    public static void clickEditMenuButton() {
        Allure.step("Нажатие панели управления новостями");
        onView(
                allOf(withId(editNewsButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(containerListNewsID),
                                        0),
                                3),
                        isDisplayed()))
                .perform(click());
    }

    public static void clickAddNews() {
        Allure.step("Нажатие на кнопку \"Добавить новость\"");
        onView(
                allOf(withId(addNewsImageView), withContentDescription("Кнопка добавления новости"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed()))
                .perform(click());
    }


    public static void creatingNews(String category, String titleName, String currentDate, String currentTime, String description) {
        //Allure.step("Создание новой новости категории <" + category + "> c наименованием <" + titleName + "> датой <" + currentDate + "> времинем <" + currentTime + "> и описанием <" + description + ">");
        NewsScreen.clickEditMenuButton();
        NewsScreen.clickAddNews();
        AddNewsScreen.inputTextInFiledType(category);
        AddNewsScreen.inputTextInFiledTitle(titleName + " " + currentDate + " " + currentTime);
        AddNewsScreen.setDateOnDateFiled(currentDate);
        AddNewsScreen.setTimeOnTimeFiled(currentTime);
        AddNewsScreen.inputTextInFiledDescription(description + " " + currentDate + " " + currentTime);
        AddNewsScreen.clickSaveButton();
        ViewActionWait.waitView(NewsScreen.getNewsListRecyclerViewID(), 20000);
        GeneralModules.scrollByRecViewWithText(NewsScreen.getNewsListRecyclerViewID(), titleName + " " + currentDate + " " + currentTime);
        GeneralModules.scrollByRecViewWithText(NewsScreen.getNewsListRecyclerViewID(), description + " " + currentDate + " " + currentTime);

    }

    public static void clickFilterButton() {
        Allure.step("Нажатие на кнопку \"Фильтр\"");
        onView(
                allOf(withId(filterNewsMaterialButton),
                        isDisplayed()))
                .perform(click());
    }

    public static void clickSortButton() throws InterruptedException {
        Allure.step("Нажатие на кнопку \"Сортировать\"");
        onView(
                allOf(withId(R.id.sort_news_material_button), withContentDescription("Кнопка сортировки списка новостей"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container_list_news_include),
                                        0),
                                1),
                        isDisplayed()))
                .perform(click());
        Thread.sleep(10000);
    }

    public static void checkingTextAtLastPositionOfNewsList(String title, ActivityScenarioRule<AppActivity> myactivity) {
        Allure.step("Проверка текста <" + title + "> на последней позиции списка");
        myactivity.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(NewsScreen.getNewsListRecyclerViewID());
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            int itemCountEnd = adapter.getItemCount();
            int lastItemPosition = itemCountEnd - 1;
            NewsListAdapter newsAdapter = (NewsListAdapter) adapter;
            List<NewsViewData> dataList = newsAdapter.getCurrentList();
            NewsViewData lastItem = dataList.get(lastItemPosition);
            String lastItemText = lastItem.getTitle();
            assert lastItemText.equals(title) : "Текст из " + title + " не совпадает с текстом " + lastItemText + " на позиции " + itemCountEnd;

        });
    }

    public static void swipeUpScreen(int ID) {
        Allure.step("Свайп экрана вверх");
        onView(withId(ID)).perform(ViewActions.swipeUp());
    }

    public static void checkTextIsInvisible(String text) {
        Allure.step("Проверка что текст <" + text + "> нет на экране");
        onView(withText(text)).check(doesNotExist());
    }
}

