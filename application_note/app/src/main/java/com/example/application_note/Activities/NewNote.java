package com.example.application_note.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.application_note.AlertReceiver;
import com.example.application_note.ColorPickerDialog;
import com.example.application_note.Helper.DBHelper;
import com.example.application_note.Model.Alarm;
import com.example.application_note.Model.Note;
import com.example.application_note.R;

import java.util.Calendar;
import java.util.Random;



public class NewNote extends AppCompatActivity {


    private Calendar mCalendar;
    private Activity mActivity;
    private String mDate,mTime;
    private long mTimeinmillis;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private Uri mCurrentReminderUri;



    final Random r = new Random();
    String content , title;
    EditText titre, contenu;
    Button save, show, category,btncolor,btn_color_note;
    DBHelper db;
    ColorPickerDialog.Color chosenColor = ColorPickerDialog.Color.COLOR_1;
    View v;
    String colorchosen="Color_0";
    long a_id,n_id;
    final String currentTime = Calendar.getInstance().getTime().toString();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        db = new DBHelper(getApplicationContext());
        Intent inten = getIntent();
        long id=0;
        if (inten.hasExtra("category id")){ // vérifie qu'une valeur est associée à la clé “edittext”
            id = inten.getLongExtra("category id", 00); // on récupère la valeur associée à la clé
        }

        final int i= (int) id;
        mActivity = this;
        Intent intent = getIntent();
        mCurrentReminderUri = intent.getData();




        titre=findViewById(R.id.title);
        contenu=findViewById(R.id.content);
        save=findViewById(R.id.button_save);
        show=findViewById(R.id.button_alert);
        category=findViewById(R.id.button_category);
        btncolor=findViewById(R.id.button_color);
       //btn_color_note=findViewById(R.id.btn_color_note);
        btncolor.setOnClickListener(clickListener);
        v= this.getWindow().getDecorView();



        //Save Note
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title=titre.getText().toString();
                content=contenu.getText().toString();
                  // Inserting note

                //Long col=db.getcolor(colorchosen);


                Note note=new Note(title, content , currentTime,i,"0",colorchosen);
                n_id=db.createNote(note);
                Alarm al=new Alarm(mDate,mTime,mTimeinmillis, (int) n_id);
                a_id=db.createAlarm(al);



                Bundle extras = new Bundle();
                extras.putInt("ID", (int) a_id);
                Intent intent = new Intent(getApplicationContext(), notifActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                finish();

            }


        });
       //add Category Menu Autre

       category.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                        Intent cat=new Intent(NewNote.this,Categories.class);
                        startActivity(cat);


                          }
       });


        Button buttonTimePicker = findViewById(R.id.button_alert);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                new DatePickerDialog(mActivity, mDateDataSet, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





        // Don't forget to close database connection
        db.closeDB();

    }


        //openDialogMethod




    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){


            if(view == btncolor) {
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
                    //setColorBackground(R.drawable.circle_color_1);
                    v.setBackgroundResource(R.color.color_1);
                    colorchosen= "Color_1";

                    break;
                case COLOR_2:

                    v.setBackgroundResource(R.color.color_2);
                   colorchosen= "Color_2";
                    break;
                case COLOR_3:

                    v.setBackgroundResource(R.color.color_3);
                    colorchosen= "Color_3";
                    break;
                case COLOR_4:

                    v.setBackgroundResource(R.color.color_4);
                    colorchosen= "Color_4";
                    break;
                default:
                    COLOR_4:

                    v.setBackgroundResource(R.color.white);
                    colorchosen= "Color_0";


            }


        }
    };

        private void setColorBackground(int drawable) {
            btncolor.setBackgroundDrawable(getResources().getDrawable(drawable));
        }


    private final DatePickerDialog.OnDateSetListener mDateDataSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(mActivity, mTimeDataSet, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
        }
    };

    /* After user decided on a time, save them into our calendar instance, and now start alarm */
    private final TimePickerDialog.OnTimeSetListener mTimeDataSet = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
            mMinute = mCalendar.get(Calendar.MINUTE);
            mYear = mCalendar.get(Calendar.YEAR);
            mMonth = mCalendar.get(Calendar.MONTH) + 1;
            mDay = mCalendar.get(Calendar.DATE);

            mDate = mDay + "/" + mMonth + "/" + mYear;
            mTime = mHour + ":" + mMinute;
            mTimeinmillis= mCalendar.getTimeInMillis();

        }
    };
    @Override
    protected void onResume() {

        super.onResume();
       titre.setText(title);
       contenu.setText(content);

    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(NewNote.this);
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


                title = titre.getText().toString();
                content = contenu.getText().toString();
                // Inserting note



                Note note = new Note(title, content, currentTime, i, "0", colorchosen);
                Alarm al = new Alarm(mDate, mTime, mTimeinmillis, (int) n_id);

                a_id = db.createAlarm(al);


                n_id = db.createNote(note);


                Bundle extras = new Bundle();
                extras.putInt("ID", (int) a_id);
                long mTimeinmillis = db.getAlarm(a_id).getTimeInMillis();
                Log.v("alarmtime", String.valueOf(mTimeinmillis));

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                intent.putExtras(extras);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), r.nextInt(1000), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (mTimeinmillis > 0)
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, mTimeinmillis, pendingIntent);
                Intent Intent = new Intent(NewNote.this, MainActivity.class);
                startActivity(Intent);

                Intent a = new Intent(NewNote.this, MainActivity.class);
                startActivity(a);
            }


        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();


    }


}






