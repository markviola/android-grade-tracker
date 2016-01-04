package com.example.mark.gradetracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.Course;
import data.GradeSection;
import data.Mark;


public class AddSingleMarkPopUpActivity extends Activity {

    private String TAG = "customFilter";

    EditText markNameEditText;
    EditText markGradeEditText;
    TextView addMarkTitle;

    ArrayList<Course> courses;
    ArrayList<GradeSection> gradeSections;
    ArrayList<Mark> marks;
    String newSemesterName;
    String newCourseName;
    String gradeSectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_single_mark_pop_up);

        setup();
    }

    private void setup(){
        markNameEditText = (EditText) findViewById(R.id.markNameEditText);
        markGradeEditText = (EditText) findViewById(R.id.markGradeEditText);
        addMarkTitle = (TextView) findViewById(R.id.addMarkTitle);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addMarkTitle.setTypeface(font);

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

    public void addMarkButtonClicked(View view){
        String markName = markNameEditText.getText().toString();

        if(isValidValue(markGradeEditText.getText().toString())){
            marks.add(new Mark(markName,
                    Double.parseDouble(markGradeEditText.getText().toString())
                    //,Double.parseDouble(markWeightEditText.getText().toString())
            ));

            Intent intent = new Intent(this, AddGradeSectionActivity.class);
            intent.putExtra("newSemesterName", newSemesterName);
            intent.putExtra("courses", courses);
            intent.putExtra("newCourseName", newCourseName);
            intent.putExtra("gradeSections", gradeSections);
            intent.putExtra("gradeSectionName", gradeSectionName);
            intent.putExtra("marks", marks);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
            startActivity(intent);
        } else {
            Toast.makeText(this, "Grade is in incorrect format", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isValidValue(String str){
        try {
            double d = Double.parseDouble(str);
            if(d >= 0 ){
                return true;
            }
        } catch(NumberFormatException nfe){
            return false;
        }
        return false;
    }

}
