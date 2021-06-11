package lev.filippov.Entry;

import lev.filippov.ColumnDB;
import lev.filippov.Constraints;
import lev.filippov.TableDB;

@TableDB
public class Cat {

    public Cat() {
    }

    public Cat(double id, String name, int age, String color, String owner) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.color = color;
        this.owner = owner;
    }

    @ColumnDB(constraints = @Constraints(primaryKey = true))
    private double id;

    @ColumnDB(constraints = @Constraints(notNull = true))
    private String name;

    @ColumnDB
    private int age;

    @ColumnDB
    private String color;

    @ColumnDB
    private String owner;
}
