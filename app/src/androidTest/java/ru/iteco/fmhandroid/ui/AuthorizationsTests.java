package ru.iteco.fmhandroid.ui;


import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.util.CustomMatchers;
import ru.iteco.fmhandroid.ui.util.TestAuthData;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

@RunWith(AllureAndroidJUnit4.class)

public class AuthorizationsTests {


    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    @Before
    public void checkLogout(){
        authorizationScreen.checkAuthorisationAndLogout();
    }

    AuthorizationScreen authorizationScreen = new AuthorizationScreen();
    CustomMatchers customMatchers = new CustomMatchers();
    ViewActionWait viewActionWait = new ViewActionWait();
    TestAuthData testAuthData = new TestAuthData();


    @Test
    @DisplayName("Валидный логин и пароль")
    public void validLoginAndPassword() {

        authorizationScreen.authorisation(testAuthData.getValidLogin(), testAuthData.getValidPassword());

    }

    @Test
    @DisplayName("Пустой логин и пароль")
    public void emptyLoginAndPassword() {

        viewActionWait.waitView(authorizationScreen.getButtonLoginID(), authorizationScreen.getWaitLoadTimer());
        authorizationScreen.clickLoginButton();
        customMatchers.checkToast(authorizationScreen.getToastByEmpty());

    }

    @Test
    @DisplayName("Неверный логин")
    public void invalidLogin() {

        authorizationScreen.inputOnFieldLogin(testAuthData.getInvalidLogin());
        authorizationScreen.inputOnFieldPassword(testAuthData.getValidPassword());
        authorizationScreen.clickLoginButton();
        customMatchers.checkToast(authorizationScreen.getToastByErrorLoginPass());
    }

    @Test
    @DisplayName("Неверный пароль")
    public void invalidPass() {

        authorizationScreen.inputOnFieldLogin(testAuthData.getInvalidLogin());
        authorizationScreen.inputOnFieldPassword(testAuthData.getInvalidPassword());
        authorizationScreen.clickLoginButton();
        customMatchers.checkToast(authorizationScreen.getToastByErrorLoginPass());
    }
}
