package com.example.application_note.Model;


public class Color {


    private Long ID_Color;
    private String Name_Color;

    public Color() {
    }

    public Color(Long ID_Color) {
        this.ID_Color = ID_Color;
    }

    public Color(String name_Color) {
        Name_Color = name_Color;
    }

    public Long getID_Color() {
        return ID_Color;
    }

    public String getName_Color() {
        return Name_Color;
    }

    public void setName_Color(String name_Color) {
        Name_Color = name_Color;
    }

    public void setID_Color(Long ID_Color) {
        this.ID_Color = ID_Color;
    }

}
