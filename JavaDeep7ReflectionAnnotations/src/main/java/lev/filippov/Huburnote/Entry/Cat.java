package lev.filippov.Huburnote.Entry;

import lev.filippov.Huburnote.ColumnDB;
import lev.filippov.Huburnote.Constraints;
import lev.filippov.Huburnote.TableDB;

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

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public String getOwner() {
        return owner;
    }
}
