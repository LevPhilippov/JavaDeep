package lev.filippov.Entry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Friend {

    private Connection connection;
    private Statement statement;
    private static String url;
    private static String password;
    private static String login;





    private static void prepareConnection(Connection connection) throws Exception {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url,login, password);
    }
    private static String createTableFromAnnotatedEntry(Class<?> clazz){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
