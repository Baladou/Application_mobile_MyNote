package com.example.application_note.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import com.example.application_note.Model.*;
import com.example.application_note.Model.Note;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class DBHelper extends SQLiteOpenHelper {

    // Logcat tag



    private static final String LOG = DBHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NotesManager";

    // Table Names
    private static final String Note = "Note";
    private static final String Category = "Category";
    private static final String Alarm = "Alarm";
    private static final String Corbeille = "Corbeille";


    // NOTE_Table - column names

    private static final String ID_Note = "ID_Note";
    private static final String Name_Note = "Name_Note";
    private static final String text = "text";
    private static final String Created_at = "Created_at";
    private static final String Favourite = "Favourite";
    private static final String ID_Categ = "ID_Categ";
    private static final String ID_Col = "ID_Col";
    private static final String Couleur = "Couleur";
    private static final String ID_Corb = "ID_Corb";

    //Category_TAble column Names

    private static final String ID_Cat = "ID_Cat";
    private static final String Name_Categ = "Name_Categ";

    //Alarm_Table column names
    private  static final String ID_Alarm="ID_ALARM";
    private  static final String Date="Date";
    private  static final String Time="Time";
    private  static final String Timeinmillis="TimeInMillis";
    private  static final String ID_Not="ID_Note";


    // Note_Table table create statement
    private static final String CREATE_TABLE_Note = "CREATE TABLE  "
            + Note + " (" + ID_Note + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Name_Note + "  TEXT NOT NULL, " + text + " TEXT NOT NULL, " + Created_at + "  DATETIME, "
            + Favourite + "  TEXT, "+Couleur + " TEXT,"
            + ID_Categ + " INTEGER, " + "FOREIGN KEY (" + ID_Categ + ")  REFERENCES  "
            + Category + " (" + ID_Cat + "))";
           /* ""+ "FOREIGN KEY (" +ID_Col+ ")  REFERENCES  "
             + Color + "("+ ID_Color + "))" ;*/



////Corbeille_table

    private static final String CREATE_TABLE_Corb = "CREATE TABLE  "
            + Corbeille + " (" + ID_Corb + " INTEGER PRIMARY KEY AUTOINCREMENT," + ID_Note + "  INTEGER NOT NULL, "
            + Name_Note + "  TEXT NOT NULL, " + text + " TEXT NOT NULL, " + Created_at + "  DATETIME, "
            + Favourite + "  TEXT, "+Couleur + " TEXT,"
            + ID_Categ + " INTEGER, " + "FOREIGN KEY (" + ID_Categ + ")  REFERENCES  "
            + Category + " (" + ID_Cat + "))";




    // Category_Table table create statement
    private static final String CREATE_TABLE_Category = "CREATE TABLE "
            + Category + "(" + ID_Cat + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Name_Categ + " TEXT NOT NULL" + ")";

    // Alarm_Table table create statement
    private static final String CREATE_TABLE_Alarm = "CREATE TABLE "
            + Alarm + "(" + ID_Alarm + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Date + " Date," + Time + " Time," +Timeinmillis + " TimeInMillis," +
            ID_Not + " INTEGER," +" FOREIGN KEY (" +ID_Not+ ") REFERENCES "
            + Note + "("+ ID_Note + "));" ;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


// creating required tables
        db.execSQL(CREATE_TABLE_Note);
        db.execSQL(CREATE_TABLE_Category);
        db.execSQL(CREATE_TABLE_Alarm);
        db.execSQL(CREATE_TABLE_Corb);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + Note);
        db.execSQL("DROP TABLE IF EXISTS " + Category);
        db.execSQL("DROP TABLE IF EXISTS " + Alarm);
        db.execSQL("DROP TABLE IF EXISTS " + Corbeille);

        // create new tables
        onCreate(db);

    }


    // ------------------------ "Notes" table methods ----------------//

    /**
     * Creating a Note
     */
    public long createNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(ID_Note, note.getID());
        values.put(Name_Note, note.getName());
        values.put(text, note.getText());
        values.put(Created_at, getDateTime());
        values.put(Favourite, note.getFavourite());
        values.put(ID_Categ, note.getID_Categ());
       values.put(Couleur, note.getCouleur());

        // insert row
        long Note_id = db.insert(Note, null, values);


        return Note_id;
    }

    /**
     * get single Note
     */
    public Note getNote(long Note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Note + " WHERE "
                + ID_Note + " = " + Note_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note note = new Note();
        note.setID(c.getInt(c.getColumnIndex(ID_Note)));
        note.setName((c.getString(c.getColumnIndex(Name_Note))));
        note.setText((c.getString(c.getColumnIndex(text))));
        note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));
        //set fav
       //String Fav = c.getString((c.getColumnIndex(Favourite)));

        note.setCouleur(c.getString(c.getColumnIndex(Couleur)));

        //
        note.setFavourite(c.getString(c.getColumnIndex(Favourite)));
        note.setID_Categ(c.getInt(c.getColumnIndex(ID_Categ)));


        return note;
    }

    /************************************/
    public List<Note> getNoteByText(String txt) {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Note + " WHERE (" + Name_Note + " LIKE '%" + txt+ "%' OR " + text +" LIKE '%"+txt+"%')" ;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(c.getInt((c.getColumnIndex(ID_Note))));
                note.setName((c.getString(c.getColumnIndex(Name_Note))));
                note.setText(c.getString(c.getColumnIndex(text)));
                note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));

                // adding to todo list
                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }








    public String getNoteByND(String name) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        //Date date = dateFormat.parse(d);
        SQLiteDatabase db = this.getReadableDatabase();

        /*String selectQuery = "SELECT * FROM " + Note + " WHERE "
                + Name_Note + " = '" + name+" '";*/
        //Cursor c = db.rawQuery(selectQuery, null);
        //+" and "+ Created_at+ " = "+d;*/

        String[] sel=new String[]{Favourite};
       Cursor c =db.query(Note, sel, Name_Note + "='" + name + "'", null, null, null, null);

        if (c != null)
            c.moveToFirst();
        String fav=c.getString(c.getColumnIndex(Favourite));
        return fav;


    }


    /**
     * getting all Notes Titles
     * */
    public List<String> getAllNotesTitles() {
        List<String> notes = new ArrayList<>();
        String selectQuery = "SELECT "+Name_Note+" FROM " + Note;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to todo list
                notes.add(c.getString(c.getColumnIndex("Name_Note")));
            } while (c.moveToNext());
        }

        return notes;
    }


    public List<Note> getAllNotesNoCat() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Note + " WHERE "
                + ID_Categ + " =0";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(c.getInt((c.getColumnIndex(ID_Note))));
                note.setName((c.getString(c.getColumnIndex(Name_Note))));
                note.setText(c.getString(c.getColumnIndex(text)));
                note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));
                note.setCouleur(c.getString(c.getColumnIndex(Couleur)));
                note.setFavourite(c.getString(c.getColumnIndex(Favourite)));
                // add fav

                // adding to todo list
                notes.add(note);
            } while (c.moveToNext());

        }
        return notes;
    }




    /**
     * getting all Notes
     */
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();
        String selectQuery = "SELECT  * FROM " + Note;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(c.getInt((c.getColumnIndex(ID_Note))));
                note.setName((c.getString(c.getColumnIndex(Name_Note))));
                note.setText(c.getString(c.getColumnIndex(text)));
                note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));
                note.setCouleur(c.getString(c.getColumnIndex(Couleur)));
                note.setFavourite(c.getString(c.getColumnIndex(Favourite)));
                // add fav

                // adding to todo list
                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }


    /******
     * ***** getting notes by name and coiss
     */


    public ArrayList<Note> getAllNotes_TO( String tri,String ordre) {
        ArrayList<Note> notes = new ArrayList<Note>();
        String selectQuery="SELECT  * FROM " + Note;
        if(ordre.equals("A")){
           selectQuery = "SELECT  * FROM " + Note +" ORDER BY " +tri +" ASC ";}
        if(ordre.equals("D")){
            selectQuery = "SELECT  * FROM " + Note +" ORDER BY "+tri +"  DESC";
        }

       // Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(c.getInt((c.getColumnIndex(ID_Note))));
                note.setName((c.getString(c.getColumnIndex(Name_Note))));
                note.setText(c.getString(c.getColumnIndex(text)));
                note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));
                note.setCouleur(c.getString(c.getColumnIndex(Couleur)));
                note.setFavourite(c.getString(c.getColumnIndex(Favourite)));
                // add fav

                // adding to todo list
                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }

    /**
     * getting all Notes under single category
     * */
    public List<Note> getAllNotesByCateg(String cat_name) {
        List<Note> notes = new ArrayList<Note>();

        String selectQuery = "SELECT  * FROM " + Note +" ,"+
                Category +    " WHERE "
                + ID_Categ + " = " +ID_Cat ;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(c.getInt((c.getColumnIndex(ID_Note))));
                note.setName((c.getString(c.getColumnIndex(Name_Note))));
                note.setText(c.getString(c.getColumnIndex(text)));
                note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));
                // add fav

                // adding to Notes list
                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }



    /**
     * Updating a Note
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Name_Note, note.getName());
        values.put(text, note.getText());
        // values.put(, note.getID_Categ());
        values.put(Favourite, note.getFavourite());
        if(note.getCouleur()!=null){
        values.put(Couleur, note.getCouleur());}



        // updating row
        return db.update(Note, values, ID_Note + " = ?",
                new String[]{String.valueOf(note.getID())});
    }

    public boolean modifier(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Name_Note, note.getName());
        values.put(text, note.getText());
        values.put(Created_at, note.getCreated_at());

        values.put(Favourite, note.getFavourite());

        //return db.update(Note , values, Name_Note+ " = ?" , new String[]{String.valueOf(note.getName())});
        db.execSQL("UPDATE Note SET Favourite='1' WHERE ID_Note='" + (note.getID()) + "'");
        return true;
        // updating row
        //return db.update(Note , values, ID_Note+ " = ?", new String[] { String.valueOf(note.getID()) });
    }

    public int getIdNote(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT ID_Note FROM " + Note + " WHERE "
                + Name_Note + " = " + date;

        // Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getInt(c.getColumnIndex(ID_Note));


    }


    // Deleting a Note

    public void deleteNote(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note, ID_Note + " = ?",
                new String[]{String.valueOf(note_id)});
    }


    /**
     * getting note count
     */
    public int getNoteCount() {
        String countQuery = "SELECT  * FROM " + Note;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


    // ------------------------ "categs" table methods ----------------//

    // ------------------------ "categs" table methods ----------------//

    /**
     * Creating category
     */
    public long createCateg(Category cat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Name_Categ, cat.getName_Cat());


        // insert row
        long cat_id = db.insert(Category, null, values);

        return cat_id;
    }

    /**
     * getting all categories
     * */
    public List<Category> getAllCategories() {
        List<Category> cats = new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + Category;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category cat = new Category();
                cat.setID_Cat(c.getInt((c.getColumnIndex(ID_Cat))));
                cat.setName_Cat(c.getString(c.getColumnIndex(Name_Categ)));

                // adding to categories list
                cats.add(cat);
            } while (c.moveToNext());
        }
        return cats;
    }




    /**
     * Updating a category
     */
    public int updateCateg(Category cat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Name_Categ, cat.getName_Cat());

        // updating row
        return db.update(Category, values, ID_Cat + " = ?",
                new String[] { String.valueOf(cat.getID_Cat()) });
    }




    /**
     * Deleting a Category
     */
    public void deleteCat(Category cat, boolean should_delete_all_notes) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting category
        // check if notes under this category should also be deleted
        if (should_delete_all_notes) {
            // get all notes under this category
            List<Note> allCatNotes = getAllNotesByCateg(cat.getName_Cat());

            // delete all notes
            for (Note note : allCatNotes) {
                // delete note
                deleteNote(note.getID());
            }
        }

        // now delete the Category
        db.delete(Category, ID_Cat + " = ?",
                new String[] { String.valueOf(cat.getID_Cat()) });
    }




    // ------------------------ "Alarms" table methods ----------------//

   // ------------------------ "Alarms" table methods ----------------//

    /**
     * Creating Alarm
     */
    public long createAlarm(Alarm alrm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Date, alrm.getDate());
        values.put(Time, alrm.getTime());
        values.put(Timeinmillis, alrm.getTimeInMillis());
        values.put(ID_Not, alrm.getNote_ID());

        // insert row
        long alrm_id = db.insert(Alarm, null, values);

        return alrm_id;
    }
    /**
     * get single alarme
     */
    public Alarm getAlarm(long Alarm_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Alarm + " WHERE "
                + ID_Alarm + " = " + Alarm_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Alarm alarm = new Alarm();
        alarm.setID_Alarm(c.getInt(c.getColumnIndex(ID_Alarm)));
        alarm.setDate((c.getString(c.getColumnIndex(Date))));
        alarm.setTime((c.getString(c.getColumnIndex(Time))));
        alarm.setNote_ID((c.getInt(c.getColumnIndex(ID_Not))));
        alarm.setTimeInMillis((c.getLong(c.getColumnIndex(Timeinmillis))));

        return alarm;
    }

    /**
     * get single alarme
     */
    public Alarm getAlarmIDNOTE(int id_note) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Alarm + " WHERE "
                + ID_Note + " = " + id_note;



        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Alarm alarm = new Alarm();
        alarm.setID_Alarm(c.getInt(c.getColumnIndex(ID_Alarm)));
        alarm.setDate((c.getString(c.getColumnIndex(Date))));
        alarm.setTime((c.getString(c.getColumnIndex(Time))));
        alarm.setNote_ID((c.getInt(c.getColumnIndex(ID_Not))));
        alarm.setTimeInMillis((c.getLong(c.getColumnIndex(Timeinmillis))));

        return alarm;
    }
    /**
     * Updating an alarm
     */
    public int updateAlarm(Alarm alrm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Date, alrm.getDate());
        values.put(Time, alrm.getTime());
        values.put(Timeinmillis, alrm.getTimeInMillis());
        values.put(ID_Not, alrm.getNote_ID());

        // updating row
        return db.update(Category, values, ID_Cat + " = ?",
                new String[] { String.valueOf(alrm.getID_Alarm()) });
    }


////////////////// TO VIEW DATABASE IN PHONE

    ////////////////// TO VIEW DATABASE IN PHONE

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }
    //////////////////////

    public long createCorb(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_Note, note.getID());
        values.put(Name_Note, note.getName());
        values.put(text, note.getText());
        values.put(Created_at, getDateTime());
        values.put(Favourite, note.getFavourite());
        values.put(ID_Categ, note.getID_Categ());
        values.put(Couleur, note.getCouleur());

        // insert row
        long Corb_id = db.insert(Corbeille, null, values);


        return Corb_id;
    }


    public ArrayList<Note> getAllCorb() {
        ArrayList<Note> notes = new ArrayList<Note>();
        String selectQuery = "SELECT  * FROM " + Corbeille;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(c.getInt((c.getColumnIndex(ID_Note))));
                note.setName((c.getString(c.getColumnIndex(Name_Note))));
                note.setText(c.getString(c.getColumnIndex(text)));
                note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));
                note.setCouleur(c.getString(c.getColumnIndex(Couleur)));
                note.setFavourite(c.getString(c.getColumnIndex(Favourite)));
                // add fav

                // adding to todo list
                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }


    public Note getCorb(long Note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Corbeille + " WHERE "
                + ID_Note + " = " + Note_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note note = new Note();
        note.setID(c.getInt(c.getColumnIndex(ID_Note)));
        note.setName((c.getString(c.getColumnIndex(Name_Note))));
        note.setText((c.getString(c.getColumnIndex(text))));
        note.setCreated_at((c.getString(c.getColumnIndex(Created_at))));
        //set fav
        String Fav = c.getString((c.getColumnIndex(Favourite)));

        note.setCouleur(c.getString(c.getColumnIndex(Couleur)));

        //
        note.setFavourite(c.getString(c.getColumnIndex(Favourite)));
        note.setID_Categ(c.getInt(c.getColumnIndex(ID_Categ)));


        return note;
    }

    public void deleteCorb(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Corbeille, ID_Note + " = ?",
                new String[]{String.valueOf(note_id)});
    }
    public void deleteAllCorb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Corbeille, null,
                null);
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
Date date =new Date();
        return dateFormat.format(date);
    }












}