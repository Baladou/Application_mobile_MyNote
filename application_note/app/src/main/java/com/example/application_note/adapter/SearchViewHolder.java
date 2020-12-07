package com.example.application_note.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.application_note.R;

class SearchViewHolder extends RecyclerView.ViewHolder{
    public TextView title,text,date;
    public SearchViewHolder(View itemView){
        super(itemView);
        title=itemView.findViewById(R.id.title);
        text=itemView.findViewById(R.id.description);
        date=itemView.findViewById(R.id.test);

    }
}
