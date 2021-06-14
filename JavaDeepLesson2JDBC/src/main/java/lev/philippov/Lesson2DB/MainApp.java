package lev.philippov.Lesson2DB;

import java.sql.*;

public class MainApp {

    public static String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=lesson";
    public static String user = "postgres";
    public static String password = "admin";
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;


    public static void main(String[] args) throws SQLException {
        prepareConnection();
        prepareStatements();
        usePS();
        autoCommitAndButchComparision();
    }

    private static void autoCommitAndButchComparision() throws SQLException {
        long s = System.currentTimeMillis();
        connection.setAutoCommit(false);
        for (int i=0;i<10000;i++){
            preparedStatement.setString(1, "Obj"+ i);
            preparedStatement.setInt(2,i % 100);
            preparedStatement.executeUpdate();
        }
        connection.setAutoCommit(true);
        System.out.println("Время на исполенение операции autocommit=false: " + (System.currentTimeMillis()-s));

        s = System.currentTimeMillis();
        for (int i=0;i<10000;i++){
            preparedStatement.setString(1, "Obj"+ i);
            preparedStatement.setInt(2,i % 100);
            preparedStatement.executeUpdate();
        }

        System.out.println("Время на исполенение операции autocommit=true: " + (System.currentTimeMillis()-s));

        s = System.currentTimeMillis();
        for (int i=0;i<10000;i++){
            preparedStatement.setString(1, "Obj"+ i);
            preparedStatement.setInt(2,i % 100);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        System.out.println("Время на исполенение пакетной операции autocommit=true, : " + (System.currentTimeMillis()-s));
    }

    private static void usePS() throws SQLException {
        preparedStatement.setString(1,"John");
        preparedStatement.setInt(2,15);
        preparedStatement.executeUpdate();
    }

    private static void prepareConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(url,user,password);
    }

    private static void prepareStatements() throws SQLException {
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("INSERT INTO table1 (name, score) values (?, ?)");
        statement.executeUpdate(Statements.statement);
    }

}
