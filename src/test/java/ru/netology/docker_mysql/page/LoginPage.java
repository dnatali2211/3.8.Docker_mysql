package ru.netology.docker_mysql.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.docker_mysql.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(Condition.visible);
    }
    public void findErrorNotificationVisibility(String expectedText) {
        errorNotification.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
    public void clearFields() {
        loginField.sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.BACK_SPACE);
        passwordField.sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.BACK_SPACE);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
