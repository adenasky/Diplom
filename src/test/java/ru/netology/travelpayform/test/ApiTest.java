package ru.netology.travelpayform.test;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.travelpayform.data.DataGenerator;
import ru.netology.travelpayform.data.ApiHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTest {

    @Test
    @DisplayName("FORM-API-01 Проверка статуса APPROVED дебетовой карты")
    void shouldCheckStatusDebitApprovedCard() {
        DataGenerator.Card approvedCard = DataGenerator.generateApprovedCard();
        final String response = ApiHelper.fillFormWithDebitCardData(approvedCard);
        assertTrue(response.contains("APPROVED"), "True if status is approved");
    }

    @Test
    @DisplayName("FORM-API-02 Проверка статуса DECLINED дебетовой карты")
    void shouldCheckStatusDebitDeclinedCard() {
        DataGenerator.Card declinedCard = DataGenerator.generateDeclinedCard();
        final String response = ApiHelper.fillFormWithDebitCardData(declinedCard);
        assertTrue(response.contains("DECLINED"), "True if status is declined");
    }

    @Test
    @DisplayName("FORM-API-03 Проверка статуса APPROVED кредитной карты")
    void shouldCheckStatusCreditApprovedCard() {
        DataGenerator.Card approvedCard = DataGenerator.generateApprovedCard();
        final String response = ApiHelper.fillFormWithCreditCardData(approvedCard);
        assertTrue(response.contains("APPROVED"), "True if status is approved");
    }

    @Test
    @DisplayName("FORM-API-04 Проверка статуса DECLINED кредитной карты")
    void shouldCheckStatusCreditDeclinedCard() {
        DataGenerator.Card declinedCard = DataGenerator.generateDeclinedCard();
        final String response = ApiHelper.fillFormWithCreditCardData(declinedCard);
        assertTrue(response.contains("DECLINED"), "True if status is declined");
    }
}
