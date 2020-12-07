package com.example.application_note.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application_note.Helper.DBHelper;
import com.example.application_note.Model.Category;
import com.example.application_note.R;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity implements NewCategory.NewCategoryListener
{

     DBHelper db;
     List<Category> list;
     List<String> CatName;
     ArrayAdapter adapter;
     ListView Category;
     Button Add;
     EditText categoryName;
    private boolean delete;
     EditText input ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Intent intent = getIntent();
        String str = "";
        if (intent.hasExtra("New category")){ // vérifie qu'une valeur est associée à la clé “edittext”
            str = intent.getStringExtra("New category"); // on récupère la valeur associée à la clé
        }



        db = new DBHelper(getApplicationContext());
        Category=findViewById(R.id.listview);
        Add=findViewById(R.id.ADD);
        categoryName=findViewById(R.id.NewCategory);

         list=new ArrayList<>();
        CatName=new ArrayList<>();

         list=db.getAllCategories();
        CatName.clear();
         for(int i =0; i< list.size(); i++){


             Category cat= list.get(i);
             CatName.add(cat.getName_Cat());
         }

         final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CatName);

          Category.setAdapter(adapter);
         // adapter.add(str);
          adapter.notifyDataSetChanged();
        Category.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> adapters, final View item, final int pos, final long id) {

                        final Category c=list.get(pos);
                        final long j=c.getID_Cat();
                        AlertDialog.Builder builder=new AlertDialog.Builder(Categories.this);
                        builder.setTitle("Delete or Update the category you selected");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

          ////////////////  / //////////delete dialog////////////////////////////////
                                AlertDialog.Builder buildDelet=new AlertDialog.Builder(Categories.this);
                                buildDelet.setTitle("Delete the category you selected");
                                buildDelet.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Category cat=list.get(pos);
                                        db.deleteCat(cat, true);

                                        CatName.remove(pos);
                                        Category.requestLayout();
                                        adapter.notifyDataSetChanged();


                                    }});

                                buildDelet.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {



                                    }}) ;



                                AlertDialog alertDialog=buildDelet.create();
                                alertDialog.show();

                   /////////end delete dialog ////////////////////////////////////
                            }
                        });

                        builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                ////////////////  / //////////update dialog////////////////////////////////
                                final AlertDialog.Builder buildUpd=new AlertDialog.Builder(Categories.this);
                                buildUpd.setTitle("Update the category you selected");
                                final EditText input = new EditText(Categories.this);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                Category cat=list.get(pos);
                                input.setText(cat.getName_Cat(),TextView.BufferType.EDITABLE);
                                buildUpd.setView(input);
                                buildUpd.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Category cat=list.get(pos);
                                        cat.setName_Cat(input.getText().toString());
                                        db.updateCateg(cat);
                                        CatName.set(pos,input.getText().toString());
                                        Category.requestLayout();
                                        adapter.notifyDataSetChanged();

                                        Intent intent = new Intent(Categories.this,Categories.class);
                                        startActivity(intent);
                                         finish();


                                    }});

                                buildUpd.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {



                                    }}) ;

                                AlertDialog alertDialog=buildUpd.create();
                                alertDialog.show();


                                //////////end delete dialog ////////////////////////////////////

                            }


                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                        return true;

                    }

                });

        adapter.notifyDataSetChanged();


        Add.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  openDialog();

              } });

        // ListView on item selected listener.
        Category.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category c=list.get(position);
                long j= c.getID_Cat();
                Intent intent = new Intent(Categories.this,NewNote.class);
                intent.putExtra("category id", j);
                startActivity(intent);

            }
        });






    }
    @Override
    public void  onResume(){
        super.onResume();

        list=new ArrayList<>();
        CatName=new ArrayList<>();

        list=db.getAllCategories();
        CatName.clear();
        for(int i =0; i< list.size(); i++){


            Category cat= list.get(i);
            CatName.add(cat.getName_Cat());
        }

        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CatName);

        Category.setAdapter(adapter);

    }
    /////Implémentation du fragment NewCategory//////////
    public  void openDialog(){

            NewCategory newCat= new NewCategory();
            newCat.show(getSupportFragmentManager(), "Add Category");
    }
    @Override
    public void SaveCategory(String CategorName) {

        Category categ = new Category(CategorName);
        long i=db.createCateg(categ);

        Log.v(String.valueOf(i),"category id");
        finish();

    }
    /////////////// end of Implémentation du fragment NewCategory//////////




}




