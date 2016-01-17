package com.example.mark.gradetracker.navigation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mark.gradetracker.adding.AddSemesterActivity;
import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.navigation.SelectSemesterActivity;

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


    ArrayList<Course> courses = new ArrayList<>();
    SemesterManager semesterManager = SemesterManager.getInstance(this);

    DBManager dbManager;

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

