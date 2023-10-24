package ru.iteco.fmhandroid.ui;


import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AddClaimScreen;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.ClaimsScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;
import ru.iteco.fmhandroid.ui.pom.NewsScreen;
import ru.iteco.fmhandroid.ui.util.CustomMatchers;
import ru.iteco.fmhandroid.ui.util.TestAuthData;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

@RunWith(AllureAndroidJUnit4.class)

public class MainTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void authorisation() {
        authorizationScreen.checkAuthorisationAndLogout();
        authorizationScreen.authorisation(testAuthData.getValidLogin(), testAuthData.getValidPassword());
    }

    AddClaimScreen addClaimScreen = new AddClaimScreen();
    AuthorizationScreen authorizationScreen = new AuthorizationScreen();

    ClaimsScreen claimsScreen = new ClaimsScreen();
    MainScreen mainScreen = new MainScreen();
    NewsScreen newsScreen = new NewsScreen();
    CustomMatchers customMatchers = new CustomMatchers();
    ViewActionWait viewActionWait = new ViewActionWait();
    TestAuthData testAuthData = new TestAuthData();

    private final int countNews = 3;
    private final int countClaims = 6;

    @Test
    @DisplayName("Количество новостей на экране")
    public void numberOfNewsOnScreen() {

        viewActionWait.waitView(mainScreen.getNewsListRecViewID(), mainScreen.getWaitLoadTimer());
        customMatchers.checkItemCountRV(mainScreen.getNewsListRecViewID(), countNews);


    }

    @Test
    @DisplayName("Количество заявок на экране")
    public void numberOfClaimsOnScreen() {

        viewActionWait.waitView(mainScreen.getNewsListRecViewID(), mainScreen.getWaitLoadTimer());
        mainScreen.swipeUpScreen();
        customMatchers.checkItemCountRV(mainScreen.getClaimListRecViewID(), countClaims);
    }

    @Test
    @DisplayName("Свернуть вкладку Новости")
    public void collapseNewsTab() {

        mainScreen.clickCollapseNewsButton();
    }

    @Test
    @DisplayName("Свернуть вкладку Заявки")
    public void collapseClaimsTab() {

        mainScreen.clickCollapseClaimButton();
    }

    @Test
    @DisplayName("Переход во все новости с главного экрана")
    public void gotoAllNewsFromHomeScreen() {

        mainScreen.clickButtonAllNews();
        viewActionWait.waitView(newsScreen.getContainerListNewsID(), newsScreen.getWaitLoadTimer());
        newsScreen.checkingTextInViewAtTitle();
    }

    @Test
    @DisplayName("Переход во все заявки с главного экрана")
    public void gotoAllClaimsFromHomeScreen() {

        mainScreen.clickButtonAllClaims();
        viewActionWait.waitView(claimsScreen.getContainerListClaimsID(), claimsScreen.getWaitLoadTimer());
        claimsScreen.checkingTextInViewAtTitle();
    }

    @Test
    @DisplayName("Переход к созданию заявки с главного экрана")
    public void goToAddClaimToTheMainScreen() {

        mainScreen.clickButtonAddNewClaim();
        addClaimScreen.checkingTextInViewOnTitle();
    }
}


