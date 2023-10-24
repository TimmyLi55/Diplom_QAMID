package ru.iteco.fmhandroid.ui;


import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;
import ru.iteco.fmhandroid.ui.pom.MissionScreen;
import ru.iteco.fmhandroid.ui.util.CustomMatchers;
import ru.iteco.fmhandroid.ui.util.TestAuthData;

@RunWith(AllureAndroidJUnit4.class)

public class OurMissionTests {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void authorisation() {
        authorizationScreen.checkAuthorisationAndLogout();
        authorizationScreen.authorisation(testAuthData.getValidLogin(), testAuthData.getValidPassword());
    }

    AuthorizationScreen authorizationScreen = new AuthorizationScreen();
    MainScreen mainScreen = new MainScreen();
    MissionScreen missionScreen = new MissionScreen();
    CustomMatchers customMatchers = new CustomMatchers();
    TestAuthData testAuthData = new TestAuthData();

    int countCard = 8;

    @Test
    @DisplayName("Количество слоганов на экране")
    public void numberOfSlogansOnTheScreen() {

        mainScreen.clickOurMission();
        missionScreen.checkingTextInView(missionScreen.getOurMissionTitleID(), missionScreen.getTextMission());
        customMatchers.checkItemCountRV(missionScreen.getOurMissionCardList(), countCard);

    }

}
