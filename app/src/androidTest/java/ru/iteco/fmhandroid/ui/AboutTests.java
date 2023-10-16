package ru.iteco.fmhandroid.ui;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AboutScreen;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;
import ru.iteco.fmhandroid.ui.util.Rules;

@RunWith(AllureAndroidJUnit4.class)

public class AboutTests extends Rules {

    @Before
    public void authorisation() {
        AuthorizationScreen.authorisation(AuthorizationsTests.getValidLogin(), AuthorizationsTests.getValidPassword());
    }

    private final String privacyPolicyLink = "https://vhospice.org/#/privacy-policy";
    private final String userAgreementLink = "https://vhospice.org/#/terms-of-use";

    @Test
    @DisplayName("Ссылка на политику конфиденциальности")
    public void intentLinkToPrivacyPolicy() {

        MainScreen.clickMainMenu();
        MainScreen.clickContentMenuAboutApp();
        AboutScreen.checkingTextInViewOnIfoLabel();
        Allure.step("Инициализация интента");
        Intents.init();
        AboutScreen.clickPolicyLink();
        AboutScreen.checkIntentLink(privacyPolicyLink);
        Allure.step("Деинициализация интента");
        Intents.release();
    }

    @Test
    @DisplayName("Ссылка на пользовательсоке соглашение")
    public void intentLinkToUserAgreement() {

        MainScreen.clickMainMenu();
        MainScreen.clickContentMenuAboutApp();
        AboutScreen.checkingTextInViewOnIfoLabel();
        Allure.step("Инициализация интента");
        Intents.init();
        AboutScreen.clickUserAgreement();
        AboutScreen.checkIntentLink(userAgreementLink);
        Allure.step("Деинициализация интента");
        Intents.release();
    }
}
