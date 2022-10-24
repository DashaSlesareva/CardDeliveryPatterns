package ru.netology.domain;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {

        public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
//        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(5));
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getPhoneNumber());
        $("[data-test-id='agreement'] span").click();
        $x("//span[text()='Запланировать']").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + generateDate(5).toString()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        //закрываем всплывающее окно
        $("[data-test-id='success-notification'] button").click();
        //вводим другую дату, отправляем форму
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(generateDate(6));
        $x("//span[text()='Запланировать']").click();
        //проверяем всплывающее окно
        $("[data-test-id='replan-notification'] ")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        //нажимаем на кнопку "Перепланировать"
        $("[data-test-id='replan-notification'] .button__text").click();
        //проверяем всплывающее окно с новой датой встречи
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + generateDate(6).toString()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
