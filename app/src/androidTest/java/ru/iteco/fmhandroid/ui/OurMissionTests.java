package ru.iteco.fmhandroid.ui;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AboutScreen;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;
import ru.iteco.fmhandroid.ui.pom.MissionScreen;
import ru.iteco.fmhandroid.ui.util.CustomMatchers;
import ru.iteco.fmhandroid.ui.util.GeneralModules;
import ru.iteco.fmhandroid.ui.util.Rules;

@RunWith(AllureAndroidJUnit4.class)

public class OurMissionTests extends Rules {
    @Before
    public void authorisation(){
        AuthorizationScreen.authorisation(AuthorizationsTests.getValidLogin(), AuthorizationsTests.getValidPassword());
    }
    @Test
    @DisplayName("Количество слоганов на экране")
    public void numberOfSlogansOnTheScreen() {

        MainScreen.clickOurMission();
        MissionScreen.checkingTextInView(MissionScreen.getOurMissionTitleID(), "Главное - жить любя");
        CustomMatchers.checkItemCountRV(MissionScreen.getOurMissionCardList(),8);


    }

}
