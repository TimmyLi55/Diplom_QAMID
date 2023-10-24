package ru.iteco.fmhandroid.ui.pom;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.util.GeneralModules.childAtPosition;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.adapter.ClaimCommentListAdapter;
import ru.iteco.fmhandroid.dto.ClaimComment;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.util.ConvertUnixTime;
import ru.iteco.fmhandroid.ui.util.ViewActionWait;

public class OneClaimScreen {

    ViewActionWait viewActionWait = new ViewActionWait();

    private final int addCommentImageButtonID = R.id.add_comment_image_button;
    private final int commentTextInputID = R.id.comment_text_input_layout;
    private final int saveButtonCommentID = R.id.save_button;
    private final int commentDescriptionTextViewID = R.id.comment_description_text_view;
    private final int commentDateTextViewID = R.id.comment_date_text_view;
    private final int editProcessingImageButtonID = R.id.edit_processing_image_button;
    private final int claimCommentsListRecyclerViewID = R.id.claim_comments_list_recycler_view;
    private final int closeImageButtonID = R.id.close_image_button;
    private final int statusProcessingImageButtonID = R.id.status_processing_image_button;
    private final int statusLabelTextViewID = R.id.status_label_text_view;
    private final int statusIconImageViewID = R.id.status_icon_image_view;
    private final int editCommentImageButtonID = R.id.edit_comment_image_button;
    private final int editTextID = R.id.editText;
    private final int buttonOkID = android.R.id.button1;
    private final int waitLoadTimer = 50000;
    private final String toastEditClaimInStatus = "Редактировать Заявку можно только в статусе Открыта.";

    private final String toastFiledNotEmpty = "Поле не может быть пустым.";

    private final String statusInWork = "В работу";
    private final String statusReset = "Сбросить";
    private final String stringCancel = "Отменить";

    private final String statusFinalCanceled = "Отменена";

    public int getWaitLoadTimer() {
        return waitLoadTimer;
    }

    public String getStringCancel() {
        return stringCancel;
    }

    public String getStatusFinalCanceled() {
        return statusFinalCanceled;
    }

    public String getStatusInWork() {
        return statusInWork;
    }

    public String getStatusReset() {
        return statusReset;
    }

    public String getToastFiledNotEmpty() {
        return toastFiledNotEmpty;
    }

    public String getToastEditClaimInStatus() {
        return toastEditClaimInStatus;
    }

    public int getClaimCommentsListRecyclerViewID() {
        return claimCommentsListRecyclerViewID;
    }

    public int getStatusLabelTextViewID() {
        return statusLabelTextViewID;
    }

    public int getStatusIconImageViewID() {
        return statusIconImageViewID;
    }

    public void clickAddComment() {
        Allure.step("Нажатие \"Добавить комментарий\"");
        onView(isRoot()).perform(ViewActions.swipeUp());
        viewActionWait.waitView(addCommentImageButtonID, 7000);
        onView(allOf(withId(addCommentImageButtonID), withContentDescription(
                "кнопка добавления комментария"), isDisplayed()))
                .perform(click());
    }

    public void inputCommentAtFiled(String text) {
        Allure.step("Ввод комментария: <" + text + "> в поле комментария");

        onView(withId(commentTextInputID))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(childAtPosition(withId(commentTextInputID),
                                0),
                        0),
                isDisplayed()))
                .perform(replaceText(text), closeSoftKeyboard());
    }

    public void updateCommentAtFiled(String textNow, String textAfter) {
        Allure.step("Изменение текста коммментария <" + textNow + "> на текст <" + textAfter + ">");

        onView(withId(commentTextInputID))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withText(textNow),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0),
                        1),
                isDisplayed()))
                .perform(replaceText(textAfter));

    }

    public void clickSaveButton() {
        Allure.step("Нажатие кнопки \"Сохранить\"");
        onView(allOf(withId(saveButtonCommentID)))
                .perform(scrollTo(), click());

    }

    public void checkCommentInFiled(String comment, String date, String time, ActivityScenarioRule<AppActivity> myactivity) {
        Allure.step("Проверка комментария <" + comment + "> c датой <" + date + "> временем <" + time + ">");
        myactivity.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(getClaimCommentsListRecyclerViewID());
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            ClaimCommentListAdapter claimAdapter = (ClaimCommentListAdapter) adapter;
            List<ClaimComment> dataList = claimAdapter.getCurrentList();
            for (ClaimComment cl : dataList) {
                Assert.assertEquals("Комментария не существует: " + cl.getDescription(), comment, cl.getDescription());
                Log.d("MY", "Comment" + cl.getDescription());
                Assert.assertEquals("Комментария c такой датой не существует: " + ConvertUnixTime.convert(cl.getCreateDate(), "DATE"), date, ConvertUnixTime.convert(cl.getCreateDate(), "DATE"));
                Log.d("MY", "Comment" + ConvertUnixTime.convert(cl.getCreateDate(), "DATE"));
                Assert.assertEquals("Комментария c таким временем не существует: " + ConvertUnixTime.convert(cl.getCreateDate(), "TIME"), time, ConvertUnixTime.convert(cl.getCreateDate(), "TIME"));
                Log.d("MY", "Comment" + ConvertUnixTime.convert(cl.getCreateDate(), "TIME"));
            }
        });
    }

    public void clickChangeStatusButton() {
        Allure.step("Нажатие изменить статус заявки");
        onView(isRoot()).perform(ViewActions.swipeUp());
        viewActionWait.waitView(statusProcessingImageButtonID, waitLoadTimer);
        onView(allOf(withId(statusProcessingImageButtonID), withContentDescription("кнопка вызова действия статусной обработки")))
                .perform(click());
    }

    public void clickEditClaimButton() {
        Allure.step("Нажатие изменить заявку");
        onView(isRoot()).perform(ViewActions.swipeUp());
        viewActionWait.waitView(editProcessingImageButtonID, waitLoadTimer);
        onView(allOf(withId(editProcessingImageButtonID), withContentDescription("кнопка настройки")))
                .perform(click());
    }

    public void clickBackClaimButton() {
        Allure.step("Нажатие назад из карточки заявки");
        onView(isRoot()).perform(ViewActions.swipeUp());
        viewActionWait.waitView(closeImageButtonID, waitLoadTimer);
        onView(allOf(withId(closeImageButtonID), withContentDescription("кнопка закрытия экранной формы")))
                .perform(click());
    }

    public void checkTextEditInTitle() {
        Allure.step("Проверка окна редактирования заявки");
        onView(allOf(withId(R.id.custom_app_bar_title_text_view), withText("Редактирование"),
                withParent(allOf(withId(R.id.container_custom_app_bar_include_on_fragment_create_edit_claim),
                        withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                isDisplayed()))
                .check(matches(withText("Редактирование")));
    }

    public void clickButtonChangeStatus(String status) {
        Allure.step("Выбор статуса <" + status + ">");
        onView(withText(status)).perform(click());
    }

    public void checkStatusAtClaim(String text) {
        Allure.step("Проверка статуса <" + text + "> у заявки");
        viewActionWait.waitView(statusIconImageViewID, waitLoadTimer);
        onView(allOf(withId(statusLabelTextViewID), withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText(text)));
    }

    public void scrollAtPositionButtonEditComment(int pos) {
        Allure.step("Редактирование комментария на позиции <" + pos + ">");
        onView(allOf(withId(claimCommentsListRecyclerViewID)))
                .perform(actionOnItemAtPosition(pos, scrollTo()));

        onView(allOf(withId(editCommentImageButtonID), childAtPosition(
                        childAtPosition(
                                withId(claimCommentsListRecyclerViewID),
                                pos),
                        1),
                isDisplayed()))
                .perform(click());
    }

    public void inputCommentStatusCanceled(String reason) {
        Allure.step("Ввод комментария причины отмены заявки <" + reason + ">");
        onView(withId(editTextID))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(allOf(withId(editTextID),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                0),
                        0),
                isDisplayed()))
                .perform(replaceText(reason), closeSoftKeyboard());
        onView(allOf(withId(buttonOkID), withText("ОК"),
                childAtPosition(
                        childAtPosition(
                                withId(com.google.android.material.R.id.buttonPanel),
                                0),
                        3)))
                .perform(scrollTo(), click());
    }

    public int getCommentPositionByTitle(String comment, ActivityScenarioRule<AppActivity> myactivity) {
        Allure.step("Получение номера позиции комментария по названию <" + comment + ">");
        AtomicInteger position = new AtomicInteger(-1);

        myactivity.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(claimCommentsListRecyclerViewID);
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            ClaimCommentListAdapter claimAdapter = (ClaimCommentListAdapter) adapter;
            List<ClaimComment> dataList = claimAdapter.getCurrentList();

            for (int i = 0; i < dataList.size(); i++) {
                ClaimComment cl = dataList.get(i);
                if (cl.getDescription().equals(comment)) {
                    position.set(i);
                    break;
                }
            }
        });

        return position.get();
    }
}