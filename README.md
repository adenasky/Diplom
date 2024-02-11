# Процедура запуска автотестов

## 1. Необходимые инструменты
1. IntelliJ IDEA
2. Docker Desktop

## 2. Запуск сервиса
1. Клонировать проект из Git репозитория командой: ```git clone https://github.com/adenasky/Diplom.git```
2. Открыть проект в IntelliJ IDEA
3. Запустить Docker контейнер командой: ```docker-compose up --build```
4. Запустить приложение ```aqa-shop.jar```, с настройкой подключения к базе данных PostgreSQL, командой:```java -jar ./image/aqa-shop.jar -P:jdbc.url=jdbc:postgresql://localhost:5432/app  -port=8080```

## 3. Запуск тестов
1. Запустить автотесты командой ```.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app```

## 4. Создание отчета
1. Создать отчет Allure командой ```.\gradlew allureServe```