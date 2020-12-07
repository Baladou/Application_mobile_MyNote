package com.example.application_note.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.application_note.R;

public class NewCategory extends AppCompatDialogFragment {

    EditText category;
    NewCategoryListener Listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.new_category, null);
        builder.setView(view);
        builder.setTitle("Add a Category");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String Cat = category.getText().toString();
                Listener.SaveCategory(Cat);
                Intent c = new Intent(getActivity(),Categories.class);
                c.putExtra("New category", Cat);
                startActivity(c);


            }


        });
        category=view.findViewById(R.id.NewCategory);
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            Listener=(NewCategoryListener)context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString() + "must implement NewCategoryListener ");
        }
    }

    public interface NewCategoryListener{

    void SaveCategory(String CategorName);


    }
}
