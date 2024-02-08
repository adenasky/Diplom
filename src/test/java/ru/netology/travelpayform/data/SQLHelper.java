package ru.netology.travelpayform.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static DataGenerator.CardStatus getDebitCardStatus() {
        var codeSQL = "SELECT status FROM credit_request_entity";
        var conn = getConn();
        var status = QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataGenerator.CardStatus(status);
    }

    @SneakyThrows
    public static DataGenerator.CardStatus getCreditCardStatus() {
        var codeSQL = "SELECT status FROM credit_request_entity";
        var conn = getConn();
        var status = QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataGenerator.CardStatus(status);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
    }

}
