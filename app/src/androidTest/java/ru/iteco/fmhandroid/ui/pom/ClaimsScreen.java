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

import androidx.recyclerview.widget.RecyclerView;
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

    AddClaimScreen addClaimScreen = new AddClaimScreen();
    GeneralModules generalModules = new GeneralModules();
    ViewActionWait viewActionWait = new ViewActionWait();
    OneClaimScreen oneClaimScreen = new OneClaimScreen();
    private final int containerListClaimsID = R.id.container_list_claim_include;
    private final int addNewClaimButtonID = R.id.add_new_claim_material_button;
    private final int saveButtonID = R.id.save_button;
    private final int claimListRecyclerViewID = R.id.claim_list_recycler_view;
    private final int filtersMaterialButtonID = R.id.filters_material_button;
    private final int containerCustomAppBarIncludeOnFragmentCreateEditClaim = R.id.container_custom_app_bar_include_on_fragment_create_edit_claim;

    private final String statusInProgress = "IN_PROGRESS";
    private final int waitLoadTimer = 50000;

    public int getWaitLoadTimer() {
        return waitLoadTimer;
    }

    public String getStatusInProgress() {
        return statusInProgress;
    }

    public int getContainerListClaimsID() {

        return containerListClaimsID;
    }

    public int getClaimListRecyclerViewID() {

        return claimListRecyclerViewID;
    }

    public void checkingTextInViewAtTitle() {
        Allure.step("Проверка окна \"Заявки\"");
        onView(
                allOf(withText("Заявки"),
                        withParent(withParent(withId(containerListClaimsID))),
                        isDisplayed())).check(matches(withText("Заявки")));

    }

    public void clickAddNewClaimButton() {
        Allure.step("Нажатие кнопки \"Добавить заявку\"");
        onView(allOf(withId(addNewClaimButtonID), isDisplayed()))
                .perform(click());
    }

    public void clickSaveButton() {
        Allure.step("Нажатие кнопки \"Сохранить\"");
        onView(allOf(withId(saveButtonID), withText("Сохранить"), withContentDescription("Сохранить")))
                .perform(click());
    }

    public void addClaim(String title, String currentDate, String currentTime, String description) {
        clickAddNewClaimButton();
        viewActionWait.waitView(containerCustomAppBarIncludeOnFragmentCreateEditClaim, 5000);
        addClaimScreen.inputTextInFiledTitle(title + " " + currentDate + " " + currentTime);
        addClaimScreen.inputTextInFiledPerformer("Ivanov Ivan Ivanovich");
        addClaimScreen.setDateOnDateFiled(currentDate);
        addClaimScreen.setTimeOnTimeFiled(currentTime);
        addClaimScreen.inputTextInFiledDescription(description + " " + currentDate + " " + currentTime);
        addClaimScreen.clickSaveButton();
        viewActionWait.waitView(getClaimListRecyclerViewID(), claimListRecyclerViewID);
        while (true) {
            if (WaitToast.waitForAnyToast(5000)) {
                Allure.step("При наличии Toast сообщения вызываем обновление списка заявок");
                swipeDownScreen(getClaimListRecyclerViewID());
            } else {
                break;
            }
        }
        generalModules.scrollByRecViewWithText(getClaimListRecyclerViewID(), title + " " + currentDate + " " + currentTime);
    }

    public void swipeDownScreen(int ID) {
        Allure.step("Свайп по экрану вниз");
        onView(isRoot()).perform(swipeDown());
//        //onView(withId(ID)).perform(ViewActions.swipeDown());
    }

    public void checkingStatusAfterFilterClaims(String status, ActivityScenarioRule<AppActivity> myactivity) {
        Allure.step("Проверка статуса всех объектов " + status + " после фильтрации");
        myactivity.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(getClaimListRecyclerViewID());
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            ClaimListAdapter claimAdapter = (ClaimListAdapter) adapter;
            List<FullClaim> dataList = claimAdapter.getCurrentList();
            for (FullClaim cl : dataList) {
                Assert.assertEquals("Неправильный статус для элемента: " + cl.getClaim().getTitle(), status, cl.getClaim().getStatus().toString());
            }
        });
    }

    public void clickFilterButton() {
        Allure.step("Нажатие кнопки \"Фильтровать\"");
        onView(allOf(withId(filtersMaterialButtonID), isDisplayed()))
                .perform(click());
    }

    public void clickAtPositionRVClaims(int pos) {
        Allure.step("Нажатие по позиции " + pos + " в списке заявок");
        onView(
                allOf(withId(claimListRecyclerViewID)))
                .perform(actionOnItemAtPosition(pos, click()));

        viewActionWait.waitView(oneClaimScreen.getStatusIconImageViewID(), 5000);
    }

    public int getClaimPositionByTitle(String title, ActivityScenarioRule<AppActivity> myactivity) {
        Allure.step("Получение номера позиции заявки по названию: " + title);
        AtomicInteger position = new AtomicInteger(-1);

        myactivity.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(getClaimListRecyclerViewID());
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