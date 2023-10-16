package ru.iteco.fmhandroid.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.pom.AddNewsFilterScreen;
import ru.iteco.fmhandroid.ui.pom.AddNewsScreen;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;
import ru.iteco.fmhandroid.ui.pom.NewsScreen;
import ru.iteco.fmhandroid.ui.util.CustomMatchers;
import ru.iteco.fmhandroid.ui.util.GeneralModules;
import ru.iteco.fmhandroid.ui.util.Rules;

@RunWith(AllureAndroidJUnit4.class)

public class NewsTests extends Rules {

    public static final int ListRVID = R.id.news_list_recycler_view;

    @Before
    public void authorisationAndGoToNews() {
        AuthorizationScreen.authorisation(AuthorizationsTests.getValidLogin(), AuthorizationsTests.getValidPassword());
        MainScreen.goToNews();
    }

    static String currentDate = GeneralModules.getDateTime("date");
    static String currentTime = GeneralModules.getDateTime("time");

    static String endDate = "01.01.2001";
    static String endTime = "00:00";

    static String category = "Объявление";

    @Test
    @DisplayName("Создание пустой новости")
    public void creatingEmptyNews() {

        NewsScreen.clickEditMenuButton();
        NewsScreen.clickAddNews();
        AddNewsScreen.clickSaveButton();
        CustomMatchers.checkToast("Заполните пустые поля");

    }

    @Test
    @DisplayName("Создание валидной новости категории \"Объявление\"")
    public void creatingValidNews() {

        NewsScreen.creatingNews(category, "Тестовая новость", currentDate, currentTime, "Тестовое описание");

    }

    @Test
    @DisplayName("Создание новости с прошедшей датой")
    public void creatingNewsWithEndDate() {

        NewsScreen.clickEditMenuButton();
        NewsScreen.clickAddNews();
        AddNewsScreen.inputTextInFiledType(category);
        AddNewsScreen.inputTextInFiledTitle("Новость с прошедшей датой" + " " + endDate + " " + currentTime);
        AddNewsScreen.setDateOnDateFiled(endDate);
        CustomMatchers.checkToast("Выбор прошедшей даты невозможен!");


    }

    @Test
    @DisplayName("Создание новости с прошедшим временем")
    public void creatingNewsWithEndTime() {

        NewsScreen.clickEditMenuButton();
        NewsScreen.clickAddNews();
        AddNewsScreen.inputTextInFiledType(category);
        AddNewsScreen.inputTextInFiledTitle("Новость с прошедшим временем" + " " + currentDate + " " + endTime);
        AddNewsScreen.setTimeOnTimeFiled(endTime);
        CustomMatchers.checkToast("Выбор прошедшего времени невозможен!");


    }


    @Test
    @DisplayName("Фильтрация созданной новости с категорией \"Объявление\" в панели управления")
    public void filteringNewsInControlPanel() {

        NewsScreen.creatingNews(category, "Фильтрация", currentDate, currentTime, "Фильтрация описание");
        NewsScreen.clickFilterButton();
        AddNewsFilterScreen.inputTextInFiledType(category);
        AddNewsFilterScreen.setDateOnStartDateFiled(currentDate);
        AddNewsFilterScreen.setDateOnEndDateFiled(currentDate);
        AddNewsFilterScreen.clickInactiveCheckBox();
        AddNewsFilterScreen.clickFilterButton();
        GeneralModules.scrollByRecViewWithText(NewsScreen.getNewsListRecyclerViewID(), "Фильтрация" + " " + currentDate + " " + currentTime);

    }

    @Test
    @DisplayName("Сортировка новости, фокус вверху списка")
    public void sortingNewsFocusTop() throws InterruptedException {
        String title = "Тестирование сортировки" + " " + currentDate + " " + currentTime;
        NewsScreen.creatingNews(category, "Тестирование сортировки", currentDate, currentTime, "Тестовое описание");
        MainScreen.clickMainMenu();
        MainScreen.clickContentMenuNews();
        NewsScreen.checkingTextInViewAtTitle();
        GeneralModules.scrollByRecViewWithText(NewsScreen.getNewsListRecyclerViewID(), title);
        CustomMatchers.checkTextAtPosition(title, 0);
        NewsScreen.clickSortButton();
        NewsScreen.checkingTextAtLastPositionOfNewsList(title, mActivityScenarioRule);
        NewsScreen.swipeUpScreen(NewsScreen.getNewsListRecyclerViewID());
        NewsScreen.checkTextIsInvisible(title);

    }
}
