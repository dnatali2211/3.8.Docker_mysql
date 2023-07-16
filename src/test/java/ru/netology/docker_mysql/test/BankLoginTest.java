package ru.netology.docker_mysql.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.docker_mysql.data.DataHelper;
import ru.netology.docker_mysql.data.SQLHelper;
import ru.netology.docker_mysql.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.docker_mysql.data.SQLHelper.cleanDataBase;

public class BankLoginTest {

    @AfterAll
    static void teardown() {
        cleanDataBase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should add error notification if login with exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should error notification if login with exist in base and active user and random password")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoLogin = DataHelper.getAuthInfoWithRandomPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should block user if login with exist in base and active user and entered random password three times")
    void shouldBlockIfLoginWithExistUserAndEnteredRandomPasswordThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoLogin = DataHelper.getAuthInfoWithRandomPassword();
        loginPage.validLogin(authInfoLogin);
        loginPage.verifyErrorNotificationVisibility();

        for (int i = 0; i < 3; i++) {
            loginPage.getLoginField().clear();
            loginPage.getPasswordInput().clear();
            loginPage.validLogin(authInfoLogin);
            loginPage.verifyErrorNotificationVisibility();
        }
    }
}
