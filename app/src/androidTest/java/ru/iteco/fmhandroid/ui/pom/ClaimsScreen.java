package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Assert;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.adapter.ClaimListAdapter;
import ru.iteco.fmhandroid.dto.FullClaim;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.util.GeneralModules;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;
import ru.iteco.fmhandroid.ui.util.WaitToast;

public class ClaimsScreen {
    private static final int containerListClaimsID = R.id.container_list_claim_include;
    public static final int addNewClaimButtonID = R.id.add_new_claim_material_button;
    public static final int saveButtonID = R.id.save_button;

    public static final int claimListRecyclerViewID = R.id.claim_list_recycler_view;
    public static final int filtersMaterialButtonID = R.id.filters_material_button;

    public static int getContainerListClaimsID() {

        return containerListClaimsID;
    }

    public static int getClaimListRecyclerViewID() {

        return claimListRecyclerViewID;
    }

    public static void checkingTextInViewAtTitle() {
        Allure.step("Проверка окна \"Заявки\"");
        onView(
                allOf(withText("Заявки"),
                        withParent(withParent(withId(containerListClaimsID))),
                        isDisplayed())).check(matches(withText("Заявки")));

    }

    public static void clickAddNewClaimButton() {
        Allure.step("Нажатие кнопки \"Добавить заявку\"");
        onView(allOf(withId(addNewClaimButtonID), isDisplayed()))
                .perform(click());
    }

    public static void clickSaveButton() {
        Allure.step("Нажатие кнопки \"Сохранить\"");
        onView(allOf(withId(saveButtonID), withText("Сохранить"), withContentDescription("Сохранить")))
                .perform(click());
    }

    public static void addClaim(String title, String currentDate, String currentTime, String description) {
        //Allure.step("Добавление новой заявки с названием: <" + title + "> датой: <" + currentDate + "> временем: <" + currentTime + "> описанием: " + description);
        ClaimsScreen.clickAddNewClaimButton();
        ViewActionWait.waitView(R.id.container_custom_app_bar_include_on_fragment_create_edit_claim, 5000);
        AddClaimScreen.inputTextInFiledTitle(title + " " + currentDate + " " + currentTime);
        AddClaimScreen.inputTextInFiledPerformer("Ivanov Ivan Ivanovich");
        AddClaimScreen.setDateOnDateFiled(currentDate);
        AddClaimScreen.setTimeOnTimeFiled(currentTime);
        AddClaimScreen.inputTextInFiledDescription(description + " " + currentDate + " " + currentTime);
        AddClaimScreen.clickSaveButton();
        ViewActionWait.waitView(ClaimsScreen.getClaimListRecyclerViewID(), 50000);
        while (true) {
            if (WaitToast.waitForAnyToast(5000)) {
                Allure.step("При наличии Toast сообщения вызываем обновление списка заявок");
                swipeDownScreen(ClaimsScreen.getClaimListRecyclerViewID());
            } else {
                break;
            }
        }
        GeneralModules.scrollByRecViewWithText(ClaimsScreen.getClaimListRecyclerViewID(), title + " " + currentDate + " " + currentTime);
    }

    public static void swipeDownScreen(int ID) {
        Allure.step("Свайп по экрану вниз");
        onView(isRoot()).perform(swipeDown());
//        //onView(withId(ID)).perform(ViewActions.swipeDown());
    }

    public static void checkingStatusAfterFilterClaims(String status, ActivityScenarioRule<AppActivity> myactivity) {
        Allure.step("Проверка статуса всех объектов " + status + " после фильтрации");
        myactivity.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(ClaimsScreen.getClaimListRecyclerViewID());
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            ClaimListAdapter claimAdapter = (ClaimListAdapter) adapter;
            List<FullClaim> dataList = claimAdapter.getCurrentList();
            for (FullClaim cl : dataList) {
                Assert.assertEquals("Неправильный статус для элемента: " + cl.getClaim().getTitle(), status, cl.getClaim().getStatus().toString());
            }
        });
    }

    public static void clickFilterButton() {
        Allure.step("Нажатие кнопки \"Фильтровать\"");
        onView(allOf(withId(filtersMaterialButtonID), isDisplayed()))
                .perform(click());
    }

    public static void clickAtPositionRVClaims(int pos) {
        Allure.step("Нажатие по позиции " + pos + " в списке заявок");
        onView(
                allOf(withId(claimListRecyclerViewID)))
                .perform(actionOnItemAtPosition(pos, click()));

        ViewActionWait.waitView(OneClaimScreen.statusIconImageViewID, 5000);
    }

    public static int getClaimPositionByTitle(String title, ActivityScenarioRule<AppActivity> myactivity) {
        Allure.step("Получение номера позиции заявки по названию: " + title);
        AtomicInteger position = new AtomicInteger(-1);

        myactivity.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(ClaimsScreen.getClaimListRecyclerViewID());
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            ClaimListAdapter claimAdapter = (ClaimListAdapter) adapter;
            List<FullClaim> dataList = claimAdapter.getCurrentList();


            for (int i = 0; i < dataList.size(); i++) {
                FullClaim cl = dataList.get(i);
                if (cl.getClaim().getTitle().equals(title)) {
                    position.set(i);
                    break;
                }
            }
        });

        return position.get();
    }


}