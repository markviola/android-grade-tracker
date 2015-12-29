package com.example.mark.gradetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import adapters.AddCourseListAdapter;
import data.Course;
import data.Semester;
import managers.SemesterManager;

public class AddSemesterActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    //List of courses to add in the new semester
    ArrayList<Course> courses;

    //Initializing relevant activity widgets
    EditText semesterNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        semesterNameEditText = (EditText) findViewById(R.id.semesterNameEditText);

        Intent intent = getIntent();

        if (intent.getSerializableExtra("courses") == null){
            courses = new ArrayList<>();
            Log.i(TAG, "courses == null");
        } else {
            courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
            Log.i(TAG, "courses != null");
        }

        SemesterManager semesterManager = SemesterManager.getInstance();
        Semester newSem = semesterManager.getSemesters().get(0);

        ListAdapter coursesAdapter = new AddCourseListAdapter(this, newSem.getCourses());
        ListView coursesListView = (ListView) findViewById(R.id.newCoursesList);
        coursesListView.setAdapter(coursesAdapter);

        coursesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String course = String.valueOf(parent.getItemAtPosition(position));
                    }
                }
        );

    }


    /**
     * Take the user to AddCourseActivity to allow them to add a course to the new semester
     * @param view The current view of the app
     */
    public void addCourseButtonClicked(View view){
        Intent newIntent = new Intent(this, AddCourseActivity.class);

        newIntent.putExtra("newSemesterName", semesterNameEditText.getText().toString());
        newIntent.putExtra("courses", courses);

        startActivity(newIntent);
    }

    /**
     * Deletes the specifically selected course from the current list of courses to add
     * @param view The current view of the app
     */
    public void deleteButtonPressed(View view){
        int position = (Integer) view.getTag();
        Log.i(TAG, "position: " + position);
    }

    public void addSemesterButtonClicked(View view){

        if (!semesterNameEditText.getText().toString().equals("")){
            Semester newSemester = new Semester(semesterNameEditText.getText().toString(), courses);
            SemesterManager semesterManager = SemesterManager.getInstance();
            semesterManager.addSemester(newSemester);
        } else {
            //Don't add anything
        }

        Intent intent = new Intent(this, SelectSemesterActivity.class);
        startActivity(intent);
    }
}
