package com.example.application_note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;



public class dialog_tri extends AppCompatDialogFragment {


    TextView txtv, txtv_ordre;
    RadioButton btnR_name, btnR_date, btnR_crois, btnR_decrois;
    RadioGroup grT, grO;

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.dialog1, null);
        builder.setView(view).setTitle("Sort by")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        txtv = view.findViewById(R.id.textView_tri);

        grT=view.findViewById(R.id.group_tri);

        return builder.create();
    }
    public void rbclick(View v){
     int rb=grT.getCheckedRadioButtonId();
        btnR_name=v.findViewById(rb);
      int rbo=grO.getCheckedRadioButtonId();
        btnR_crois=v.findViewById(rbo);

    }
}