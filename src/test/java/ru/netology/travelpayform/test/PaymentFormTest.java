package ru.netology.travelpayform.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
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

    @Test
    @DisplayName("FORM-N-07 Отправка формы с большим количеством допустимых символов в номере карты")
    void shouldNotSetMoreThanMaxDigitCardNumber() {
        DataGenerator.Card cardNumberMoreThanMaxDigit = DataGenerator.generateInvalidCardNumberMoreThanMaxDigit();
        paymentPage.debitForm().setDebitCard(cardNumberMoreThanMaxDigit);
        Assertions.assertEquals(16, debitCardSection.getFieldCardNumberValue().length());
    }

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

    @Test
    @DisplayName("FORM-N-11 Отправка формы с большим количеством допустимых символов в месяце")
    void shouldNotSetMoreThanMaxDigitMonth() {
        DataGenerator.Card cardWithInvalidMonth = DataGenerator.generateInvalidMonthMoreThanMaxDigit();
        paymentPage.debitForm().setDebitCard(cardWithInvalidMonth);
        Assertions.assertEquals(2, debitCardSection.getFieldExpirationMonthValue().length());
    }

    @Test
    @DisplayName("FORM-N-12 Отправка формы с меньшим количеством допустимых символов в месяце")
    void shouldNotSetLessThanMaxDigitMonth() {
        DataGenerator.Card monthLessThanMaxDigit = DataGenerator.generateInvalidMonthLessThanMaxDigit();
        paymentPage.debitForm().setDebitCard(monthLessThanMaxDigit);
        debitCardSection.fieldErrorExpirationMonthNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-13 Отправка формы с невалидными данными, буквы в месяце")
    void shouldNotSetLettersInMonth() {
        DataGenerator.Card monthWithLetters = DataGenerator.generateInvalidMonthWithLetters();
        paymentPage.debitForm().setDebitCard(monthWithLetters);
        debitCardSection.fieldErrorExpirationMonthNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-14 Отправка формы с невалидными данными, спецсимволы в месяце")
    void shouldNotSetSpecialCharactersInMonth() {
        DataGenerator.Card monthWithSpecialCharacters = DataGenerator.generateInvalidMonthWithSpecialCharacters();
        paymentPage.debitForm().setDebitCard(monthWithSpecialCharacters);
        debitCardSection.fieldErrorExpirationMonthNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-15 Отправка формы с невалидным, предыдущим годом")
    void shouldNotPayWithPreviousYearCard() {
        DataGenerator.Card yearPrevious = DataGenerator.generateInvalidYearPrevious();
        paymentPage.debitForm().setDebitCard(yearPrevious);
        debitCardSection.fieldErrorExpirationYearNotification("Истёк срок действия карты");
    }

    @Test
    @DisplayName("FORM-N-16 Отправка формы с меньшим количеством допустимых символов в году")
    void shouldNotSetLessThanMaxDigitYear() {
        DataGenerator.Card yearLessThanMaxDigit = DataGenerator.generateInvalidYearLessThanMaxDigit();
        paymentPage.debitForm().setDebitCard(yearLessThanMaxDigit);
        debitCardSection.fieldErrorExpirationYearNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-17 Отправка формы с большим количеством допустимых символов в году")
    void shouldNotSetMoreThanMaxDigitYear() {
        DataGenerator.Card cardWithInvalidYear = DataGenerator.generateInvalidYearMoreThanMaxDigit();
        paymentPage.debitForm().setDebitCard(cardWithInvalidYear);
        Assertions.assertEquals(2, debitCardSection.getFieldExpirationYearValue().length());
    }

    @Test
    @DisplayName("FORM-N-18 Отправка формы с невалидными данными, буквы в году")
    void shouldNotSetLettersInYear() {
        DataGenerator.Card yearWithLetters = DataGenerator.generateInvalidYearWithLetters();
        paymentPage.debitForm().setDebitCard(yearWithLetters);
        debitCardSection.fieldErrorExpirationYearNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-19 Отправка формы с невалидными данными, спецсимволы в году")
    void shouldNotSetSpecialCharactersInYear() {
        DataGenerator.Card yearWithSpecialCharacters = DataGenerator.generateInvalidYearWithSpecialCharacters();
        paymentPage.debitForm().setDebitCard(yearWithSpecialCharacters);
        debitCardSection.fieldErrorExpirationYearNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-20 Отправка формы с количеством символом меньше допустимого для поля Владелец")
    void shouldNotPayWithCardholderNameLengthLessThanAcceptable() {
        DataGenerator.Card cardholderNameLessLength = DataGenerator.generateInvalidCardholderNameLessLetters();
        paymentPage.debitForm().setDebitCard(cardholderNameLessLength);
        debitCardSection.fieldErrorCardholderNameNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-21 Отправка формы со значением на кириллице для поля Владелец")
    void shouldNotPayWithCardholderNameCyrillic() {
        DataGenerator.Card cardholderNameCyrillic = DataGenerator.generateInvalidCardholderNameCyrillic();
        paymentPage.debitForm().setDebitCard(cardholderNameCyrillic);
        debitCardSection.fieldErrorCardholderNameNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-22 Отправка формы с невалидными данными, цифры в поле Владелец")
    void shouldNotPayWithCardholderNameWithDigit() {
        DataGenerator.Card cardholderNameWithDigit = DataGenerator.generateCardholderNameWithDigit();
        paymentPage.debitForm().setDebitCard(cardholderNameWithDigit);
        debitCardSection.fieldErrorCardholderNameNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-23 Отправка формы с невалидными данными, спецсимволы в поле Владелец")
    void shouldNotPayWithCardholderNameWithSpecialCharacters() {
        DataGenerator.Card cardholderNameWithSpecialCharacters = DataGenerator.generateCardholderNameWithSpecialCharacters();
        paymentPage.debitForm().setDebitCard(cardholderNameWithSpecialCharacters);
        debitCardSection.fieldErrorCardholderNameNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-24 Отправка формы с большим количеством допустимых символов в CVC/CVV")
    void shouldNotSetMoreThanMaxDigitSecurityCode() {
        DataGenerator.Card cardWithInvalidSecurityCode = DataGenerator.generateInvalidSecurityCodeMoreThanMaxDigit();
        paymentPage.debitForm().setDebitCard(cardWithInvalidSecurityCode);
        Assertions.assertEquals(3, debitCardSection.getFieldSecurityCodeValue().length());
    }

    @Test
    @DisplayName("FORM-N-25 Отправка формы с меньшим количеством допустимых символов в CVC/CVV")
    void shouldNotSetLessThanMaxDigitSecurityCode() {
        DataGenerator.Card securityCodeLessThanMaxDigit = DataGenerator.generateInvalidSecurityCodeLessThanMaxDigit();
        paymentPage.debitForm().setDebitCard(securityCodeLessThanMaxDigit);
        debitCardSection.fieldErrorSecurityCodeNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-26 Отправка формы с невалидными данными, буквы в CVC/CVV")
    void shouldNotSetLettersInSecurityCode() {
        DataGenerator.Card securityCodeWithLetters = DataGenerator.generateInvalidSecurityCodeWithLetters();
        paymentPage.debitForm().setDebitCard(securityCodeWithLetters);
        debitCardSection.fieldErrorSecurityCodeNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-27 Отправка формы с невалидными данными, спецсимволы в CVC/CVV")
    void shouldNotSetSpecialCharactersInSecurityCode() {
        DataGenerator.Card securityCodeWithSpecialCharacters = DataGenerator.generateInvalidSecurityCodeWithSpecialCharacters();
        paymentPage.debitForm().setDebitCard(securityCodeWithSpecialCharacters);
        debitCardSection.fieldErrorSecurityCodeNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-28 Отправка формы с невалидными данными, нули в номере карты")
    void shouldNotSetZeroInCardNumber() {
        DataGenerator.Card cardNumberWithZero = DataGenerator.generateInvalidCardNumberWithZero();
        paymentPage.debitForm().setDebitCard(cardNumberWithZero);
        paymentPage.notificationStatus("Ошибка! Банк отказал в проведении операции.");
        assertEquals("DECLINED", SQLHelper.getDebitCardStatus().getStatus());
    }

    @Test
    @DisplayName("FORM-N-29 Отправка формы с невалидными данными, нули в месяце")
    void shouldNotSetZeroInMonth() {
        DataGenerator.Card monthWithZero = DataGenerator.generateInvalidMonthWithZero();
        paymentPage.debitForm().setDebitCard(monthWithZero);
        debitCardSection.fieldErrorExpirationMonthNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-30 Отправка формы с невалидными данными, нули в году")
    void shouldNotSetZeroInYear() {
        DataGenerator.Card yearWithZero = DataGenerator.generateInvalidYearWithZero();
        paymentPage.debitForm().setDebitCard(yearWithZero);
        debitCardSection.fieldErrorExpirationYearNotification("Неверный формат");
    }

    @Test
    @DisplayName("FORM-N-31 Отправка формы с невалидными данными, нули в CVC/CVV")
    void shouldNotSetZeroInSecurityCode() {
        DataGenerator.Card securityCodeWithZero = DataGenerator.generateInvalidSecurityCodeWithZero();
        paymentPage.debitForm().setDebitCard(securityCodeWithZero);
        debitCardSection.fieldErrorSecurityCodeNotification("Неверный формат");
    }
}