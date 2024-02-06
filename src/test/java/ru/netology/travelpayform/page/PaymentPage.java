package ru.netology.travelpayform.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import java.time.Duration;


public class PaymentPage {
        private SelenideElement buttonPayByDebit = $$(".button__text").findBy(exactText("Купить"));
        private SelenideElement buttonPayByCredit = $$(".button__text").findBy(exactText("Купить в кредит"));
        private SelenideElement headingForDebit = $(By.xpath("//h3[text()='Оплата по карте']"));
        private SelenideElement headingForCredit = $(By.xpath("//h3[text()='Кредит по данным карты']"));

        private SelenideElement notification = $(".notification__content");

        public DebitCardSection debitForm() {
                buttonPayByDebit.click();
                return new DebitCardSection();
        }

        public CreditCardSection creditForm() {
                buttonPayByCredit.click();
                return new CreditCardSection();
        }

        public void notificationStatus(String expectedText) {
                notification.shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText(expectedText));
        }
}