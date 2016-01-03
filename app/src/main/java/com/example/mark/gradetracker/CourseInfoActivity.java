package com.example.mark.gradetracker;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.GradeSectionInfoListAdapter;
import data.Course;
import data.GradeSection;
import data.Mark;

public class CourseInfoActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    String semesterName;
    Course selectedCourse;

    //Declare relevant activity widgets
    TextView courseNameText;
    TextView courseCodeText;
    ExpandableListView gradeSectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        //Initialize relevant activity widgets
        courseNameText = (TextView) findViewById(R.id.courseNameText);
        courseCodeText = (TextView) findViewById(R.id.courseCodeText);
        gradeSectionList = (ExpandableListView) findViewById(R.id.gradeSectionList);

        //Retrieve data from previous activity
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");

        //Set static text in activity
        courseNameText.setText(selectedCourse.getName());
        courseCodeText.setText("Course Code: " + selectedCourse.getCode());

        //Generate a HashMap for the ExpandableListView
        HashMap<String, List<Mark>> childList = new HashMap<>();
        for(GradeSection gradeSection: selectedCourse.getGrade()){
            childList.put(gradeSection.getSectionName(), gradeSection.getMarks());
        }

        GradeSectionInfoListAdapter listAdapter = new GradeSectionInfoListAdapter(this, selectedCourse.getGrade(), childList);
        gradeSectionList.setAdapter(listAdapter);

    }

    public void settingsButtonClicked(View view){

    }
}
