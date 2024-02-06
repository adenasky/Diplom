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

    private final static String approvedCardNumber = "1111222233334444";
    private final static String declinedCardNumber = "5555666677778888";

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
        int yearsToCurrent = new Random().nextInt(10);
        return LocalDate.now().plusYears(yearsToCurrent).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateRandomExpirationYear() {
        return String.format("%02d", (LocalDate.now().getYear() + new Random().nextInt(10)) % 100);
    }

    public static String generateSecurityCode() {
        return faker.numerify("###");
    }

    public static String statusCardApproved() {
        return "APPROVED";
    }
    public static String statusCardDeclined() {
        return "DECLINED";
    }

    //Invalid data
    public static String generateInvalidCardNumberMoreThanMaxDigit() {
        String digit = String.valueOf(new Random().nextInt(10));
        return approvedCardNumber + digit;
    }

    public static String generateInvalidCardNumberLessThanMinDigit() {
        return approvedCardNumber.substring(0, approvedCardNumber.length() - 1);
    }

    public static String generateInvalidPreviousExpirationYear() {
        int yearsToCurrent = 1 + new Random().nextInt(30);
        return LocalDate.now().minusYears(yearsToCurrent).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static char generateRandomCyrillicLetter() {
        String cyrillicLetters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        return cyrillicLetters.charAt(new Random().nextInt(cyrillicLetters.length()));
    }

    public static char generateRandomSymbol() {
        String symbols = "!@#$%^&*()_+-=[]{};':\",.<>/?\\|";
        return symbols.charAt(new Random().nextInt(symbols.length()));
    }

    public static int generateRandomDigit() {
        return new Random().nextInt(10);
    }

    public static String generateEmptyField() {
        return "";
    }

    //Generate Cards
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

    @Value
    public static class Card {
        String cardNumber;
        String cardholderName;
        String expirationMonth;
        String expirationYear;
        String securityCode;
    }
}
