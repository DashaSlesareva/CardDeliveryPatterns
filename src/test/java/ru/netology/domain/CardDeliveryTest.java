package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static ru.netology.domain.util.LoggingUtils.logInfo;

public class CardDeliveryTest {

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void positiveTest() {
        //гененируем юзера, исползуя Faker
        UserData user = UserGenerator.generateUser();
        //Заполняем поля данными сгенерированного юзера, отправляем форму, проверяем всплывающее окно
        $("[data-test-id='city'] input").setValue(user.getCity());
        logInfo("В поле ввода введен город " + user.getCity());
        $("[data-test-id='date'] .input__control").sendKeys(Keys.COMMAND + "A");
        $("[data-test-id='date'] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(5));
        logInfo("В поле ввода введена дата через 5 дней " + generateDate(5));
        $("[data-test-id='name'] input").setValue(user.getName());
        logInfo("В поле ввода введено имя " + user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhoneNumber());
        logInfo("В поле ввода введен номер телефона " + user.getPhoneNumber());
        $("[data-test-id='agreement'] span").click();
        $x("//span[text()='Запланировать']").click();
        logInfo("Выполнен клик по кнопке запланировать");
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + generateDate(5).toString()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        logInfo("Встреча успешно запланирована на " + generateDate(5));
        //закрываем всплывающее окно
        $("[data-test-id='success-notification'] button").click();
        logInfo("Закрыто всплывающее окно");
        //вводим другую дату, отправляем форму
        $("[data-test-id='date'] .input__control").sendKeys(Keys.COMMAND + "A");
        $("[data-test-id='date'] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(6));
        logInfo("В поле ввода введена новая дата через 6 дней " + generateDate(6));
        $x("//span[text()='Запланировать']").click();
        logInfo("Выполнен клик по кнопке запланировать");
        //проверяем всплывающее окно
        $("[data-test-id='replan-notification'] ")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        logInfo("Проверка вспывающего окна");
        //нажимаем на кнопку "Перепланировать"
        $("[data-test-id='replan-notification'] .button__text").click();
        logInfo("Выполнен клик по кнопке перепланировать");
        //проверяем всплывающее окно с новой датой встречи
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + generateDate(6).toString()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        logInfo("Встреча успешно запланирована на " + generateDate(6));
    }
}
