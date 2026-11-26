package ru.netology.ibank.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.ibank.data.RegistrationDto;
import ru.netology.ibank.data.UserGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginWithActiveUser() {
        RegistrationDto user = UserGenerator.generateActiveUser();

        login(user.getLogin(), user.getPassword());

        $("h2").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        RegistrationDto user = UserGenerator.generateBlockedUser();

        login(user.getLogin(), user.getPassword());

        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        RegistrationDto user = UserGenerator.generateUserWithWrongPassword();

        login(user.getLogin(), user.getPassword());

        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWithUnknownUser() {
        RegistrationDto user = UserGenerator.generateUnknownUser();

        login(user.getLogin(), user.getPassword());

        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    private void login(String login, String password) {
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
    }
}