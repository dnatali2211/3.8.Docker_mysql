package ru.netology.docker_mysql.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void verifyVerificationPageVisibility() {codeField.shouldBe(Condition.visible);}
    public void verifyErrorNotificationVisibility() {errorNotification.shouldBe(Condition.visible);}

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }
    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }
}
