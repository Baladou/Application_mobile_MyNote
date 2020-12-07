package com.example.application_note.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_note.Helper.DBHelper;
import com.example.application_note.Activities.Main2Activity;
import com.example.application_note.Activities.MainActivity;
import com.example.application_note.Model.Note;
import com.example.application_note.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter {

    private List<Note> note;
    private Context context;
    public AppCompatActivity activity;
    DBHelper db;
    Dialog MyDialog;


    public ItemAdapter(Context context, List<Note> note, AppCompatActivity ac, DBHelper db) {
        this.context = context;
        this.note = note;
        this.db = db;
        //this.listener=listener;
        this.activity = ac;

    }


    private static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView title, description, date;
        private ImageView color, favoris;
        private Button delete;
        LinearLayout item_content;

        private ItemHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            date = (TextView) view.findViewById(R.id.test);
            color = (ImageView) view.findViewById(R.id.color_image);
            favoris = (ImageView) view.findViewById(R.id.fav_image);
            delete = (Button) view.findViewById(R.id.btn_delete);
            item_content = (LinearLayout) view.findViewById(R.id.layout_recview);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemHolder(items);
        // MyDialog =new Dialog(context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        //now creating model for adapter
        Note noteitem = note.get(position);
        final ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.title.setText(noteitem.getName());
        itemHolder.description.setText(noteitem.getText());
        itemHolder.date.setText(noteitem.getCreated_at());

        //
        if (noteitem.getFavourite().equals("1")) {
            itemHolder.favoris.setImageResource(R.drawable.ic_star_yellow);
        }

// traitement colors
        if (noteitem.getCouleur().equals("Color_2")) {
            itemHolder.color.setImageResource(R.drawable.colorbtn2);

        }
        if (noteitem.getCouleur().equals("Color_4")) {
            itemHolder.color.setImageResource(R.drawable.colorbtn4);
        }
        if (noteitem.getCouleur().equals("Color_1")) {
            itemHolder.color.setImageResource(R.drawable.colorbtn1);

        }
        if (noteitem.getCouleur().equals("Color_3")) {
            itemHolder.color.setImageResource(R.drawable.colorbtn3);
        }


        itemHolder.item_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note n = note.get(position);
                long j = n.getID();
                Intent i = new Intent(context, Main2Activity.class);
                i.putExtra("id_note", j);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(i, 1);
            }
        });

        itemHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note n = note.get(position);
                long j = n.getID();
                // AlertDialog alertDialog=openDialog_delete(j);
                // MainActivity.openDialog_delete(j);
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("id_note", j);
                i.putExtra("delete", "yes");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(i, 2);

            }
        });


    }


    @Override
    public int getItemCount() {
        return note.size();
    }
}

