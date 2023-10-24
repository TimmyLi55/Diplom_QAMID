package ru.iteco.fmhandroid.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
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
import ru.iteco.fmhandroid.ui.util.TestAuthData;

@RunWith(AllureAndroidJUnit4.class)

public class NewsTests {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    public static final int ListRVID = R.id.news_list_recycler_view;

    @Before
    public void authorisationAndGoToNews() {
        authorizationScreen.checkAuthorisationAndLogout();
        authorizationScreen.authorisation(testAuthData.getValidLogin(), testAuthData.getValidPassword());
        mainScreen.goToNews();
    }


    AddNewsFilterScreen addNewsFilterScreen = new AddNewsFilterScreen();
    AddNewsScreen addNewsScreen = new AddNewsScreen();
    AuthorizationScreen authorizationScreen = new AuthorizationScreen();

    MainScreen mainScreen = new MainScreen();
    NewsScreen newsScreen = new NewsScreen();
    GeneralModules generalModules = new GeneralModules();
    CustomMatchers customMatchers = new CustomMatchers();
    TestAuthData testAuthData = new TestAuthData();

    String currentDate = generalModules.getDateTime("date");
    String currentTime = generalModules.getDateTime("time");
    String endDate = "01.01.2001";
    String endTime = "00:00";
    String category = "Объявление";
    String titleTestNews = "Тестовая новость";
    String titleTestDescription = "Тестовое описание";
    String titleNewsElapsedDate = "Новость с прошедшей датой";
    String titleNewsElapsedTime = "Новость с прошедшим временем";

    String titleNewsFilter = "Тестирование фильтрации";

    String titleNewSorted = "Тестирование сортировки";

    @Test
    @DisplayName("Создание пустой новости")
    public void creatingEmptyNews() {

        newsScreen.clickEditMenuButton();
        newsScreen.clickAddNews();
        addNewsScreen.clickSaveButton();
        customMatchers.checkToast(addNewsScreen.getToastEmptyFiled());

    }

    @Test
    @DisplayName("Создание валидной новости категории \"Объявление\"")
    public void creatingValidNews() {

        newsScreen.creatingNews(category, titleTestNews, currentDate, currentTime, titleTestDescription);

    }

    @Test
    @DisplayName("Создание новости с прошедшей датой")
    public void creatingNewsWithEndDate() {

        newsScreen.clickEditMenuButton();
        newsScreen.clickAddNews();
        addNewsScreen.inputTextInFiledType(category);
        addNewsScreen.inputTextInFiledTitle(titleNewsElapsedDate + " " + endDate + " " + currentTime);
        addNewsScreen.setDateOnDateFiled(endDate);
        customMatchers.checkToast(addNewsScreen.getToastElapsedDate());


    }

    @Test
    @DisplayName("Создание новости с прошедшим временем")
    public void creatingNewsWithEndTime() {

        newsScreen.clickEditMenuButton();
        newsScreen.clickAddNews();
        addNewsScreen.inputTextInFiledType(category);
        addNewsScreen.inputTextInFiledTitle(titleNewsElapsedTime + " " + currentDate + " " + endTime);
        addNewsScreen.setTimeOnTimeFiled(endTime);
        customMatchers.checkToast(addNewsScreen.getToastElapsedTime());


    }


    @Test
    @DisplayName("Фильтрация созданной новости с категорией \"Объявление\" в панели управления")
    public void filteringNewsInControlPanel() {

        newsScreen.creatingNews(category, titleNewsFilter, currentDate, currentTime, titleTestDescription);
        newsScreen.clickFilterButton();
        addNewsFilterScreen.inputTextInFiledType(category);
        addNewsFilterScreen.setDateOnStartDateFiled(currentDate);
        addNewsFilterScreen.setDateOnEndDateFiled(currentDate);
        addNewsFilterScreen.clickInactiveCheckBox();
        addNewsFilterScreen.clickFilterButton();
        generalModules.scrollByRecViewWithText(newsScreen.getNewsListRecyclerViewID(), titleNewsFilter + " " + currentDate + " " + currentTime);

    }

    @Test
    @DisplayName("Сортировка новости, фокус вверху списка")
    public void sortingNewsFocusTop() throws InterruptedException {
        String title = titleNewSorted + " " + currentDate + " " + currentTime;
        newsScreen.creatingNews(category, titleNewSorted, currentDate, currentTime, titleTestDescription);
        mainScreen.clickMainMenu();
        mainScreen.clickContentMenuNews();
        newsScreen.checkingTextInViewAtTitle();
        generalModules.scrollByRecViewWithText(newsScreen.getNewsListRecyclerViewID(), title);
        customMatchers.checkTextAtPosition(title, 0);
        newsScreen.clickSortButton();
        newsScreen.checkingTextAtLastPositionOfNewsList(title, mActivityScenarioRule);
        newsScreen.swipeUpScreen(newsScreen.getNewsListRecyclerViewID());
        newsScreen.checkTextIsInvisible(title);

    }
}
