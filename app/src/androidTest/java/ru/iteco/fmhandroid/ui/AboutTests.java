package ru.iteco.fmhandroid.ui;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AboutScreen;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;
import ru.iteco.fmhandroid.ui.util.TestAuthData;

@RunWith(AllureAndroidJUnit4.class)

public class AboutTests {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void authorisation() {
        authorizationScreen.checkAuthorisationAndLogout();
        authorizationScreen.authorisation(testAuthData.getValidLogin(), testAuthData.getValidPassword());
    }

    AboutScreen aboutScreen = new AboutScreen();
    AuthorizationScreen authorizationScreen = new AuthorizationScreen();
    MainScreen mainScreen = new MainScreen();
    TestAuthData testAuthData = new TestAuthData();

    @Test
    @DisplayName("Ссылка на политику конфиденциальности")
    public void intentLinkToPrivacyPolicy() {

        mainScreen.clickMainMenu();
        mainScreen.clickContentMenuAboutApp();
        aboutScreen.checkingTextInViewOnIfoLabel();
        Allure.step("Инициализация интента");
        Intents.init();
        aboutScreen.clickPolicyLink();
        aboutScreen.checkIntentLink(aboutScreen.getPrivacyPolicyLink());
        Allure.step("Деинициализация интента");
        Intents.release();
    }

    @Test
    @DisplayName("Ссылка на пользовательсоке соглашение")
    public void intentLinkToUserAgreement() {

        mainScreen.clickMainMenu();
        mainScreen.clickContentMenuAboutApp();
        aboutScreen.checkingTextInViewOnIfoLabel();
        Allure.step("Инициализация интента");
        Intents.init();
        aboutScreen.clickUserAgreement();
        aboutScreen.checkIntentLink(aboutScreen.getUserAgreementLink());
        Allure.step("Деинициализация интента");
        Intents.release();
    }
}
