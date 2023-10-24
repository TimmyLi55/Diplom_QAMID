package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.pom.AddClaimFilterScreen;
import ru.iteco.fmhandroid.ui.pom.AddClaimScreen;
import ru.iteco.fmhandroid.ui.pom.AuthorizationScreen;
import ru.iteco.fmhandroid.ui.pom.ClaimsScreen;
import ru.iteco.fmhandroid.ui.pom.MainScreen;
import ru.iteco.fmhandroid.ui.pom.OneClaimScreen;
import ru.iteco.fmhandroid.ui.util.CustomMatchers;
import ru.iteco.fmhandroid.ui.util.GeneralModules;
import ru.iteco.fmhandroid.ui.util.TestAuthData;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void authorisationAndGoToClaims() {
        authorizationScreen.checkAuthorisationAndLogout();
        authorizationScreen.authorisation(testAuthData.getValidLogin(), testAuthData.getValidPassword());
        mainScreen.goToClaims();
    }


    AddClaimFilterScreen addClaimFilterScreen = new AddClaimFilterScreen();
    AddClaimScreen addClaimScreen = new AddClaimScreen();
    AuthorizationScreen authorizationScreen = new AuthorizationScreen();

    ClaimsScreen claimsScreen = new ClaimsScreen();
    MainScreen mainScreen = new MainScreen();
    OneClaimScreen oneClaimScreen = new OneClaimScreen();
    GeneralModules generalModules = new GeneralModules();
    CustomMatchers customMatchers = new CustomMatchers();
    ViewActionWait viewActionWait = new ViewActionWait();
    TestAuthData testAuthData = new TestAuthData();

    String currentDate = generalModules.getDateTime("date");
    String currentTime = generalModules.getDateTime("time");
    String endDate = "01.01.2022";
    String endTime = "00:00";
    String titleTestClaim = "Тестовая заявка";
    String titleTestDescription = "Тестовое описание";
    String titleMore50Symbols = "Тестирование количества символов в теме заявки тут больше 50";
    String title50Symbols = "Тестирование количества символов в теме заявки тут";
    String titleWithElapsedDate = "Заявка с прошедшей датой";
    String titleWithElapsedTime = "Заявка с прошедшим временем";
    String textPerformer = "Ivanov Ivan Ivanovich";
    String titleAddComment = "Добавление комментария";
    String textComment = "Тестирование комменатрия";
    String newTextComment = "Измененный комментарий";
    String newTitleClaimText = "Изменение наименования заявки";
    String textStatusReset = "Проверка сброса";
    String titleTestClaimStatus = "Новая заявка для изменения статуса";

    @Test
    @DisplayName("Создание пустой заявки")
    public void creatingEmptyClaims() {
        claimsScreen.clickAddNewClaimButton();
        claimsScreen.clickSaveButton();
        customMatchers.checkToast(addClaimScreen.getToastEmptyFiled());

    }

    @Test
    @DisplayName("Создание валидной заявки")
    public void creatingValidClaim() {
        claimsScreen.addClaim(titleTestClaim, currentDate, currentTime, titleTestDescription);
    }


    @Test
    @DisplayName("Фильтрация по заявке В работе")
    public void filteringClaim() throws InterruptedException {
        claimsScreen.clickFilterButton();
        addClaimFilterScreen.clickCheckBoxOpen();
        addClaimFilterScreen.clickOkButton();
        claimsScreen.checkingStatusAfterFilterClaims(claimsScreen.getStatusInProgress(), mActivityScenarioRule);

    }

    @Test
    @DisplayName("Создание заявки с наименованием больше 50 символов")
    public void creatingClaimWithNameMore50Characters() {
        claimsScreen.clickAddNewClaimButton();
        viewActionWait.waitView(addClaimScreen.getContainerFragmentCreateClaimID(), addClaimScreen.getWaitLoadTimer());
        addClaimScreen.inputTextInFiledTitle(titleMore50Symbols);
        addClaimScreen.checkTextInFiled(title50Symbols);
    }

    @Test
    @DisplayName("Создание заявки с прошедшей датой")
    public void creatingClaimWithEndDate() {

        claimsScreen.clickAddNewClaimButton();
        addClaimScreen.inputTextInFiledTitle(titleWithElapsedDate + " " + currentDate + " " + currentTime);
        addClaimScreen.inputTextInFiledPerformer(textPerformer);
        addClaimScreen.setDateOnDateFiled(endDate);
        customMatchers.checkToast(addClaimScreen.getToastElapsedDate());


    }

    @Test
    @DisplayName("Создание заявки с прошедшим временем ")
    public void creatingClaimWithEndTime() {

        claimsScreen.clickAddNewClaimButton();
        addClaimScreen.inputTextInFiledTitle(titleWithElapsedTime + " " + currentDate + " " + currentTime);
        addClaimScreen.inputTextInFiledPerformer(textPerformer);
        addClaimScreen.setDateOnDateFiled(endDate);
        addClaimScreen.setTimeOnTimeFiled(endTime);
        customMatchers.checkToast(addClaimScreen.getToastElapsedTime());
    }

    @Test
    @DisplayName("Добавление комментария к готовой заявке с проверкой даты и времени создания")
    public void creatingCommentInClaim() {
        claimsScreen.addClaim(titleAddComment, currentDate, currentTime, titleTestDescription);
        claimsScreen.clickAtPositionRVClaims(claimsScreen.getClaimPositionByTitle(titleAddComment + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        oneClaimScreen.clickAddComment();
        oneClaimScreen.inputCommentAtFiled(textComment + " " + currentTime);
        oneClaimScreen.clickSaveButton();
        oneClaimScreen.checkCommentInFiled(textComment + " " + currentTime, currentDate, currentTime, mActivityScenarioRule);
    }

    @Test
    @DisplayName("Редактирование созданной заявки в статусе Открыта")
    public void editingCreatedClaimOpenStatus() {
        claimsScreen.addClaim(titleTestClaim, currentDate, currentTime, titleTestDescription);
        claimsScreen.clickAtPositionRVClaims(claimsScreen.getClaimPositionByTitle(titleTestClaim + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        oneClaimScreen.clickEditClaimButton();
        oneClaimScreen.checkTextEditInTitle();
        addClaimScreen.inputTextInFiledTitle(newTitleClaimText + " " + currentDate + " " + currentTime);
        addClaimScreen.clickSaveButton();
        oneClaimScreen.clickBackClaimButton();
        viewActionWait.waitView(claimsScreen.getClaimListRecyclerViewID(), claimsScreen.getWaitLoadTimer());
        generalModules.scrollByRecViewWithText(claimsScreen.getClaimListRecyclerViewID(), newTitleClaimText + " " + currentDate + " " + currentTime);
    }

    @Test
    @DisplayName("Редактирование созданной заявки в статусе В работе")
    public void editingCreatedClaimInProgressStatus() {
        claimsScreen.addClaim(titleTestClaim, currentDate, currentTime, titleTestDescription);
        claimsScreen.clickAtPositionRVClaims(claimsScreen.getClaimPositionByTitle(titleTestClaim + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        oneClaimScreen.clickChangeStatusButton();
        oneClaimScreen.clickButtonChangeStatus(oneClaimScreen.getStatusInWork());
        oneClaimScreen.clickEditClaimButton();
        customMatchers.checkToast(oneClaimScreen.getToastEditClaimInStatus());
    }

    @Test
    @DisplayName("Добавление пустого комментария к готовой заявке")
    public void creatingEmptyCommentInClaim() {
        claimsScreen.addClaim(titleAddComment, currentDate, currentTime, titleTestDescription);
        claimsScreen.clickAtPositionRVClaims(claimsScreen.getClaimPositionByTitle(titleAddComment + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        oneClaimScreen.clickAddComment();
        oneClaimScreen.clickSaveButton();
        customMatchers.checkToast(oneClaimScreen.getToastFiledNotEmpty());
    }

    @Test
    @DisplayName("Изменение созданного комментария")
    public void editingCommentInClaim() {
        claimsScreen.addClaim(titleAddComment, currentDate, currentTime, titleTestDescription);
        claimsScreen.clickAtPositionRVClaims(claimsScreen.getClaimPositionByTitle(titleAddComment + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        oneClaimScreen.clickAddComment();
        oneClaimScreen.inputCommentAtFiled(textComment + " " + currentDate);
        oneClaimScreen.clickSaveButton();
        oneClaimScreen.scrollAtPositionButtonEditComment(oneClaimScreen.getCommentPositionByTitle(textComment + " " + currentDate, mActivityScenarioRule));
        oneClaimScreen.updateCommentAtFiled(textComment + " " + currentDate, newTextComment + " " + currentDate);
        oneClaimScreen.clickSaveButton();
        oneClaimScreen.checkCommentInFiled(newTextComment + " " + currentDate, currentDate, currentTime, mActivityScenarioRule);

    }

    @Test
    @DisplayName("Изменение статуса заявки с В работе на Отменена")
    public void changeStatusClaimInWorkToCancelled() {
        claimsScreen.addClaim(titleTestClaimStatus, currentDate, currentTime, titleTestDescription);
        claimsScreen.clickAtPositionRVClaims(claimsScreen.getClaimPositionByTitle(titleTestClaimStatus + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        oneClaimScreen.clickChangeStatusButton();
        oneClaimScreen.clickButtonChangeStatus(oneClaimScreen.getStatusInWork());
        oneClaimScreen.clickChangeStatusButton();
        oneClaimScreen.clickButtonChangeStatus(oneClaimScreen.getStatusReset());
        oneClaimScreen.inputCommentStatusCanceled(textStatusReset);
        oneClaimScreen.checkStatusAtClaim(oneClaimScreen.getStatusFinalCanceled());
    }

    @Test
    @DisplayName("Изменение статуса отмененной заявки с нажатием назад системной кнопкой")
    public void changeStatusClaimCancelled() {
        claimsScreen.addClaim(titleTestClaimStatus, currentDate, currentTime, titleTestDescription);
        claimsScreen.clickAtPositionRVClaims(claimsScreen.getClaimPositionByTitle(titleTestClaimStatus + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        oneClaimScreen.clickChangeStatusButton();
        oneClaimScreen.clickButtonChangeStatus(oneClaimScreen.getStringCancel());
        oneClaimScreen.checkStatusAtClaim(oneClaimScreen.getStatusFinalCanceled());
        viewActionWait.waitView(oneClaimScreen.getStatusLabelTextViewID(), oneClaimScreen.getWaitLoadTimer());
        oneClaimScreen.clickChangeStatusButton();
        Allure.step("Нажатие системной кнопки назад");
        onView(isRoot()).perform(ViewActions.pressBack());
        viewActionWait.waitView(claimsScreen.getClaimListRecyclerViewID(), claimsScreen.getWaitLoadTimer());
    }

}

//enum class Status {
//    CANCELLED,
//    EXECUTED,
//    IN_PROGRESS,
//    OPEN
//}