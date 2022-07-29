package ru.netology.domain;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.domain.entities.User;
import ru.netology.domain.utils.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    private void printTestData(String city, String date, String name, String phone) {
        System.out.println("\n" + city + "\n" + date + "\n" + name + "\n" + phone + "\n");
    }

    private void printTestData(User user) {
        printTestData(user.getCity(), user.getDate(), user.getName(), user.getPhone());
    }

    @Test
    void shouldTestOrderAdminCenter() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);

        $("[data-test-id='success-notification']").shouldBe(visible);
    }

    @Test
    void shouldTestOrderAdminCenterHyphenated() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue("Южно-Сахалинск");
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData("Южно-Сахалинск", user.getDate(), user.getName(), user.getPhone());

        $("[data-test-id='success-notification']").shouldBe(visible);
    }

    @Test
    void shouldTestOrderCityNotAdminCenter() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue("Орск");
        form.$("[data-test-id=date] input").sendKeys(Keys.ESCAPE);
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData("Орск", user.getDate(), user.getName(), user.getPhone());

        $("[data-test-id='city'] [class='input__sub']").shouldHave(exactText("Доставка в выбранный город" +
                " недоступна"));
    }

    @Test
    void shouldTestOrderCityLatin() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue("Kazan");
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData("Kazan", user.getDate(), user.getName(), user.getPhone());

        $("[data-test-id='city'] [class='input__sub']").shouldHave(exactText("Доставка в выбранный город" +
                " недоступна"));
    }

    @Test
    void shouldTestOrderDateOld() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", -3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);

        $("[data-test-id='date'] [class='input__sub']").shouldHave(exactText("Заказ на выбранную дату" +
                " невозможен"));
    }

    @Test
    void shouldTestOrderDateActual() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 0);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);

        $("[data-test-id='date'] [class='input__sub']").shouldHave(exactText("Заказ на выбранную дату" +
                " невозможен"));
    }

    @Test
    void shouldTestOrderDateFuture() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 150);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] [value]").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);

        $("[data-test-id='success-notification']").shouldBe(visible);
    }

    @Test
    void shouldTestOrderNameHyphen() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue("Сергеев-Петров Андрей");
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user.getCity(), user.getDate(), "Сергеев-Петров Андрей", user.getPhone());

        $("[data-test-id='success-notification']").shouldBe(visible);
    }

    @Test
    void shouldTestOrderNameLatin() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue("Sirotkin Dmitry");
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user.getCity(), user.getDate(), "Sirotkin Dmitry", user.getPhone());

        $("[data-test-id='name'] [class='input__sub']").shouldHave(exactText("Имя и Фамилия указаные неверно." +
                " Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestOrderPhoneLowLength() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue("123");
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user.getCity(), user.getDate(), user.getName(), "123");

        $("[data-test-id='success-notification']").shouldBe(visible);
    }

    @Test
    void shouldTestOrderPhoneHighLength() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue("+712345678901412");
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user.getCity(), user.getDate(), user.getName(), "+712345678901412");

        $("[data-test-id='success-notification']").shouldBe(visible);
    }

    @Test
    void shouldTestOrderPhoneText() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue("number");
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user.getCity(), user.getDate(), user.getName(), "number");

        $("[data-test-id='success-notification']").shouldBe(visible);
    }

    @Test
    void shouldTestOrderCheckboxOff() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$$("[type = 'button']").last().click();

        printTestData(user);

        $("[data-test-id='agreement'] [class='checkbox__text']").shouldHave(exactText("Я соглашаюсь с" +
                " условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldTestReplanOrder() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);
        refresh();

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(DataGenerator.setDate(10));
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();
        $("[data-test-id='replan-notification'] [type = 'button']").click();

        printTestData(user.getCity(), DataGenerator.setDate(10), user.getName(), user.getPhone());

        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldTestReplanOrderBackToTime() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 10);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);
        refresh();

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(DataGenerator.setDate(5));
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();
        $("[data-test-id='replan-notification'] [type = 'button']").click();

        printTestData(user.getCity(), DataGenerator.setDate(5), user.getName(), user.getPhone());

        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldTestReplanOrderBackToPast() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);
        refresh();

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(DataGenerator.setDate(-3));
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user.getCity(), DataGenerator.setDate(10), user.getName(), user.getPhone());

        $("[data-test-id='date'] [class='input__sub']").shouldHave(exactText("Заказ на выбранную дату" +
                " невозможен"), Duration.ofSeconds(5));
    }

    @Test
    void shouldTestReplanOrderBackToToday() {
        SelenideElement form = $(".form");
        User user = DataGenerator.Registration.generateUser("ru", 3);

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(user.getDate());
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user);
        refresh();

        form.$("[data-test-id = 'city'] input").setValue(user.getCity());
        form.$("[data-test-id = 'date'] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id = 'date'] input").setValue(DataGenerator.setDate(0));
        form.$("[data-test-id = 'name'] input").setValue(user.getName());
        form.$("[data-test-id = 'phone'] input").setValue(user.getPhone());
        form.$("[data-test-id = 'agreement']").click();
        form.$$("[type = 'button']").last().click();

        printTestData(user.getCity(), DataGenerator.setDate(10), user.getName(), user.getPhone());

        $("[data-test-id='date'] [class='input__sub']").shouldHave(exactText("Заказ на выбранную дату" +
                " невозможен"), Duration.ofSeconds(5));
    }
}
