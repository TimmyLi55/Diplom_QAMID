package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
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
import ru.iteco.fmhandroid.ui.util.Rules;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

@RunWith(AllureAndroidJUnit4.class)

public class ClaimsTest extends Rules {
    @Before
    public void authorisationAndGoToClaims() {
        AuthorizationScreen.authorisation(AuthorizationsTests.getValidLogin(), AuthorizationsTests.getValidPassword());
        MainScreen.goToClaims();
    }

    static String currentDate = GeneralModules.getDateTime("date");
    static String currentTime = GeneralModules.getDateTime("time");
    static String endDate = "01.01.2022";
    static String endTime = "00:00";

    @Test
    @DisplayName("Создание пустой заявки")
    public void creatingEmptyClaims() {
        ClaimsScreen.clickAddNewClaimButton();
        ClaimsScreen.clickSaveButton();
        CustomMatchers.checkToast("Заполните пустые поля");

    }

    @Test
    @DisplayName("Создание валидной заявки")
    public void creatingValidClaim() {
        ClaimsScreen.addClaim("Тестовая заявка", currentDate, currentTime, "Тестовое описание");
    }


    @Test
    @DisplayName("Фильтрация по заявке В работе")
    public void filteringClaim() throws InterruptedException {

        String status = "IN_PROGRESS";

        ClaimsScreen.clickFilterButton();
        AddClaimFilterScreen.clickCheckBoxOpen();
        AddClaimFilterScreen.clickOkButton();
        ClaimsScreen.checkingStatusAfterFilterClaims(status, mActivityScenarioRule);

    }

    @Test
    @DisplayName("Создание заявки с наименованием больше 50 символов")
    public void creatingClaimWithNameMore50Characters() {
        ClaimsScreen.clickAddNewClaimButton();
        ViewActionWait.waitView(AddClaimScreen.containerFragmentCreateClaimID, 5000);
        AddClaimScreen.inputTextInFiledTitle("Тестирование количества символов в теме заявки тут больше 50");
        AddClaimScreen.checkTextInFiled("Тестирование количества символов в теме заявки тут");
    }

    @Test
    @DisplayName("Создание заявки с прошедшей датой")
    public void creatingClaimWithEndDate() {

        ClaimsScreen.clickAddNewClaimButton();
        AddClaimScreen.inputTextInFiledTitle("Заявка с прошедшим временем" + " " + currentDate + " " + currentTime);
        AddClaimScreen.inputTextInFiledPerformer("Ivanov Ivan Ivanovich");
        AddClaimScreen.setDateOnDateFiled(endDate);
        CustomMatchers.checkToast("Выбор прошедшей даты невозможен!");


    }

    @Test
    @DisplayName("Создание заявки с прошедшим временем ")
    public void creatingClaimWithEndTime() {

        ClaimsScreen.clickAddNewClaimButton();
        AddClaimScreen.inputTextInFiledTitle("Заявка с прошедшим временем" + " " + currentDate + " " + currentTime);
        AddClaimScreen.inputTextInFiledPerformer("Ivanov Ivan Ivanovich");
        AddClaimScreen.setDateOnDateFiled(endDate);
        AddClaimScreen.setTimeOnTimeFiled(endTime);
        CustomMatchers.checkToast("Выбор прошедшего времени невозможен!");
    }

    @Test
    @DisplayName("Добавление комментария к готовой заявке с проверкой даты и времени создания")
    public void creatingCommentInClaim() {
        ClaimsScreen.addClaim("Добавление комментария", currentDate, currentTime, "Тестовое описание");
        ClaimsScreen.clickAtPositionRVClaims(ClaimsScreen.getClaimPositionByTitle("Добавление комментария" + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        OneClaimScreen.clickAddComment();
        OneClaimScreen.inputCommentAtFiled("Тестирование комменатрия" + " " + currentTime);
        OneClaimScreen.clickSaveButton();
        OneClaimScreen.checkCommentInFiled("Тестирование комменатрия" + " " + currentTime, currentDate, currentTime, mActivityScenarioRule);
    }

    @Test
    @DisplayName("Редактирование созданной заявки в статусе Открыта")
    public void editingCreatedClaimOpenStatus() {
        ClaimsScreen.addClaim("Новая заявка", currentDate, currentTime, "Тестовое описание");
        ClaimsScreen.clickAtPositionRVClaims(ClaimsScreen.getClaimPositionByTitle("Новая заявка" + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        OneClaimScreen.clickEditClaimButton();
        OneClaimScreen.checkTextEditInTitle();
        AddClaimScreen.inputTextInFiledTitle("Изменение наименования заявки" + " " + currentDate + " " + currentTime);
        AddClaimScreen.clickSaveButton();
        OneClaimScreen.clickBackClaimButton();
        ViewActionWait.waitView(ClaimsScreen.getClaimListRecyclerViewID(), 50000);
        GeneralModules.scrollByRecViewWithText(ClaimsScreen.getClaimListRecyclerViewID(), "Изменение наименования заявки" + " " + currentDate + " " + currentTime);
    }

    @Test
    @DisplayName("Редактирование созданной заявки в статусе В работе")
    public void editingCreatedClaimInProgressStatus() {
        ClaimsScreen.addClaim("Новая заявка", currentDate, currentTime, "Тестовое описание");
        ClaimsScreen.clickAtPositionRVClaims(ClaimsScreen.getClaimPositionByTitle("Новая заявка" + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        OneClaimScreen.clickChangeStatusButton();
        OneClaimScreen.clickButtonChangeStatus("В работу");
        OneClaimScreen.clickEditClaimButton();
        CustomMatchers.checkToast("Редактировать Заявку можно только в статусе Открыта.");
    }

    @Test
    @DisplayName("Добавление пустого комментария к готовой заявке")
    public void creatingEmptyCommentInClaim() {
        ClaimsScreen.addClaim("Добавление комментария", currentDate, currentTime, "Тестовое описание");
        ClaimsScreen.clickAtPositionRVClaims(ClaimsScreen.getClaimPositionByTitle("Добавление комментария" + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        OneClaimScreen.clickAddComment();
        OneClaimScreen.clickSaveButton();
        CustomMatchers.checkToast("Поле не может быть пустым.");
    }

    @Test
    @DisplayName("Изменение созданного комментария")
    public void editingCommentInClaim() {
        ClaimsScreen.addClaim("Добавление комментария", currentDate, currentTime, "Тестовое описание");
        ClaimsScreen.clickAtPositionRVClaims(ClaimsScreen.getClaimPositionByTitle("Добавление комментария" + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        OneClaimScreen.clickAddComment();
        OneClaimScreen.inputCommentAtFiled("Комментарий" + " " + currentDate);
        OneClaimScreen.clickSaveButton();
        OneClaimScreen.scrollAtPositionButtonEditComment(OneClaimScreen.getCommentPositionByTitle("Комментарий" + " " + currentDate, mActivityScenarioRule));
        OneClaimScreen.updateCommentAtFiled("Комментарий" + " " + currentDate, "Измененный комментарий" + " " + currentDate);
        OneClaimScreen.clickSaveButton();
        OneClaimScreen.checkCommentInFiled("Измененный комментарий" + " " + currentDate, currentDate, currentTime, mActivityScenarioRule);

    }

    @Test
    @DisplayName("Изменение статуса заявки с В работе на Отменена")
    public void changeStatusClaimInWorkToCancelled() {
        ClaimsScreen.addClaim("Новая заявка для изменения статуса", currentDate, currentTime, "Тестовое описание");
        ClaimsScreen.clickAtPositionRVClaims(ClaimsScreen.getClaimPositionByTitle("Новая заявка для изменения статуса" + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        OneClaimScreen.clickChangeStatusButton();
        OneClaimScreen.clickButtonChangeStatus("В работу");
        OneClaimScreen.clickChangeStatusButton();
        OneClaimScreen.clickButtonChangeStatus("Сбросить");
        OneClaimScreen.inputCommentStatusCanceled("Проверка сброса");
        OneClaimScreen.checkStatusAtClaim("Отменена");
    }

    @Test
    @DisplayName("Изменение статуса отмененной заявки с нажатием назад системной кнопкой")
    public void changeStatusClaimCancelled() {
        ClaimsScreen.addClaim("Заявка для изменения на Отмена", currentDate, currentTime, "Тестовое описание");
        ClaimsScreen.clickAtPositionRVClaims(ClaimsScreen.getClaimPositionByTitle("Заявка для изменения на Отмена" + " " + currentDate + " " + currentTime, mActivityScenarioRule));
        OneClaimScreen.clickChangeStatusButton();
        OneClaimScreen.clickButtonChangeStatus("Отменить");
        OneClaimScreen.checkStatusAtClaim("Отменена");
        ViewActionWait.waitView(OneClaimScreen.statusLabelTextViewID, 50000);
        OneClaimScreen.clickChangeStatusButton();
        Allure.step("Нажатие системной кнопки назад");
        onView(isRoot()).perform(ViewActions.pressBack());
        ViewActionWait.waitView(ClaimsScreen.getClaimListRecyclerViewID(), 50000);
    }

}

//enum class Status {
//    CANCELLED,
//    EXECUTED,
//    IN_PROGRESS,
//    OPEN
//}