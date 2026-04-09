package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connectToDB() {
        try {
            connection = DriverManager.getConnection(
                    ConfigReader.read("dbUrl"),
                    ConfigReader.read("dbUsername"),
                    ConfigReader.read("dbPassword")
            );
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static List<Map<String, String>> fetch(String query) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(query);
            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                Map<String, String> row = new LinkedHashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    row.put(resultSet.getMetaData().getColumnLabel(i),
                            resultSet.getString(i));
                }

                data.add(row);
            }

        } catch (Exception e) {
            throw new RuntimeException("Query execution failed: " + query, e);
        }

        return data;
    }

    public static void closeDBConnection() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            throw new RuntimeException("Closing DB connection failed", e);
        }
    }
}