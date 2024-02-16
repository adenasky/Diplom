# Процедура запуска автотестов

## 1. Необходимые инструменты
1. IntelliJ IDEA
2. Docker Desktop

## 2. Запуск сервиса
1. Клонировать проект из Git репозитория командой: ```git clone https://github.com/adenasky/Diplom.git```
2. Открыть проект в IntelliJ IDEA
3. Запустить Docker контейнер командой: ```docker-compose up --build```
4. Запустить приложение ```aqa-shop.jar``` командой:```java -jar ./image/aqa-shop.jar```

## 3. Запуск тестов
1. Запустить автотесты командой ```.\gradlew clean test```

## 4. Создание отчета
1. Создать отчет Allure командой ```.\gradlew allureServe```