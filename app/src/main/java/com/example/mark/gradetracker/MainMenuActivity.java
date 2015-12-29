package com.example.mark.gradetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import data.Course;
import data.DBManager;
import data.GradeSection;
import data.GradeSectionEven;
import data.GradeSectionMark;
import data.Semester;
import managers.SemesterManager;

public class MainMenuActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    Button selectSemesterButton;
    Button addSemesterButton;
    Button settingsButton;

    // TEST CODE BELOW //
    Course CSCB07 = new Course("CSCB07");
    Course CSCB36 = new Course("CSCB36");
//    Course MATB24 = new Course("MATB24");
//    Course MATB41 = new Course("MATB41");
//    Course STAB52 = new Course("STAB52");

    ArrayList<Course> courses = new ArrayList<>();
    SemesterManager semesterManager = SemesterManager.getInstance();

    DBManager dbManager;
    // TEST CODE ABOVE //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        selectSemesterButton = (Button) findViewById(R.id.selectSemesterButton);
        addSemesterButton = (Button) findViewById(R.id.addSemesterButton);
        settingsButton = (Button) findViewById(R.id.settingsButton);

        // TEST CODE BELOW //
        GradeSectionEven cscb07Assignments = new GradeSectionEven("Assignments", 0.4);
        cscb07Assignments.addMark(new GradeSectionMark("Assignment1", 90.00));
        cscb07Assignments.addMark(new GradeSectionMark("Assignment2", 88.00));
        GradeSectionEven cscb36Assignments = new GradeSectionEven("Assignments", 0.3);
        cscb36Assignments.addMark(new GradeSectionMark("Assignment1", 85.00));

        CSCB07.addGradeSection(cscb07Assignments);
        CSCB36.addGradeSection(cscb36Assignments);

        courses.add(CSCB07);
        courses.add(CSCB36);
//        courses.add(MATB24);
//        courses.add(MATB41);
//        courses.add(STAB52);

        Semester testSem = new Semester("Fall 2015", courses);

        dbManager = new DBManager(this, null, null, 1);
        //dbManager.addSemester(testSem);

        Semester reSem = dbManager.getSemesterStrToObj("Fall 2015");
        semesterManager.addSemester(reSem);

        // TEST CODE ABOVE //

    }

    public void selectSemesterClicked(View view){
        Intent intent = new Intent(this, SelectSemesterActivity.class);
        startActivity(intent);
    }

    public void addSemesterClicked(View view){
        Intent intent = new Intent(this, AddSemesterActivity.class);
        startActivity(intent);
    }

    public void settingsClicked(View view){

    }

}
