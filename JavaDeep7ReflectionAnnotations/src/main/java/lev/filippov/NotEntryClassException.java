package lev.filippov;

import java.sql.SQLException;

public class NotEntryClassException extends SQLException {
    @Override
    public String getMessage() {
        return "Класс не размечен аннотацией как пригодный для создания таблицы!" ;
    }
}
