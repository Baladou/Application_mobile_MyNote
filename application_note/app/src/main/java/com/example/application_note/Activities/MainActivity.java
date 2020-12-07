package com.example.application_note.Activities;

import android.annotation.SuppressLint;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.application_note.Helper.DBHelper;

import com.example.application_note.Model.Note;
import com.example.application_note.R;
import com.example.application_note.adapter.ItemAdapter;
import com.example.application_note.adapter.listAdapter;
import com.example.application_note.adapter.search_adapter;
import com.example.application_note.materialsearchbar.MaterialSearchBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button btnadd;
    DBHelper controllerdb = new DBHelper (this);
    search_adapter adapter;

    List<Note> list;
    private RecyclerView itemlist;
    MaterialSearchBar materialSearchBar;
    List<String> suggestlist=new ArrayList<>();

    private DrawerLayout drawer;
    int rbo,rbt,rba;
    TextView txtv, txtv_ordre;
    RadioButton btnR_name, btnR_crois, rdbtn_aff;
    RadioGroup grT, grO,gra;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    long id_note;

    String del="no";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemlist=findViewById(R.id.item_list);


            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if(extras == null) {
                    id_note= -1;
                } else {

                    del=extras.getString("delete","no");
                    final String delete =del;

                    if(delete.equals("yes")){
                        id_note= extras.getLong("id_note");
                        openDialog_delete(id_note);}
                }
            } else {
                // id_note= (long) savedInstanceState.getSerializable("id_note");
            }



            drawer = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        materialSearchBar= findViewById(R.id.searchbar);

        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(8);
        loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest= new ArrayList<>();
                for (String search:suggestlist){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                  // itemlist.setAdapter(adapter);
                    display();
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode) {
                    case MaterialSearchBar.BUTTON_SPEECH:
                        break;
                    case MaterialSearchBar.BUTTON_BACK:
                        materialSearchBar.disableSearch();
                       display();
                        break;
                }
            }
        });



        display();

////////
        final FloatingActionButton searchButton = findViewById(R.id.plus);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewNote.class);
                startActivity(a);            }
        });


    }



    ////////
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void startSearch(String txt) {

        adapter=new search_adapter(this, controllerdb.getNoteByText(txt));
        itemlist.setAdapter(adapter);
    }

    @Override
    protected void onResume() {

        super.onResume();
        display();

    }


    private void loadSuggestList(){
        suggestlist=controllerdb.getAllNotesTitles();
        materialSearchBar.setLastSuggestions(suggestlist);
    }



    public void display() {
        list = new ArrayList<>();
        list = controllerdb.getAllNotes();


        ItemAdapter itemAdapter = new ItemAdapter(getBaseContext(), list, MainActivity.this, controllerdb);
        itemlist.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        itemlist.setAdapter(itemAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_corbeille) {
            Intent intent_corb=new Intent(MainActivity.this, Corb_activity.class);
            startActivity(intent_corb);
        } else if (id == R.id.nav_categ) {
            Intent cat=new Intent(MainActivity.this, Categories.class);
            startActivity(cat);
        } else if (id == R.id.nav_nocateg) {
            Intent noCat=new Intent(MainActivity.this, NoCat_activ.class);
            startActivity(noCat);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tri: {
                      openDialog_tri();

                return false;
            }

            case R.id.aff: {
        openDialog_aff();
                return true;
            }


            default:

                return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

        }

    }

    private void openDialog_aff() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();


        View view = inflater.inflate(R.layout.dialog2, null);
        builder.setView(view).setTitle("View By")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // finish();

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (rdbtn_aff.getText().equals("Simple List")) {
                    display_list();
                }
                if (rdbtn_aff.getText().equals("List")) {
                    display();
                }

            }
        });


        gra = view.findViewById(R.id.graff);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }





    public void rbAclick(View v) {
        rba = gra.getCheckedRadioButtonId();
        rdbtn_aff = v.findViewById(rba);

    }





    private void openDialog_tri() {
      /*dialog_tri dial = new dialog_tri ();
        dial.show(getSupportFragmentManager(), "Sort by");*/
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();


        View view = inflater.inflate(R.layout.dialog1, null);
        builder.setView(view).setTitle("Sort by")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // finish();

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (btnR_name.getText().equals("Title") && btnR_crois.getText().equals("Ascending")) {
                    display_TO("Name_Note","A");

                }
                if (btnR_name.getText().equals("Title") && btnR_crois.getText().equals("Descending")) {
                    display_TO("Name_Note","D");
                }
                if (btnR_name.getText().equals("Date created") && btnR_crois.getText().equals("Ascending")) {
                    display_TO("Created_at","A");

                }
                if (btnR_name.getText().equals("Date created") && btnR_crois.getText().equals("Descending")) {
                    display_TO("Created_at","D");
                }
            }
        });

        grT=view.findViewById(R.id.group_tri);
        grO=view.findViewById(R.id.group_order);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


    private void display_TO(String tri, String ordre) {
        list = new ArrayList<>();

        list = controllerdb.getAllNotes_TO(tri, ordre);


        ItemAdapter itemAdapter = new ItemAdapter(getBaseContext(), list, MainActivity.this, controllerdb);
        itemlist.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        itemlist.setAdapter(itemAdapter);
    }


    public void rbtclick(View v) {
        rbt = grT.getCheckedRadioButtonId();
        btnR_name = v.findViewById(rbt);
    }

    public void rbOclick(View v) {

        rbo = grO.getCheckedRadioButtonId();
        btnR_crois = v.findViewById(rbo);


    }


    public void openDialog_delete(final long j) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to delete this note?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Note not=controllerdb.getNote(j);
                long id_c= controllerdb.createCorb(not);
                controllerdb.deleteNote(j);

                display();


            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



    public void display_list(){
        list=new ArrayList<>();

        list=controllerdb.getAllNotes();


        listAdapter listAdapter=new listAdapter(getBaseContext(),list,MainActivity.this);
        itemlist.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        itemlist.setAdapter(listAdapter);
    }




}













   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/










