package ru.iteco.fmhandroid.ui;


import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.util.CustomMatchers;
import ru.iteco.fmhandroid.ui.util.Rules;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

@RunWith(AllureAndroidJUnit4.class)

public class AuthorizationsTests extends Rules {
    private static String validLogin = "login2";
    private static String invalidLogin = "login1";
    private static String validPassword = "password2";
    private static String invalidPassword = "password1";

    public static String getValidLogin() {
        return validLogin;
    }

    public static String getValidPassword() {
        return validPassword;
    }

    @Test
    @DisplayName("Валидный логин и пароль")
    public void validLoginAndPassword() {

        AuthorizationScreen.authorisation(validLogin, validPassword);

    }

    @Test
    @DisplayName("Пустой логин и пароль")
    public void emptyLoginAndPassword() {

        ViewActionWait.waitView(AuthorizationScreen.getButtonLoginID(), 10000);
        AuthorizationScreen.clickLoginButton();
        CustomMatchers.checkToast("Логин и пароль не могут быть пустыми");

    }

    @Test
    @DisplayName("Неверный логин")
    public void invalidLogin() {

        AuthorizationScreen.inputOnFieldLogin(invalidLogin);
        AuthorizationScreen.inputOnFieldPassword(validPassword);
        AuthorizationScreen.clickLoginButton();
        CustomMatchers.checkToast("Неверный логин или пароль");
    }

    @Test
    @DisplayName("Неверный пароль")
    public void invalidPass() {

        AuthorizationScreen.inputOnFieldLogin(validLogin);
        AuthorizationScreen.inputOnFieldPassword(invalidPassword);
        AuthorizationScreen.clickLoginButton();
        CustomMatchers.checkToast("Неверный логин или пароль");
    }
}
