package com.njoro.noteskeeper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<NoteInfo> noteInfoList;
    /***** Creating OnItemClickListener *****/

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(NoteInfo noteInfo);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public  NotesAdapter(List<NoteInfo> notes){
        noteInfoList = notes;
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NoteInfo noteInfo = noteInfoList.get(position);

        holder.tvCourseName.setText(noteInfo.getCourse().getTitle());
        holder.tvNoteTitle.setText(noteInfo.getTitle());
    }

    @Override
    public int getItemCount() {
        return noteInfoList == null ? 0 : noteInfoList.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName, tvNoteTitle;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(noteInfoList.get(position));
                        }
                    }
                }
            });
        }
    }
}
