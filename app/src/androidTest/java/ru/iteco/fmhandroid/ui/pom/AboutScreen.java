package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AboutScreen {
    private static final int infoLabelID = R.id.about_company_info_label_text_view;
    private static final int privacyPolicyLinkID = R.id.about_privacy_policy_value_text_view;
    private static final int userAgreementLinkID = R.id.about_terms_of_use_value_text_view;

    public static void clickPolicyLink() {

        Allure.step("Нажатие по ссылке политики конфиденсальности");
        onView(withId(privacyPolicyLinkID))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public static void clickUserAgreement() {

        Allure.step("Нажатие по ссылке пользовательского соглашения");
        onView(withId(userAgreementLinkID))
                .check(matches(isDisplayed()))
                .perform(click());

    }

    public static void checkIntentLink(String link) {
        Allure.step("Проверка перехода по ссылке " + link);
        intended(hasData(link));
    }

    public static void checkingTextInViewOnIfoLabel() {
        Allure.step("Проверка открывшейся страницы");
        onView(
                allOf(withText("© Айтеко, 2022"), withId(infoLabelID),
                        isDisplayed())).check(matches(withText("© Айтеко, 2022")));
    }
}