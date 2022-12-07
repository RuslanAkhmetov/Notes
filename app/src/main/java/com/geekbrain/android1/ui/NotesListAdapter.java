package com.geekbrain.android1.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrain.android1.Note;
import com.geekbrain.android1.R;

import java.util.List;


public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder>  {

    private List<Note> list;

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private static final String TAG = "NotesListAdapter";

    public List<Note> getList() {
        return list;
    }

    public NotesListAdapter(List<Note> list) {
        this.list = list;
    }

    public void setList(List<Note> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note currentNote = list.get(position);
        holder.getNameText().setText(currentNote.getName());
        holder.getDateText().setText(currentNote.getNoteDate().toString());
        holder.getBodyText().setText(currentNote.getBody());
        if (currentNote.getBackColor() ==  holder.itemView.getContext().getResources().getColor(R.color.teal_700, null)) {
            Log.i(TAG, "fragmentInit: note.getBackColor()");
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.frame_border_teal_700));
        } else if  (currentNote.getBackColor() == holder.itemView.getContext().getResources().getColor(R.color.purple_200, null)) {
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.frame_border_purple_200));
        } else {
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.frame_border)); // Drawable.createFromPath("@drawable/frame_border"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView bodyText;
        private TextView dateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.note_name);
            dateText = itemView.findViewById(R.id.note_date);
            bodyText = itemView.findViewById(R.id.note_body);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Note currentNote = list.get(position);
                    if (itemClickListener!=null) {
                        itemClickListener.onItemClick(view, currentNote);
                    }
                }
            });
        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getBodyText() {
            return bodyText;
        }

        public TextView getDateText() {
            return dateText;
        }

    }


}
