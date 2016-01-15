package com.example.mark.gradetracker.popups;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.navigation.CourseInfoActivity;
import com.example.mark.gradetracker.navigation.SelectCourseActivity;
import com.example.mark.gradetracker.navigation.SelectSemesterActivity;

import data.Course;
import data.DBManager;
import data.GradeSection;
import data.Semester;
import managers.SemesterManager;

public class CustomAlertPopUp extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView cautionTitleText;
    TextView cautionContentText;
    Button continueButton;

    String previousActivity;

    String semesterName;
    Course selectedCourse;
    GradeSection selectedGradeSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_alert_pop_up);

        cautionTitleText = (TextView) findViewById(R.id.cautionTitleText);
        cautionContentText = (TextView) findViewById(R.id.cautionContentText);
        continueButton = (Button) findViewById(R.id.continueButton);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        cautionTitleText.setTypeface(font);

        Intent intent = getIntent();
        previousActivity = (String) intent.getSerializableExtra("previousActivity");

        if(previousActivity.equals("EditSemesterOptionsPopUpActivity")){
            setupEditSemesterOptionsPopUpActivity();
        } else if (previousActivity.equals("EditCourseOptionsPopUpActivity")){
            setupEditCourseOptionsPopUpActivity();
        } else if (previousActivity.equals("EditGradeSectionOptionsPopUpActivity")){
            setupEditGradeSectionOptionsPopUpActivity();
        }

        changeActivitySize();
    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8 * width), (int) (0.6 * height));
    }

    /*
     *  Setup Functions
     *  These are the set of functions that can occur when the popup window is created
     */

    private void setupEditSemesterOptionsPopUpActivity(){
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        cautionContentText.setText(String.format("Deleting \"%s\" semester. All data will be lost" +
                " including all corresponding courses and grade sections. Are you sure you want to" +
                " delete the semester?", semesterName));
        continueButton.setText("Delete Semester");
    }

    private void setupEditCourseOptionsPopUpActivity(){
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        cautionContentText.setText(String.format("Deleting \"%s\" course. All data will be lost" +
                " including all corresponding grade sections. Are you sure you want to" +
                " delete this course?", selectedCourse.getName()));
        continueButton.setText("Delete Course");
    }

    private void setupEditGradeSectionOptionsPopUpActivity(){
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        selectedGradeSection = (GradeSection) intent.getSerializableExtra("selectedGradeSection");
        cautionContentText.setText(String.format("Deleting \"%s\" grade section. All data will be" +
                " lost including all corresponding marks. Are you sure you want to" +
                " delete this grade section?", selectedGradeSection.getSectionName()));
        continueButton.setText("Delete Grade Section");
    }

    /*
     *  Continue Button Functions
     *  These are the functions that will execute when the continue button is clicked
     */

    public void continueButtonClicked(View view){
        if(previousActivity.equals("EditSemesterOptionsPopUpActivity")){
            deleteSemester();
        } else if(previousActivity.equals("EditCourseOptionsPopUpActivity")){
            deleteCourse();
        } else if (previousActivity.equals("EditGradeSectionOptionsPopUpActivity")){
            deleteGradeSection();
        }

    }

    private void deleteSemester(){
        DBManager dbManager = DBManager.getInstance(this);
        dbManager.deleteSemester(semesterName);

        Intent intent = new Intent(this, SelectSemesterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    private void deleteCourse(){
        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);

        Semester semester = semesterManager.getSemester(semesterName);
        semester.deleteCourseByName(selectedCourse.getName());

        dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());

        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition left_to_right_transition
        startActivity(intent);
    }

    private void deleteGradeSection(){
        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);

        Semester semester = semesterManager.getSemester(semesterName);
        Course course = semester.getCourseByName(selectedCourse.getName());
        course.deleteGradeSectionByName(selectedGradeSection.getSectionName());

        dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());

        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", course);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    public void cancelButtonClicked(View view){
        this.finish();
    }
}
