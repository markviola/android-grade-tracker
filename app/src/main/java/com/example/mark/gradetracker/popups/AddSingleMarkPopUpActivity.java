package com.example.mark.gradetracker.popups;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark.gradetracker.adding.AddGradeSectionActivity;
import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.navigation.CourseInfoActivity;

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
    String newCourseCode;
    String gradeSectionName;
    String gradeSectionWeight;

    String semesterName;
    Course currentCourse;
    GradeSection selectedGradeSection;


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

        if(intent.getSerializableExtra("currentCourse") == null){
            newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
            courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
            newCourseName = (String) intent.getSerializableExtra("newCourseName");
            newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
            gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
            gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
            gradeSectionWeight = (String) intent.getSerializableExtra("gradeSectionWeight");
            marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
        } else {
            semesterName = (String) intent.getSerializableExtra("semesterName");
            currentCourse = (Course) intent.getSerializableExtra("currentCourse");
            selectedGradeSection = (GradeSection) intent.getSerializableExtra("selectedGradeSection");
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8*width),(int) (0.5*height));
    }

    public void addMarkButtonClicked(View view){
        String markName = markNameEditText.getText().toString();

        if(isValidValue(markGradeEditText.getText().toString())){
            Mark newMark = new Mark(markName,
                    Double.parseDouble(markGradeEditText.getText().toString())
                    //,Double.parseDouble(markWeightEditText.getText().toString())
            );

            //Check if adding a mark from the "AddGradeSectionActivity" or "CourseInfoActivity"
            if(currentCourse == null){

                marks.add(newMark);
                Intent intent = new Intent(this, AddGradeSectionActivity.class);
                intent.putExtra("newSemesterName", newSemesterName);
                intent.putExtra("courses", courses);
                intent.putExtra("newCourseName", newCourseName);
                intent.putExtra("newCourseCode", newCourseCode);
                intent.putExtra("gradeSections", gradeSections);
                intent.putExtra("gradeSectionName", gradeSectionName);
                intent.putExtra("gradeSectionWeight", gradeSectionWeight);
                intent.putExtra("marks", marks);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
                startActivity(intent);
            } else {
                GradeSection gradeSection = currentCourse.getGradeSectionByName(selectedGradeSection.getSectionName());
                gradeSection.addMark(newMark);
                Intent intent = new Intent(this, CourseInfoActivity.class);
                intent.putExtra("semesterName", semesterName);
                intent.putExtra("selectedCourse", currentCourse);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
                startActivity(intent);

            }

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
