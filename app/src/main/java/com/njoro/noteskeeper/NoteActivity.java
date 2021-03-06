package com.njoro.noteskeeper;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private  EditText etTitle, etText;
    String mTitle, mCourse,mText;
    private  List<CourseInfo> courses;
    private Spinner coursesSpinner;
    private boolean isNewNote = true;
    private NoteInfo note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coursesSpinner = findViewById(R.id.spinner_courses);
        etTitle = findViewById(R.id.etTitle);
        etText = findViewById(R.id.etText);

        courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        coursesSpinner.setAdapter(arrayAdapter);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            NoteInfo note = bundle.getParcelable("NOTE");

            isNewNote = (note == null);
        }

        if (!isNewNote) {
            readDisplayContents();
        }

    }

    private void readDisplayContents() {

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
                coursesSpinner.setSelection(courses.indexOf(courseInfoData));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNote();
    }

    private void saveNote() {
        mCourse = coursesSpinner.getSelectedItem().toString();
        mTitle = etTitle.getText().toString().trim();
        mText = etText.getText().toString().trim();

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        CourseInfo selectedCourse = null;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getTitle().equalsIgnoreCase(mCourse)) {
                selectedCourse = courses.get(i);
                break;
            }
        }

        if (selectedCourse != null && !TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mText)) {
            int size = DataManager.getInstance().createNewNote(selectedCourse,mTitle,mText);
            Log.e("TAG NOTES KEEPER","number of notes: "+size);
        }
    }
}