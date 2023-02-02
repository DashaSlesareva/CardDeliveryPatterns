package ru.netology.domain;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class UserGenerator {

    public static UserData generateUser() {
        String city = generateCity();
        String name = generateName();
        String phoneNumber = generatePhoneNumber();
        UserData user = new UserData(city, name, phoneNumber);
        return user;
    }


    public static String generateCity() {
        var cities = new String[]{"Майкоп", "Горно-Алтайск", "Уфа", "Магас", "Злиста", "Черкесск", "Петрозаводск",
                "Сыктывкар", "Симферополь", "Йошкар-Опа", "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл",
                "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар",
                "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благоверенск", "Архангельск", "Астрахань",
                "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Орёл", "Иркутск",
                "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Санкт-Петербург", "Липецк",
                "Магадан", "Москва", "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск",
                "Омск", "Оренбург", "Пенза", "Псков", "Салехард", "Ростов-на-Дону", "Рязань", "Самара", "Саратов",
                "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск",
                "Челябинск", "Ярославль", "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь"};
        return cities[new Random().nextInt(cities.length)];
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
