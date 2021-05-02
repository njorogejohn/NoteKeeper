package com.njoro.noteskeeper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(this, NoteActivity.class))
        );

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_notes);

        List<NoteInfo> noteInfoList = DataManager.getInstance().getNotes();
        Log.e("TAG NOTES KEEPER", "number of notes LIST: "+noteInfoList.size());
        NotesAdapter notesAdapter = new NotesAdapter(noteInfoList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));

        recyclerView.setAdapter(notesAdapter);

        notesAdapter.setOnItemClickListener((noteInfo) -> {
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("NOTE", noteInfo);
            startActivity(intent);
        });
    }
}