package com.example.mark.gradetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import adapters.CourseListAdapter;
import data.Semester;
import managers.SemesterManager;

public class SelectCourseActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_list);

        Intent intent = getIntent();
        String semesterName = (String) intent.getSerializableExtra("semester");
        SemesterManager semesterManager = SemesterManager.getInstance();
        Semester semester = semesterManager.getSemester(semesterName);

        ListAdapter coursesAdapter = new CourseListAdapter(this, semester.getCourses());
        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);
        coursesListView.setAdapter(coursesAdapter);

        coursesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String course = String.valueOf(parent.getItemAtPosition(position));
                    }
                }
        );
    }
}
