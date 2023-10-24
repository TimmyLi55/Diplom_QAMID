package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.util.GeneralModules.childAtPosition;

import android.view.View;

import org.hamcrest.core.IsInstanceOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;


public class AuthorizationScreen {
    MainScreen mainScreen = new MainScreen();
    ViewActionWait viewActionWait = new ViewActionWait();
    private final int navHostFragment = R.id.nav_host_fragment;
    private final int buttonLoginID = R.id.enter_button;
    private final int fieldLoginID = R.id.login_text_input_layout;
    private final int fieldPasswordID = R.id.password_text_input_layout;
    private final int waitLoadTimer = 50000;
    private final String toastByEmpty = "Логин и пароль не могут быть пустыми";
    private final String toastByErrorLoginPass = "Неверный логин или пароль";

    public int getButtonLoginID() {
        return buttonLoginID;
    }

    public String getToastByErrorLoginPass() {
        return toastByErrorLoginPass;
    }

    public String getToastByEmpty() {
        return toastByEmpty;
    }

    public int getWaitLoadTimer() {
        return waitLoadTimer;
    }

    public void clickLoginButton() {
        Allure.step("Нажатие кнопки \"Войти\"");
        onView(withId(buttonLoginID))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void authorisation(String login, String pass) {
        Allure.step("Авторизация пользователя с логином <" + login + "> и паролем <" + pass + ">");
        inputOnFieldLogin(login);
        inputOnFieldPassword(pass);
        clickLoginButton();
        viewActionWait.waitView(mainScreen.getNewsList(), mainScreen.getWaitLoadTimer());
        mainScreen.checkingTextInView(mainScreen.getNewsList(), "Новости");
        mainScreen.checkingTextInView(mainScreen.getClaimList(), "Заявки");
    }

    public void inputOnFieldLogin(String login) {
        Allure.step("Ввод текста в поле \"Логин\"");
        viewActionWait.waitView(fieldLoginID, waitLoadTimer);
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

    public void inputOnFieldPassword(String pass) {
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

    public boolean checkAuthorisationScreen() {
            boolean loginPage = true;
            try {
                viewActionWait.waitView(fieldLoginID, 5000);
            } catch(RuntimeException exception) {
                loginPage = false;
            }
            return loginPage;
        }

//        onView(allOf(withId(R.id.enter_button), withText("ВОЙТИ"), withContentDescription("Сохранить"),
//                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
//                        isDisplayed()))
//                .check(matches(isDisplayed()));
//        return true;
//    }

    public void checkAuthorisationAndLogout(){
        viewActionWait.waitView(R.id.container_custom_app_bar_include_on_fragment_main, waitLoadTimer);
        if(!checkAuthorisationScreen()){
            mainScreen.clickButtonLogout();
        }
    }
}
