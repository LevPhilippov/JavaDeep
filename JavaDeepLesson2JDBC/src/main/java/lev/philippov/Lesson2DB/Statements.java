package lev.philippov.Lesson2DB;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Statements {
    public static String statement;
    public static final String statement1 = "DROP TABLE IF EXISTS table1;";
    public static final String statement2 =
            "CREATE TABLE table1(" +
            "id serial primary key," +
            "name text not null," +
            "score text not null);";

    static {
        prepareStatements();
    }

    public static void prepareStatements(){
        File statements = new File("JavaDeepLesson2JDBC\\src\\main\\resources\\statements.txt");

        try(FileReader reader = new FileReader(statements);) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
                while(bufferedReader.ready()) {
                   sb.append(bufferedReader.readLine());
                }
                statement = sb.toString();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(statements.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
