package com.example.mark.gradetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import adapters.SemesterListAdapter;
import data.Semester;
import managers.SemesterManager;

public class SelectSemesterActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_semester_list);

        SemesterManager semesterManager = SemesterManager.getInstance();

        ListAdapter semesterAdapter = new SemesterListAdapter(this, semesterManager.getSemesters());
        ListView coursesListView = (ListView) findViewById(R.id.semesterListView);
        coursesListView.setAdapter(semesterAdapter);

        coursesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Semester semester = (Semester) parent.getItemAtPosition(position);
                        goToCourses(semester);
                    }
                }
        );
    }

    private void goToCourses(Semester semester){
        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semester", semester.getName());
        startActivity(intent);
    }

    public void settingsClicked(View view){

    }

}
