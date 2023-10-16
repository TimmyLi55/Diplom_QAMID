package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.util.GeneralModules.childAtPosition;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;


public class AuthorizationScreen {
    private static final int buttonLoginID = R.id.enter_button;
    private static final int fieldLoginID = R.id.login_text_input_layout;
    private static final int fieldPasswordID = R.id.password_text_input_layout;

    public static int getButtonLoginID() {
        return buttonLoginID;
    }

    public static void clickLoginButton() {
        Allure.step("Нажатие кнопки \"Войти\"");
        onView(withId(buttonLoginID))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public static void authorisation(String login, String pass) {
        Allure.step("Авторизация пользователя с логином <" + login + "> и паролем <" + pass + ">");
        AuthorizationScreen.inputOnFieldLogin(login);
        AuthorizationScreen.inputOnFieldPassword(pass);
        AuthorizationScreen.clickLoginButton();
        ViewActionWait.waitView(MainScreen.getNewsList(), 50000);
        MainScreen.checkingTextInView(MainScreen.getNewsList(), "Новости");
        MainScreen.checkingTextInView(MainScreen.getClaimList(), "Заявки");
    }

    public static void inputOnFieldLogin(String login) {
        Allure.step("Ввод текста в поле \"Логин\"");
        ViewActionWait.waitView(fieldLoginID, 50000);
        Allure.step("Фокусировка на объекте " + fieldLoginID);
        onView(withId(fieldLoginID))
                .check(matches(isDisplayed()))
                .perform(click());
        Allure.step("Ввод текста " + login + " в объект " + fieldLoginID);
        onView(allOf(childAtPosition(
                childAtPosition(
                        withId(fieldLoginID),
                        0),
                0)))
                .perform(replaceText(login), closeSoftKeyboard());
    }

    public static void inputOnFieldPassword(String pass) {
        Allure.step("Ввод текста в поле \"Пароль\"");
        Allure.step("Фокусировка на объекте " + fieldPasswordID);
        onView(withId(fieldPasswordID))
                .check(matches(isDisplayed()))
                .perform(click());
        Allure.step("Ввод текста " + pass + " в объект " + fieldLoginID);
        onView(allOf(childAtPosition(
                childAtPosition(
                        withId(fieldPasswordID),
                        0),
                0)))
                .perform(replaceText(pass), closeSoftKeyboard());
    }
}
