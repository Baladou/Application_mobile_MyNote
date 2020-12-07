package com.example.application_note.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.application_note.Helper.DBHelper;
import com.example.application_note.Model.Note;
import com.example.application_note.R;
import com.example.application_note.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoCat_activ extends AppCompatActivity {
    DBHelper controllerdb = new DBHelper(this);
    List<Note> list;
    private RecyclerView itemlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_cat_activ);
        itemlist = findViewById(R.id.item_list_cat);
        display_NoCat();
    }


    public void display_NoCat() {
        list = new ArrayList<>();
        list = controllerdb.getAllNotesNoCat();


        ItemAdapter itemAdapter = new ItemAdapter(getBaseContext(), list, NoCat_activ.this, controllerdb);
        itemlist.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        itemlist.setAdapter(itemAdapter);
    }
}
