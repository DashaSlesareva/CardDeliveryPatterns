package ru.netology.domain;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UserGenerator {

    public static UserData generateUser(int days) {
        String city = generateCity();
        String date = generateDate(days);
        String name = generateName();
        String phoneNumber = generatePhoneNumber();
        UserData user = new UserData(city, date, name, phoneNumber);
        return user;
    }

    public static String generateCity() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.address().city();
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generatePhoneNumber() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }
}
