package lev.filippov.Huburnote;

import lev.filippov.Huburnote.Entry.Cat;
import lev.filippov.Huburnote.Entry.FriendDB;

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
