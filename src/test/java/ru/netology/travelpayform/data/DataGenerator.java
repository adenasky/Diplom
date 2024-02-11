package ru.netology.travelpayform.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));
    private static final Faker cyrillicFaker = new Faker(new Locale("ru"));

    private DataGenerator() {
    }

    /**
     * specific test card numbers
     */
    private final static String approvedCardNumber = "1111 2222 3333 4444";
    private final static String declinedCardNumber = "5555 6666 7777 8888";


    /**
     * generate data
     */
    public static String generateRandomCardNumber() {
        return faker.numerify("#### #### #### ####");
    }

    public static String generateCardholderName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateCyrillicCardholderName() {
        return cyrillicFaker.name().firstName() + " " + cyrillicFaker.name().lastName();
    }

    public static String generateCurrentExpirationMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateValidExpirationMonth() {
        LocalDate currentDate = LocalDate.now();
        int remainingMonths = 12 - currentDate.getMonthValue();
        int monthsToFuture = new Random().nextInt(remainingMonths);
        LocalDate futureDate = currentDate.plusMonths(monthsToFuture);
        return futureDate.format(DateTimeFormatter.ofPattern("MM"));
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


    /**
     * generate invalid data
     */
    public static char generateRandomCyrillicLetter() {
        String cyrillicLetters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        return cyrillicLetters.charAt(new Random().nextInt(cyrillicLetters.length()));
    }

    public static String generateTwoRandomLatinLettersWithSpace() {
        String latinLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int index1 = new Random().nextInt(latinLetters.length());
        int index2 = new Random().nextInt(latinLetters.length());
        return latinLetters.charAt(index1) + " " + latinLetters.charAt(index2);
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

    public static String generateTwoZero() {
        return "00";
    }

    public static String generateThreeZero() {
        return "000";
    }

    public static String generate16Zero() {
        return "0000000000000000";
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

    public static String generateExpirationMonthWithLetters() {
        return generateValidExpirationMonth().substring(0, generateValidExpirationMonth().length() - 1) + generateRandomCyrillicLetter();
    }

    public static String generateExpirationMonthWithSpecialCharacters() {
        return generateValidExpirationMonth().substring(0, generateValidExpirationMonth().length() - 1) + generateRandomSpecialCharacter();
    }

    public static String generatePreviousExpirationYear() {
        int yearsToCurrent = 1 + new Random().nextInt(20);
        return LocalDate.now().minusYears(yearsToCurrent).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateInvalidExpirationYearLessThanMaxDigit() {
        return generateValidExpirationYear().substring(0, generateValidExpirationYear().length() - 1);
    }

    public static String generateInvalidExpirationYearMoreThanMaxDigit() {
        return generateValidExpirationYear() + generateRandomDigit();
    }

    public static String generateInvalidExpirationYearWithLetters() {
        return generateValidExpirationYear().substring(0, generateValidExpirationYear().length() - 1) + generateRandomCyrillicLetter();
    }

    public static String generateInvalidExpirationYearWithSpecialCharacters() {
        return generateValidExpirationYear().substring(0, generateValidExpirationYear().length() - 1) + generateRandomSpecialCharacter();
    }

    public static String generateInvalidCardholderNameWithDigit() {
        return generateCardholderName() + generateRandomDigit();
    }

    public static String generateInvalidCardholderWithSpecialCharacters() {
        return generateCardholderName() + generateRandomSpecialCharacter();
    }

    public static String generateSecurityCodeMoreThanMaxDigit() {
        return generateSecurityCode() + generateRandomDigit();
    }

    public static String generateSecurityCodeLessThanMaxDigit() {
        return generateSecurityCode().substring(0, generateSecurityCode().length() - 1);
    }

    public static String generateSecurityCodeWithLetters() {
        return generateSecurityCode().substring(0, generateSecurityCode().length() - 1) + generateRandomCyrillicLetter();
    }

    public static String generateSecurityCodeWithSpecialCharacters() {
        return generateSecurityCode().substring(0, generateSecurityCode().length() - 1) + generateRandomSpecialCharacter();
    }

    /**
     * Generate Cards
     */
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

    public static Card generateInvalidMonthWithLetters() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateExpirationMonthWithLetters(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidMonthWithSpecialCharacters() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateExpirationMonthWithSpecialCharacters(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidYearPrevious() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generatePreviousExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidYearLessThanMaxDigit() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateInvalidExpirationYearLessThanMaxDigit(),
                generateSecurityCode());
    }

    public static Card generateInvalidYearMoreThanMaxDigit() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateInvalidExpirationYearMoreThanMaxDigit(),
                generateSecurityCode());
    }

    public static Card generateInvalidYearWithLetters() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateInvalidExpirationYearWithLetters(),
                generateSecurityCode());
    }

    public static Card generateInvalidYearWithSpecialCharacters() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateInvalidExpirationYearWithSpecialCharacters(),
                generateSecurityCode());
    }

    public static Card generateInvalidCardholderNameLessLetters() {
        return new Card(approvedCardNumber,
                generateTwoRandomLatinLettersWithSpace(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidCardholderNameCyrillic() {
        return new Card(approvedCardNumber,
                generateCyrillicCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateCardholderNameWithDigit() {
        return new Card(approvedCardNumber,
                generateInvalidCardholderNameWithDigit(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateCardholderNameWithSpecialCharacters() {
        return new Card(approvedCardNumber,
                generateInvalidCardholderWithSpecialCharacters(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidSecurityCodeMoreThanMaxDigit() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCodeMoreThanMaxDigit());
    }

    public static Card generateInvalidSecurityCodeLessThanMaxDigit() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCodeLessThanMaxDigit());
    }

    public static Card generateInvalidSecurityCodeWithLetters() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCodeWithLetters());
    }

    public static Card generateInvalidSecurityCodeWithSpecialCharacters() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCodeWithSpecialCharacters());
    }

    public static Card generateInvalidCardNumberWithZero() {
        return new Card(generate16Zero(),
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidMonthWithZero() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateTwoZero(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    public static Card generateInvalidYearWithZero() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateTwoZero(),
                generateSecurityCode());
    }

    public static Card generateInvalidSecurityCodeWithZero() {
        return new Card(approvedCardNumber,
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateThreeZero());
    }

    public static Card generateInvalidCardNumberRandom() {
        return new Card(generateRandomCardNumber(),
                generateCardholderName(),
                generateValidExpirationMonth(),
                generateValidExpirationYear(),
                generateSecurityCode());
    }

    @Value
    public static class Card {
        String number;
        String holder;
        String month;
        String year;
        String cvc;
    }

    @Value
    public static class CardStatus {
        String status;
    }
}
