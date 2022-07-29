package ru.netology.domain.utils;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.domain.entities.User;

import java.time.LocalDate;
import java.util.Locale;

@UtilityClass
public class DataGenerator {

    public static String[] selectCity = {"Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик"
            , "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск"
            , "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита"
            , "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь", "Владивосток", "Ставрополь"
            , "Хабаровск", "Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград"
            , "Вологда", "Воронеж", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома"
            , "Курган", "Курск", "Гатчина", "Липецк", "Магадан", "Красногорск", "Мурманск", "Нижний Новгород"
            , "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону"
            , "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь"
            , "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Москва", "Санкт-Петербург"
            , "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард"};

    @UtilityClass
    public static class Registration {
        public static User generateUser(String locale, int shiftDate) {
            Faker faker = new Faker(new Locale(locale));
            int cityIndex = rnd(0, 84);
            System.out.println("cityIndex: " + cityIndex);
            return new User(selectCity[cityIndex], setDate(shiftDate), faker.name().lastName() + " " + faker.name().firstName(), faker.phoneNumber().phoneNumber());
        }
    }

    /**
     * Метод получения псевдослучайного целого числа от min до max (включая max);
     */
    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public String setDate(int shiftDay) {
        LocalDate date = LocalDate.now();
        String testDate;

        /** изменение даты minus[Days][weeks][months][years](int n): отнимает от даты некоторое количество дней,
         * plus[Days][weeks][months][years](int n): добавляет к дате некоторое количество дней
         * сдиг даты относительно сегоднешнего дня
         */
        if (shiftDay != 0) {
            date = date.plusDays(shiftDay);
        }
        // контроль за временем
        //System.out.println("Trace of time: " + date);
        /**
         * переворачиваем написание даты
         */
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        /**
         * добавляем 0, если день и/или месяц состоят из одного числа
         */

        if (day < 10) {
            if (month < 10) {
                testDate = String.join(".", "0" + Integer.toString(day), "0" + Integer.toString(month), Integer.toString(year));
            } else {
                testDate = String.join(".", "0" + Integer.toString(day), Integer.toString(month), Integer.toString(year));
            }
        } else {
            if (month < 10) {
                testDate = String.join(".", Integer.toString(day), "0" + Integer.toString(month), Integer.toString(year));
            } else {
                testDate = String.join(".", Integer.toString(day), Integer.toString(month), Integer.toString(year));
            }
        }
        // контроль за временем
        //System.out.println("Trace of time: " + testDate);

        return testDate;
    }

}
