package ru.netology.travelpayform.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.travelpayform.data.DataGenerator;
import ru.netology.travelpayform.page.PaymentPage;
import static com.codeborne.selenide.Selenide.open;

public class PaymentFormTest {
    PaymentPage paymentPage;

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
        paymentPage = open("http://localhost:8080", PaymentPage.class);
    }

    @Test
    void shouldPayWithApprovedCard() {
        DataGenerator.Card approvedCard = DataGenerator.generateApprovedCard();
        paymentPage.debitForm().setDebitCard(approvedCard);
        paymentPage.notificationStatus("Операция одобрена Банком.");
    }
}