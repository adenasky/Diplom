package ru.netology.travelpayform.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.travelpayform.data.DataGenerator;
import ru.netology.travelpayform.data.SQLHelper;
import ru.netology.travelpayform.page.DebitCardSection;
import ru.netology.travelpayform.page.PaymentPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;

public class PaymentFormTest {
    PaymentPage paymentPage;
    DebitCardSection debitCardSection;

    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        SQLHelper.cleanDatabase();
        paymentPage = open("http://localhost:8080", PaymentPage.class);
        debitCardSection = new DebitCardSection();
    }

    @Test
    @DisplayName("NAV-F-01 Открытие дебетовой формы по кнопке Купить")
    void shouldOpenDebitForm() {
        paymentPage.debitForm();
        paymentPage.debitHeading("Оплата по карте");
    }

    @Test
    @DisplayName("NAV-F-02 Открытие кредитной формы по кнопке Купить")
    void shouldOpenCreditForm() {
        paymentPage.creditForm();
        paymentPage.creditHeading("Кредит по данным карты");
    }

    @Test
    @DisplayName("FORM-P-01 Отправка формы с валидными данными, APPROVED карта")
    void shouldPayWithApprovedCard() {
        DataGenerator.Card approvedCard = DataGenerator.generateApprovedCard();
        paymentPage.debitForm().setDebitCard(approvedCard);
        paymentPage.notificationStatus("Операция одобрена Банком.");
        assertEquals("APPROVED", SQLHelper.getDebitCardStatus());
    }

    @Test
    @DisplayName("FORM-N-01 Отправка формы с незаполненными данными карты")
    void shouldDisplayErrorForEmptyCardNumber() {
        DataGenerator.Card emptyCardNumber = DataGenerator.generateWithEmptyCardNumber();
        paymentPage.debitForm().setDebitCard(emptyCardNumber);
        debitCardSection.fieldErrorCardNumberNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-02 Отправка формы с незаполненным месяцем")
    void shouldDisplayErrorForEmptyExpirationMonth() {
        DataGenerator.Card emptyExpirationMonth = DataGenerator.generateWithEmptyExpirationMonth();
        paymentPage.debitForm().setDebitCard(emptyExpirationMonth);
        debitCardSection.fieldErrorExpirationMonthNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-03 Отправка формы с незаполненным годом")
    void shouldDisplayErrorForEmptyExpirationYear() {
        DataGenerator.Card emptyExpirationYear = DataGenerator.generateWithEmptyExpirationYear();
        paymentPage.debitForm().setDebitCard(emptyExpirationYear);
        debitCardSection.fieldErrorExpirationYearNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-04 Отправка формы с незаполненным владельцем")
    void shouldDisplayErrorForEmptyCardholderName() {
        DataGenerator.Card emptyCardholderName = DataGenerator.generateWithEmptyCardholderName();
        paymentPage.debitForm().setDebitCard(emptyCardholderName);
        debitCardSection.fieldErrorCardholderNameNotification("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("FORM-N-05 Отправка формы с незаполненным CVC/CVV")
    void shouldDisplayErrorForEmptySecurityCode() {
        DataGenerator.Card emptySecurityCode = DataGenerator.generateWithEmptySecurityCode();
        paymentPage.debitForm().setDebitCard(emptySecurityCode);
        debitCardSection.fieldErrorSecurityCodeNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-06 Отправка формы с валидными данными, DECLINED карта")
    void shouldNotPayWithDeclinedCard() {
        DataGenerator.Card declinedCard = DataGenerator.generateDeclinedCard();
        paymentPage.debitForm().setDebitCard(declinedCard);
        paymentPage.notificationStatus("Ошибка! Банк отказал в проведении операции.");
    }

//    @Test
//    @DisplayName("FORM-N-07 Отправка формы с большим количеством допустимых символов в номере карты")
//    void shouldNotSetMoreThanMaxDigit() {
//        DataGenerator.Card cardNumberMoreThanMaxDigit = DataGenerator.generateCardNumberMoreThanMaxDigit();
//        paymentPage.debitForm().setDebitCard(cardNumberMoreThanMaxDigit);
//        debitCardSection.fieldErrorCardNumberNotification("Неверный формат");
//    }

    @Test
    @DisplayName("FORM-N-08 Отправка формы с меньшим количеством допустимых символов в номере карты")
    void shouldNotSetLessThanMaxDigit() {
        DataGenerator.Card cardNumberLessThanMaxDigit = DataGenerator.generateCardNumberLessThanMaxDigit();
        paymentPage.debitForm().setDebitCard(cardNumberLessThanMaxDigit);
        debitCardSection.fieldErrorCardNumberNotification("Неверный формат");
    }


}