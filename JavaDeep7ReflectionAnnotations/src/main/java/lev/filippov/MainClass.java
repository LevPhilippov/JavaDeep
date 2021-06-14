package lev.filippov;

import lev.filippov.Entry.Cat;
import lev.filippov.Entry.FriendDB;

import java.lang.reflect.Field;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args){
        try{
          FriendDB friendDB = new FriendDB();
//          friendDB.constructTableFromEntry(Cat.class);
//          System.out.println((friendDB.addEntry(new Cat(5,"Bars",7, "White", "Me"))));
            friendDB.addEntry(new Cat(5,"Bars",7, "White", "Me"));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
