package ru.netology.domain;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {

    public static UserData generateUser(){
        String city = generateCity();
        String name = generateName();
        String phoneNumber = generatePhoneNumber();
        UserData user = new UserData(city, name, phoneNumber);
        return user;
    }

    public static String generateCity() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.address().city();
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
