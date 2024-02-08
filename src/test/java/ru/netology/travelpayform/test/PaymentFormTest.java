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
import static org.junit.Assert.*;

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
        assertEquals("APPROVED", SQLHelper.getDebitCardStatus().getStatus());
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
        assertEquals("DECLINED", SQLHelper.getDebitCardStatus().getStatus());
    }

//    @Test
//    @DisplayName("FORM-N-07 Отправка формы с большим количеством допустимых символов в номере карты")
//    void shouldNotSetMoreThanMaxDigitCardNumber() {
//        DataGenerator.Card cardNumberMoreThanMaxDigit = DataGenerator.generateInvalidCardNumberMoreThanMaxDigit();
//        paymentPage.debitForm().setDebitCard(cardNumberMoreThanMaxDigit);
//        assertTrue(enteredCardNumber.length() = 16);
//    }

    @Test
    @DisplayName("FORM-N-08 Отправка формы с меньшим количеством допустимых символов в номере карты")
    void shouldNotSetLessThanMaxDigitCardNumber() {
        DataGenerator.Card cardNumberLessThanMaxDigit = DataGenerator.generateInvalidCardNumberLessThanMaxDigit();
        paymentPage.debitForm().setDebitCard(cardNumberLessThanMaxDigit);
        debitCardSection.fieldErrorCardNumberNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-09 Отправка формы с невалидными данными, буквы в номере карты")
    void shouldNotSetLettersInCardNumber() {
        DataGenerator.Card cardNumberWithLetters = DataGenerator.generateInvalidCardNumberWithLetters();
        paymentPage.debitForm().setDebitCard(cardNumberWithLetters);
        debitCardSection.fieldErrorCardNumberNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-10 Отправка формы с невалидными данными, спецсимволы в номере карты")
    void shouldNotSetSpecialCharactersInCardNumber() {
        DataGenerator.Card cardNumberWithSpecialCharacters = DataGenerator.generateInvalidCardNumberWithSpecialCharacters();
        paymentPage.debitForm().setDebitCard(cardNumberWithSpecialCharacters);
        debitCardSection.fieldErrorCardNumberNotification("Неверный формат");
    }

//    @Test
//    @DisplayName("FORM-N-11 Отправка формы с большим количеством допустимых символов в месяце")
//    void shouldNotSetMoreThanMaxDigitMonth() {
//        DataGenerator.Card monthMoreThanMaxDigit = DataGenerator.generateExpirationMonthMoreThanMaxDigit();
//        paymentPage.debitForm().setDebitCard(monthMoreThanMaxDigit);
//        assertTrue(enteredCardNumber.length() = 2);
//    }

}