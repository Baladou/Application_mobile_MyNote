package com.example.application_note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.application_note.Model.Note;
import com.example.application_note.R;

import java.util.List;

public class search_adapter extends RecyclerView.Adapter<SearchViewHolder>{
    private Context context;
    private List<Note> notes;

    public search_adapter(Context context,List<Note> notes){
        this.context=context;
        this.notes=notes;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.item_row,parent,false);
        return new SearchViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getName());
        holder.text.setText(notes.get(position).getText());
        holder.date.setText(notes.get(position).getCreated_at());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
