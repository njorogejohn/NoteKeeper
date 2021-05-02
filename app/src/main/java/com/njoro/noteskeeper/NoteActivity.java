package com.njoro.noteskeeper;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner coursesSpinner = findViewById(R.id.spinner_courses);
        EditText etTitle = findViewById(R.id.etTitle);
        EditText etText = findViewById(R.id.etText);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        coursesSpinner.setAdapter(arrayAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            NoteInfo note = bundle.getParcelable("NOTE");

            String title = note.getTitle();
            if (!TextUtils.isEmpty(title)) {
                etTitle.setText(title);
            }

            String text = note.getText();
            if (!TextUtils.isEmpty(text)) {
                etText.setText(text);
            }

            CourseInfo courseInfoData = note.getCourse();
            if (courseInfoData != null) {
                String course = courseInfoData.getTitle();
                if (!TextUtils.isEmpty(course)) {
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getTitle().equalsIgnoreCase(course)) {
                            coursesSpinner.setSelection(i);
                            break;
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}