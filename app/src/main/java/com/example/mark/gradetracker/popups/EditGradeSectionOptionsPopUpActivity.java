package com.example.mark.gradetracker.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.navigation.CourseInfoActivity;

import data.Course;
import data.DBManager;
import data.GradeSection;
import data.Semester;
import managers.SemesterManager;

public class EditGradeSectionOptionsPopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    String semesterName;
    Course currentCourse;
    GradeSection selectedGradeSection;

    TextView gradeSectionTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grade_section_options);

        gradeSectionTitleText = (TextView) findViewById(R.id.gradeSectionTitleText);

        Intent intent = getIntent();

        semesterName = (String) intent.getSerializableExtra("semesterName");
        currentCourse = (Course) intent.getSerializableExtra("currentCourse");
        selectedGradeSection = (GradeSection) intent.getSerializableExtra("selectedGradeSection");

        gradeSectionTitleText.setText(String.format("\"%s\"\nGrade Section", selectedGradeSection.getSectionName()));

        changeActivitySize();

    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.7 * width), (int) (0.35 * height));
    }

    public void editGradeSectionButtonClicked(View view){
        //Take user to EditGradeSectionActivity.class

        Intent intent = new Intent(this, EditGradeSectionPopUpActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", currentCourse);
        intent.putExtra("selectedGradeSection", selectedGradeSection);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    public void deleteGradeSectionButtonClicked(View view){
        //Add confirmation popup

        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);

        Semester semester = semesterManager.getSemester(semesterName);
        Course course = semester.getCourseByName(currentCourse.getName());
        course.deleteGradeSectionByName(selectedGradeSection.getSectionName());

        dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());


        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", course);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    public void addMarkButtonClicked(View view){
        //Take user to AddMarkActivity.class
        Intent intent = new Intent(this, AddEditSingleMarkPopUpActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("currentCourse", currentCourse);
        intent.putExtra("selectedGradeSection", selectedGradeSection);
        intent.putExtra("previousActivity", "EditGradeSectionOptionsPopUpActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    public void cancelButtonClicked(View view){
        this.finish();
    }
}
