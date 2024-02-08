package ru.netology.travelpayform.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    /** specific test card numbers */
    private final static String approvedCardNumber = "1111222233334444";
    private final static String declinedCardNumber = "5555666677778888";


    /** generate data */
    public static String generateCardNumber() {
        return faker.numerify("#### #### #### ####");
    }

    public static String generateCardholderName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateCurrentExpirationMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM")); }

    public static String generateValidExpirationMonth() {
        int monthsToCurrent = new Random().nextInt(30);
        return LocalDate.now().plusMonths(monthsToCurrent).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateRandomExpirationMonth() {
        return String.format("%02d", new Random().nextInt(12) + 1);
    }

    public static String generateCurrentExpirationYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateValidExpirationYear() {
        int yearsToCurrent = new Random().nextInt(5);
        return LocalDate.now().plusYears(yearsToCurrent).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateRandomExpirationYear() {
        return String.format("%02d", (LocalDate.now().getYear() + new Random().nextInt(10)) % 100);
    }

    public static String generateSecurityCode() {
        return faker.numerify("###");
    }


    /** generate invalid data */
    public static char generateRandomCyrillicLetter() {
        String cyrillicLetters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        return cyrillicLetters.charAt(new Random().nextInt(cyrillicLetters.length()));
    }

    public static char generateRandomSpecialCharacter() {
        String symbols = "!@#$%^&*()_+-=[]{};':\",.<>/?\\|";
        return symbols.charAt(new Random().nextInt(symbols.length()));
    }

    public static int generateRandomDigit() {
        return new Random().nextInt(10);
    }

    public static String generateEmptyField() {
        return "";
    }

    public static String generateCardNumberMoreThanMaxDigit() {
        return approvedCardNumber + generateRandomDigit();
    }

    public static String generateCardNumberLessThanMaxDigit() {
        return approvedCardNumber.substring(0, approvedCardNumber.length() - 1);
    }

    public static String generateCardNumberWithLetters() {
        return approvedCardNumber.substring(0, approvedCardNumber.length() - 1) + generateRandomCyrillicLetter();
    }

    public static String generateCardNumberWithSpecialCharacters() {
        return approvedCardNumber.substring(0, approvedCardNumber.length() - 1) + generateRandomSpecialCharacter();
    }

    public static String generateExpirationMonthMoreThanMaxDigit() {
        return generateValidExpirationMonth() + generateRandomDigit();
    }

    public static String generateExpirationMonthLessThanMaxDigit() {
        return generateValidExpirationMonth().substring(0, generateValidExpirationMonth().length() - 1);
    }

    public static String generatePreviousExpirationYear() {
        int yearsToCurrent = 1 + new Random().nextInt(30);
        return LocalDate.now().minusYears(yearsToCurrent).format(DateTimeFormatter.ofPattern("yy"));
    }


    /** Generate Cards */
    public static Card generateApprovedCard() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateDeclinedCard() {
        return new Card(declinedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateWithEmptyCardNumber() {
        return new Card(generateEmptyField(),
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateWithEmptyExpirationMonth() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateEmptyField(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateWithEmptyExpirationYear() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateEmptyField(),
                generateSecurityCode());
    }

    public static Card generateWithEmptyCardholderName() {
        return new Card(approvedCardNumber,
                generateEmptyField(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateWithEmptySecurityCode() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateEmptyField());
    }

    public static Card generateInvalidCardNumberMoreThanMaxDigit() {
        return new Card(generateCardNumberMoreThanMaxDigit(),
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidCardNumberLessThanMaxDigit() {
        return new Card(generateCardNumberLessThanMaxDigit(),
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidCardNumberWithLetters() {
        return new Card(generateCardNumberWithLetters(),
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidCardNumberWithSpecialCharacters() {
        return new Card(generateCardNumberWithSpecialCharacters(),
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidMonthMoreThanMaxDigit() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateExpirationMonthMoreThanMaxDigit(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidMonthLessThanMaxDigit() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateExpirationMonthLessThanMaxDigit(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    @Value
    public static class Card {
        String cardNumber;
        String cardholderName;
        String expirationMonth;
        String expirationYear;
        String securityCode;
    }

    @Value
    public static class CardStatus {
        String status;
    }
}
