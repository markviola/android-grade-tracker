package com.example.mark.gradetracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import data.Course;
import data.GradeSection;
import data.Mark;

public class AddBulkMarkPopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView addMarksTitle;

    ArrayList<Course> courses;
    ArrayList<GradeSection> gradeSections;
    ArrayList<Mark> marks;
    String newSemesterName;
    String newCourseName;
    String gradeSectionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bulk_mark_pop_up);

        addMarksTitle = (TextView) findViewById(R.id.addMarksTitle);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addMarksTitle.setTypeface(font);

        Intent intent = getIntent();
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
        marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8*width),(int) (0.5*height));
    }

    public void addMarksButtonClicked(View view){

    }
}
