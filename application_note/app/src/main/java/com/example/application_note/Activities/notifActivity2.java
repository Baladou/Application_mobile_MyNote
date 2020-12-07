package com.example.application_note.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.application_note.Helper.DBHelper;
import com.example.application_note.Model.Note;
import com.example.application_note.R;

import java.util.Random;

public class notifActivity2 extends AppCompatActivity {
    static TextView note_tv,title_tv,date_tv;
    private DBHelper mydb ;
    private static String tle,txt;

    Button fav_btn;
    DBHelper db;
    View v;
    long id_note;
    MenuItem fav,fav1;
    Random r = new Random();

    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_activity);

        v= this.getWindow().getDecorView();

        db= new DBHelper(getApplicationContext());
        note_tv=findViewById(R.id.notetv);
        title_tv=findViewById(R.id.titletv);
        date_tv=findViewById(R.id.datetv);
        note_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(notifActivity2.this, update_note.class);
                i.putExtra("id_note",id_note);
                startActivityForResult(i, 1);
            }
        });
        title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(notifActivity2.this, update_note.class);
                i.putExtra("id_note",id_note);
                startActivityForResult(i, 1);
            }
        });

        mydb = new DBHelper(this);
        id_note = getIntent().getExtras().getInt("ID3");
        Log.v("bundlereceived2", String.valueOf(id_note));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exp_menu, menu);

        fav = menu.findItem(R.id.favorite);
        fav1 = menu.findItem(R.id.favorite1);

        if(note.getFavourite().equals("1")){
            fav1.setVisible(true);
            fav.setVisible(false);

        }
        else{
            fav.setVisible(true);
            fav1.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);

    }



    public static void getlistinfo(String title, String note, String created){
        note_tv.setText(note);
        title_tv.setText(title);
        date_tv.setText(created);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit: {
                Intent i=new Intent(notifActivity2.this, update_note.class);
                i.putExtra("id_note",id_note);
                startActivityForResult(i, 1);
                return false;
            }

            case R.id.favorite: {
                fav1.setVisible(true);
                fav.setVisible(false);
                note.setFavourite("1");
                db.modifier(note);
                return true;
            }
            case R.id.favorite1: {
                fav1.setVisible(false);
                fav.setVisible(true);
                note.setFavourite("0");
                db.updateNote(note);
                return true;
            }
            case R.id.home: {
                Intent main = new Intent(notifActivity2.this, MainActivity.class);
                startActivity(main);
            }

            default:

                return super.onOptionsItemSelected(item);

        }

    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                note=db.getNote(id_note);
                getlistinfo(note.getName(),note.getText(),note.getCreated_at());
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }//onAct
*/

    /* @Override
     public void onBackPressed() {


         Intent returnIntent = new Intent();
         setResult(RESULT_OK,returnIntent);
         finish();
     }*/
    @Override
    protected void onResume() {

        super.onResume();
        showdata();

    }
    public void showdata(){
        note=db.getNote(id_note);
        getlistinfo(note.getName(),note.getText(),note.getCreated_at());
        if(note.getCouleur().equals("Color_2")) {
            v.setBackgroundResource(R.color.color_2);
        }
        if(note.getCouleur().equals("Color_1")) {
            v.setBackgroundResource(R.color.color_1);
        }
        if(note.getCouleur().equals("Color_3")) {
            v.setBackgroundResource(R.color.color_3);
        }
        if(note.getCouleur().equals("Color_4")) {
            v.setBackgroundResource(R.color.color_4);
        }

    }
}
