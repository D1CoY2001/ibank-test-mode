package ru.netology.ibank.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.ibank.data.RegistrationDto;
import ru.netology.ibank.data.UserGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    static {
        Configuration.browser = "chrome";
    Configuration.headless = true;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginWithActiveUser() {
        RegistrationDto user = UserGenerator.generateActiveUser();

        login(user.getLogin(), user.getPassword());

        $("h2").shouldBe(Condition.visible);
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        RegistrationDto user = UserGenerator.generateBlockedUser();

        login(user.getLogin(), user.getPassword());

        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("\u041e\u0448\u0438\u0431\u043a\u0430! \u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u0437\u0430\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u043d"));
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        RegistrationDto user = UserGenerator.generateActiveUser();

        login(user.getLogin(), "wrong-password");

        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("\u041e\u0448\u0438\u0431\u043a\u0430! \u041d\u0435\u0432\u0435\u0440\u043d\u043e \u0443\u043a\u0430\u0437\u0430\u043d \u043b\u043e\u0433\u0438\u043d \u0438\u043b\u0438 \u043f\u0430\u0440\u043e\u043b\u044c"));
    }

    @Test
    void shouldNotLoginWithUnknownUser() {
        login("unknown-user", "any-password");

        $("[data-test-id=error-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("\u041e\u0448\u0438\u0431\u043a\u0430! \u041d\u0435\u0432\u0435\u0440\u043d\u043e \u0443\u043a\u0430\u0437\u0430\u043d \u043b\u043e\u0433\u0438\u043d \u0438\u043b\u0438 \u043f\u0430\u0440\u043e\u043b\u044c"));
    }

    private void login(String login, String password) {
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
    }
}