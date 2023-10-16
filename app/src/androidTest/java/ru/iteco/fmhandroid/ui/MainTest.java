package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
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
import ru.iteco.fmhandroid.ui.util.Rules;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

@RunWith(AllureAndroidJUnit4.class)

public class MainTest extends Rules {
    @Before
    public void authorisation() {
        AuthorizationScreen.authorisation(AuthorizationsTests.getValidLogin(), AuthorizationsTests.getValidPassword());
    }


    @Test
    @DisplayName("Количество новостей на экране")
    public void numberOfNewsOnScreen() {

        ViewActionWait.waitView(MainScreen.getNewsListRecViewID(), 50000);
        CustomMatchers.checkItemCountRV(MainScreen.getNewsListRecViewID(), 3);


    }

    @Test
    @DisplayName("Количество заявок на экране")
    public void numberOfClaimsOnScreen() {

        ViewActionWait.waitView(MainScreen.getNewsListRecViewID(), 50000);
        MainScreen.swipeUpScreen();
        CustomMatchers.checkItemCountRV(MainScreen.getClaimListRecViewID(), 6);
    }

    @Test
    @DisplayName("Свернуть вкладку Новости")
    public void collapseNewsTab() {

        MainScreen.clickCollapseNewsButton();
    }

    @Test
    @DisplayName("Свернуть вкладку Заявки")
    public void collapseClaimsTab() {

        MainScreen.clickCollapseClaimButton();
    }

    @Test
    @DisplayName("Переход во все новости с главного экрана")
    public void gotoAllNewsFromHomeScreen() {

        MainScreen.clickButtonAllNews();
        ViewActionWait.waitView(NewsScreen.getContainerListNewsID(), 20000);
        NewsScreen.checkingTextInViewAtTitle();
    }

    @Test
    @DisplayName("Переход во все заявки с главного экрана")
    public void gotoAllClaimsFromHomeScreen() {

        MainScreen.clickButtonAllClaims();
        ViewActionWait.waitView(ClaimsScreen.getContainerListClaimsID(), 20000);
        ClaimsScreen.checkingTextInViewAtTitle();
    }

    @Test
    @DisplayName("Переход к созданию заявки с главного экрана")
    public void goToAddClaimToTheMainScreen() {

        MainScreen.clickButtonAddNewClaim();
        AddClaimScreen.checkingTextInViewOnTitle();
    }
}


