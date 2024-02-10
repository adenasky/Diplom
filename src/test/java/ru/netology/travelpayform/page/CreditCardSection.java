package ru.netology.travelpayform.page;

import ru.netology.travelpayform.data.DataGenerator;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditCardSection {
    private SelenideElement fieldCardNumber = $("input.input__control[placeholder='0000 0000 0000 0000']");
    private SelenideElement fieldExpirationMonth = $("input.input__control[placeholder='08']");
    private SelenideElement fieldExpirationYear = $("input.input__control[placeholder='22']");
    private SelenideElement fieldCardholderName = $$(".input-group__input-case").findBy(text("Владелец")).$(".input__control");
    private SelenideElement fieldSecurityCode = $$(".input-group__input-case").findBy(text("CVC/CVV")).$(".input__control");
    private SelenideElement buttonContinue = $$(".button__text").findBy(text("Продолжить"));

    private SelenideElement fieldErrorCardNumber = $$(".form-field .input_invalid").findBy(text("Номер карты")).$(".input__sub");
    private SelenideElement fieldErrorExpirationMonth = $$(".form-field .input_invalid").findBy(text("Месяц")).$(".input__sub");
    private SelenideElement fieldErrorExpirationYear = $$(".form-field .input_invalid").findBy(text("Год")).$(".input__sub");
    private SelenideElement fieldErrorCardholderName = $$(".form-field .input_invalid").findBy(text("Владелец")).$(".input__sub");
    private SelenideElement fieldErrorSecurityCode = $$(".form-field .input_invalid").findBy(text("CVC/CVV")).$(".input__sub");

    public void setCreditCard(DataGenerator.Card card) {
        fieldCardNumber.setValue(card.getCardNumber());
        fieldExpirationMonth.setValue(card.getExpirationMonth());
        fieldExpirationYear.setValue(card.getExpirationYear());
        fieldCardholderName.setValue(card.getCardholderName());
        fieldSecurityCode.setValue(card.getSecurityCode());
        buttonContinue.click();
    }

    public void fieldErrorCardNumberNotification(String expectedText) {
        fieldErrorCardNumber.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public void fieldErrorExpirationMonthNotification(String expectedText) {
        fieldErrorExpirationMonth.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public void fieldErrorExpirationYearNotification(String expectedText) {
        fieldErrorExpirationYear.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public void fieldErrorCardholderNameNotification(String expectedText) {
        fieldErrorCardholderName.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public void fieldErrorSecurityCodeNotification(String expectedText) {
        fieldErrorSecurityCode.shouldHave(exactText(expectedText)).shouldBe(visible);
    }
}
