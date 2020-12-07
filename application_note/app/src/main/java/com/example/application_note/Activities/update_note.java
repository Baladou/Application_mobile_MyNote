package com.example.application_note.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application_note.ColorPickerDialog;
import com.example.application_note.Helper.DBHelper;
import com.example.application_note.Model.Note;
import com.example.application_note.R;

public class update_note extends AppCompatActivity {

    long id_note;
    Button save, show, category,btn_color;
    DBHelper db;
    ColorPickerDialog.Color chosenColor = ColorPickerDialog.Color.COLOR_1;
    View v;
    String colorchosen;
    EditText title,contenu_note;
    TextView test;

    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);


        //declaration des elements de layout
        db = new DBHelper(getApplicationContext());

       title=findViewById(R.id.title_edit);
        contenu_note=findViewById(R.id.content_edit);
        save=findViewById(R.id.btn_enreg);
        show=findViewById(R.id.btn_ale);
        category=findViewById(R.id.btn_cat);


        btn_color=findViewById(R.id.btn_couleur);
        btn_color.setOnClickListener(clickListener);
        v= this.getWindow().getDecorView();
        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);

        // recupere l'identifiant


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id_note= -1;
            } else {
                id_note= extras.getLong("id_note");
            }
        } else {
            id_note= (long) savedInstanceState.getSerializable("id_note");
        }
        note=db.getNote(id_note);
        contenu_note.setText(note.getText(), TextView.BufferType.EDITABLE);
       title.setText(note.getName(), TextView.BufferType.EDITABLE);
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









        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                note.setName(title.getText().toString());
                note.setText(contenu_note.getText().toString());
                note.setCouleur(colorchosen);
                // Updating note


               db.updateNote(note);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();



            }
        });
        //add Category Menu Autre

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);

            }
        });





        // Don't forget to close database connection
        db.closeDB();



    }

    public void openDialog() {

        NewCategory newCat = new NewCategory();
        newCat.show(getSupportFragmentManager(), "Add Category");


    }


    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){


            if(view == btn_color) {
                ColorPickerDialog diag = new ColorPickerDialog();
                diag.setColorPickerListener(colorListener);
                diag.show(getFragmentManager(), "colors");
            }
        }
    };

    private ColorPickerDialog.ColorPickerListener colorListener = new ColorPickerDialog.ColorPickerListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onColorPicked(ColorPickerDialog.Color c) {
            chosenColor = c;
            switch (c) {
                case COLOR_1:

                { v.setBackgroundResource(R.color.color_1);
                    colorchosen= "Color_1";
                    break;}
                case COLOR_2:

                {v.setBackgroundResource(R.color.color_2);
                    colorchosen= "Color_2";
                    break;}
                case COLOR_3:

                { v.setBackgroundResource(R.color.color_3);
                    colorchosen= "Color_3";
                    break;}
                case COLOR_4:
                { //setColorBackground(R.drawable.circle_color_4);
                    v.setBackgroundResource(R.color.color_4);
                    colorchosen= "Color_4";
                    break;}
                default:
                { COLOR_4:

                    v.setBackgroundResource(R.color.white);
                    colorchosen= "Color_0";}




            }


        }
    };





    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(update_note.this);
       builder.setMessage("Do you want to save your modifications?");
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
                note.setName(title.getText().toString());
                note.setText(contenu_note.getText().toString());
                note.setCouleur(colorchosen);
                db.updateNote(note);

                finish();


            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
            private void setColorBackground(int drawable) {
                btn_color.setBackgroundDrawable(getResources().getDrawable(drawable));
            }

        }
