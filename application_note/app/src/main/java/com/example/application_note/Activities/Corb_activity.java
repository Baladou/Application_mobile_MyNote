package com.example.application_note.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.example.application_note.Helper.DBHelper;
import com.example.application_note.Model.Note;
import com.example.application_note.R;
import com.example.application_note.adapter.CorbAdapter;

import java.util.ArrayList;
import java.util.List;

public class Corb_activity extends AppCompatActivity {
    DBHelper controllerdb = new DBHelper(this);
    List<Note> list;
    private RecyclerView itemlist;
    long id_corb;
    String del="no";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corb_activity);
        itemlist = findViewById(R.id.item_list_corb);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id_corb= -1;
            } else {

                del=extras.getString("restore","no");
                final String delete =del;
                id_corb= extras.getLong("id_corb");
                Note note=controllerdb.getCorb(id_corb);
                controllerdb.createNote(note);
                controllerdb.deleteCorb(id_corb);

                if(delete.equals("yes")){
                    id_corb= extras.getLong("id_note");
                             }
            }
        } else {
            id_corb= (long) savedInstanceState.getSerializable("id_note");
        }
        display_corb();

    }


    public void display_corb() {
        list = new ArrayList<>();
        list = controllerdb.getAllCorb();


        CorbAdapter itemAdapter = new CorbAdapter(getBaseContext(), list, Corb_activity.this, controllerdb);
        itemlist.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        itemlist.setAdapter(itemAdapter);
    }
    @Override
    public void onBackPressed() {

            // super.onBackPressed();
            Intent intent = new Intent(Corb_activity.this, MainActivity.class);

            startActivity(intent);
            finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.empty: {
                openDialog_del();
                return false;
            }


            default:

                return super.onOptionsItemSelected(item);

        }

    }

    private void openDialog_del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Corb_activity.this);
        LayoutInflater inflater = Corb_activity.this.getLayoutInflater();
        builder.setMessage("Do you want to empty the recycle bin?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               /* Intent ac =new Intent(update_note.this, Main2Activity.class);
                startActivity(ac);*/
                dialogInterface.cancel();
                finish();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                controllerdb.deleteAllCorb();
                display_corb();
            }
        });



        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    }


