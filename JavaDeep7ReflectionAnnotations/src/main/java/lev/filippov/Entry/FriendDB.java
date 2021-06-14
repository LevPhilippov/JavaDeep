package lev.filippov.Entry;

import lev.filippov.ColumnDB;
import lev.filippov.NotEntryClassException;
import lev.filippov.TableDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class FriendDB {

    private Connection connection;
    private Statement statement;
    private String classOfDataBase;
    private String url;
    private String password;
    private String user;
    private static Map<String, String> typesMap;
    static {
        typesMap = new HashMap<String, String>(){
            {   put("INT","NUMERIC");
                put("STRING","TEXT");
                put("PRIMARY KEY", "SERIAL");
            }
        };
    }


    public FriendDB() throws Exception {
        prepareConnection();
    }

    private void prepareConnection() throws Exception {
        prepareConnectionData();
        Class.forName(classOfDataBase);
        connection = DriverManager.getConnection(url, user, password);
    }
    private static String createTableFromAnnotatedEntry(Class<?> clazz){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private void prepareConnectionData() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("JavaDeep7ReflectionAnnotations/src/main/resources/DBData.txt"));
        List<String> list = new ArrayList<>();
        while(br.ready()) {
            list.add(br.readLine());
        }
        for (String s : list) {
            String[] strings = s.split("\\s",2);
            switch (strings[0].trim()) {
                case("classDB"):
                    classOfDataBase = strings[1].trim();
                case("url"):
                    url= strings[1].trim();
                case("user"):
                    user=strings[1].trim();
                case("password"):
                    password = strings[1].trim();
            }
        }
        System.out.println(classOfDataBase + "|"+ url + "|" + user + "|" + password);
        Class.forName(classOfDataBase);
        connection=DriverManager.getConnection(url,user,password);
    }

    private static String constructSQLCreationQuery(Class clazz) throws SQLException {
        //Проверка на предмет наличия аннотации шаблона для разметки
        Annotation annotation = clazz.getAnnotation(TableDB.class);
        if(annotation == null){
            throw new NotEntryClassException();
        }
        //Создаем stringbuilder для построния таблицы
        StringBuilder SQL = new StringBuilder();
        SQL.append("CREATE TABLE ");
        //Когда проверка пройдена вытаскиваем имя класса из аннотации или используем имя класса
        TableDB tableDB = (TableDB) clazz.getAnnotation(TableDB.class);
        if (tableDB.tableName().length()<1) {
            SQL.append(clazz.getSimpleName().toUpperCase(Locale.ROOT) + " (\n");
        } else SQL.append(tableDB.tableName().toUpperCase(Locale.ROOT) + " (\n");
        //Сейчас запрос вида CREATE TABLE [TABLE NAME] (
        //Запрашиваем поля и итерируем их на нличие аннотаций для разметки полей SQL таблицы

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            Annotation fieldAnnotation = f.getAnnotation(ColumnDB.class);
            if(fieldAnnotation == null) continue;
            ColumnDB columnDB = (ColumnDB) fieldAnnotation;
            if(columnDB.columnName().length()<1)
                SQL.append(f.getName().toUpperCase(Locale.ROOT) + " ");
            else SQL.append(columnDB.columnName().toUpperCase(Locale.ROOT) + " ");
            //добавляем тип поля
            if(columnDB.constraints().primaryKey()) {
                SQL.append(typesMap.get("PRIMARY KEY") + " ");
            } else SQL.append(typesMap.get(f.getType().getSimpleName().toUpperCase(Locale.ROOT)) + " ");
            //проверяем наличие дополнительных модификаторов
                if(columnDB.constraints().notNull())
                    SQL.append("NOT NULL ");
                if(columnDB.constraints().unique())
                    SQL.append("UNIQUE ");
                if(columnDB.constraints().primaryKey())
                    SQL.append("PRIMARY KEY ");
                //убираем последний пробел и ставим запятую
                SQL.setLength(SQL.length()-1);
                SQL.append(",\n");
        }
        SQL.setLength(SQL.length()-2);
        SQL.append(")");
        return SQL.toString();
    }

    public void constructTableFromEntry(Class clazz) throws SQLException {
        connection.createStatement().execute(constructSQLCreationQuery(clazz));
    }

    public void addEntry(Object obj) throws SQLException {
//        return constructSQLAddQuery(obj);
        connection.createStatement().execute(constructSQLAddQuery(obj));
    }

    private String constructSQLAddQuery(Object obj) throws SQLException{
        Class clazz = obj.getClass();
        Annotation annotation = clazz.getAnnotation(TableDB.class);
        if(annotation==null)
            throw new NotEntryClassException();

        StringBuilder SQL = new StringBuilder();
        SQL.append("INSERT INTO ");

        TableDB tableDB = (TableDB) annotation;

        if(tableDB.tableName().length()<1)
            SQL.append(clazz.getSimpleName().toUpperCase(Locale.ROOT) + " (");
        else SQL.append(tableDB.tableName().toUpperCase(Locale.ROOT) + " (");


        List<Object> list = new ArrayList<Object>();

        for (Field f : clazz.getDeclaredFields()) {
            Annotation fieldAnnotation = f.getAnnotation(ColumnDB.class);
            if(fieldAnnotation==null)
                continue;
            ColumnDB columnDB = (ColumnDB) fieldAnnotation;
            if(columnDB.columnName().length()<1)
                SQL.append(f.getName().toUpperCase(Locale.ROOT) + ", ");
            else SQL.append(columnDB.columnName().toUpperCase(Locale.ROOT) + ", ");

            try {
                f.setAccessible(true);
                Object takenObj = f.get(obj);
                if (takenObj instanceof String) {
                    takenObj = new StringBuilder().append("'").append((String) takenObj).append("'").toString();
                }
                list.add(takenObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        SQL.setLength(SQL.length()-2);
        SQL.append(") values (");

        for (int i =0; i<list.size();i++) {
            SQL.append((list.get(i) + ", "));
        }
        SQL.setLength(SQL.length()-2);
        SQL.append(");");
        return SQL.toString();
    }
}
