package com.example.mark.gradetracker.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.mark.gradetracker.R;

import java.util.ArrayList;

import data.Course;
import data.GradeSection;
import data.Mark;

public class AddMarkOptionsPopUpActivity extends AppCompatActivity {

    ArrayList<Course> courses;
    ArrayList<GradeSection> gradeSections;
    ArrayList<Mark> marks;
    String newSemesterName;
    String newCourseName;
    String newCourseCode;
    String gradeSectionName;
    String gradeSectionWeight;
    boolean fromSelectCourseActivity;
    boolean fromCourseInfoActivity;
    Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark_options_pop_up);

        Intent intent = getIntent();

        fromCourseInfoActivity = (boolean) intent.getSerializableExtra("fromCourseInfoActivity");

        if(fromCourseInfoActivity){
            newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
            selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
            gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
            gradeSectionWeight = (String) intent.getSerializableExtra("gradeSectionWeight");
            marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
            fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
        } else{
            newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
            courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
            newCourseName = (String) intent.getSerializableExtra("newCourseName");
            newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
            gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
            gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
            gradeSectionWeight = (String) intent.getSerializableExtra("gradeSectionWeight");
            marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
            fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
        }


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.7 * width), (int) (0.5 * height));
    }

    public void singleAddButtonClicked(View view){
        Intent intent = new Intent(this, AddEditSingleMarkPopUpActivity.class);

        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionName);
        intent.putExtra("gradeSectionWeight", gradeSectionWeight);
        intent.putExtra("marks", marks);
        intent.putExtra("previousActivity", "AddMarkOptionsPopUpActivity");
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);

        startActivity(intent);
    }

    public void bulkAddButtonClicked(View view){
        Intent intent = new Intent(this, AddBulkMarkPopUpActivity.class);

        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionName);
        intent.putExtra("gradeSectionWeight", gradeSectionWeight);
        intent.putExtra("marks", marks);
        intent.putExtra("previousActivity", "AddMarkOptionsPopUpActivity");
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);

        startActivity(intent);
    }
}
