package com.example.application_note.Model;

public class Note {


       //attributes

    private  int ID_Note;
    private  String Name_Note;
    private String text;
    private String created_at;
    private  String Favourite;
    //foreign key!
    private int ID_Categ;
    private Long ID_Col;
    private String couleur;


    //constructors

    public Note(String name, String text,String created_at,int ID_Categ,String favourite) {

        this.Name_Note = name;
        this.text = text;
        this.Favourite=favourite;
        this.created_at = created_at;
        this.ID_Categ=ID_Categ;
    }
    public Note(String name, String text,String created_at,int ID_Categ,String favourite,String couleur) {

        this.Name_Note = name;
        this.text = text;
        this.Favourite=favourite;
        this.created_at = created_at;
        this.ID_Categ=ID_Categ;
        this.couleur=couleur;
    }
  public Note(String name, String text,String favourite,String created_at) {

        this.Name_Note = name;
        this.text = text;
        this.Favourite=favourite;
        this.created_at = created_at;
        //this.ID_Col=ID_Col;
    }


   /* public Note( String name, String text, String created_at, int ID_Categ,String couleur) {

        this.Name_Note = name;
        this.text = text;
        this.created_at = created_at;
        this.ID_Categ=ID_Categ;
        this.couleur=couleur;

    }

    public Note( String name, String text, String created_at,int ID_Categ, String Favourite,Long ID_Col) {
       // this.ID_Note = ID;
        this.Name_Note = name;
        this.text = text;
        this.created_at = created_at;
        this.Favourite=Favourite;
        this.ID_Categ=ID_Categ;
        this.ID_Col=ID_Col;
    }*/

    public Note(){}

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    //getters and setters
    public int getID() {
        return ID_Note;
    }

    public void setID(int ID) {
        this.ID_Note = ID;
    }

    public String getName() {
        return Name_Note;
    }

    public void setName(String name) {
        Name_Note = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }




    public String  getFavourite() {
        return Favourite;
    }

    public void setFavourite(String favourite) {
        Favourite = favourite;
    }

    public int getID_Categ() {
        return ID_Categ;
    }

    public void setID_Categ(int ID_Categ) {
        this.ID_Categ = ID_Categ;
    }


    public Long getID_Col() {
        return ID_Col;
    }

    public void setID_Col(Long ID_Col) {
        this.ID_Col = ID_Col;
    }
}
