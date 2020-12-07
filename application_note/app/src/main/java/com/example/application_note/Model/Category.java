package com.example.application_note.Model;

public class Category {

//attributes
    private int ID_Cat;
    private  String Name_Categ;


    //constructor
    public Category(String Name_Categ) {
        this.Name_Categ = Name_Categ;
    }

    public Category(int ID_Cat, String name_Cat) {
        this.ID_Cat = ID_Cat;
        this.Name_Categ = name_Cat;
    }

    public Category() {}
        //getters and setters

    public int getID_Cat() {
        return ID_Cat;
    }

    public void setID_Cat(int ID_Cat) {
        this.ID_Cat = ID_Cat;
    }

    public String getName_Cat() {
        return Name_Categ;
    }

    public void setName_Cat(String name_Cat) {
        Name_Categ = name_Cat;
    }
}
