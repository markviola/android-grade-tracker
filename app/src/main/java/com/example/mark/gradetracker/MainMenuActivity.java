package com.example.mark.gradetracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import data.Course;
import data.DBManager;
import data.GradeSectionAllMarks;
import data.Mark;
import data.Semester;
import managers.SemesterManager;

public class MainMenuActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    Button selectSemesterButton;
    Button addSemesterButton;
    Button settingsButton;
    TextView mainMenuTitleText;

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
        mainMenuTitleText = (TextView) findViewById(R.id.mainMenuTitleText);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        mainMenuTitleText.setTypeface(font);

        // TEST CODE BELOW //
        GradeSectionAllMarks cscb07Assignments = new GradeSectionAllMarks("Assignments", 0.4);
        cscb07Assignments.addMark(new Mark("Assignment1", 90.00));
        cscb07Assignments.addMark(new Mark("Assignment2", 88.00));
        GradeSectionAllMarks cscb36Assignments = new GradeSectionAllMarks("Assignments", 0.3);
        cscb36Assignments.addMark(new Mark("Assignment1", 85.00));

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

