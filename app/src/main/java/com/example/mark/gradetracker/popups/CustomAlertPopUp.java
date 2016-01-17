package com.example.mark.gradetracker.popups;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.adding.AddCourseActivity;
import com.example.mark.gradetracker.adding.AddGradeSectionActivity;
import com.example.mark.gradetracker.adding.AddSemesterActivity;
import com.example.mark.gradetracker.navigation.CourseInfoActivity;
import com.example.mark.gradetracker.navigation.SelectCourseActivity;
import com.example.mark.gradetracker.navigation.SelectSemesterActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import data.Course;
import data.DBManager;
import data.GradeSection;
import data.Mark;
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
    Mark selectedMark;
    String newSemesterName;
    String newCourseName;
    String newCourseCode;
    String gradeSectionName;
    String gradeSectionWeight;
    String markNameTemplate;
    ArrayList<Course> courses;
    ArrayList<GradeSection> gradeSections;
    ArrayList<Mark> marks;
    ArrayList<Mark> newMarks;
    int position;

    boolean fromSelectCourseActivity;
    boolean fromCourseInfoActivity;

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

        switch (previousActivity) {
            case "EditSemesterOptionsPopUpActivity":
                setupEditSemesterOptionsPopUpActivity();
                break;
            case "EditCourseOptionsPopUpActivity":
                setupEditCourseOptionsPopUpActivity();
                break;
            case "EditGradeSectionOptionsPopUpActivity":
                setupEditGradeSectionOptionsPopUpActivity();
                break;
            case "EditMarkOptionsPopUpActivity":
                setupEditMarkOptionsPopUpActivity();
                break;
            case "AddSemesterActivityBack":
                setupAddSemesterActivityBack();
                break;
            case "AddSemesterActivityDeleteCourse":
                setupAddSemesterActivityDeleteCourse();
                break;
            case "AddCourseActivityToSelectCourse":
                setupAddCourseActivityToSelectCourse();
                break;
            case "AddCourseActivityToAddSemester":
                setupAddCourseActivityToAddSemester();
                break;
            case "AddCourseActivityDeleteGradeSection":
                setupAddCourseActivityDeleteGradeSection();
                break;
            case "AddGradeSectionActivityBackToAddCourse":
                setupAddGradeSectionActivityBackToAddCourse();
                break;
            case "AddGradeSectionActivityBackToCourseInfo":
                setupAddGradeSectionActivityBackToCourseInfo();
                break;
            case "AddGradeSectionActivityDeleteMark":
                setupAddGradeSectionActivityDeleteMark();
                break;
            case "AddBulkMarkPopUpActivity":
                setupAddBulkMarkPopUpActivity();
                break;
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

    private void setupEditMarkOptionsPopUpActivity(){
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        selectedGradeSection = (GradeSection) intent.getSerializableExtra("selectedGradeSection");
        selectedMark = (Mark) intent.getSerializableExtra("selectedMark");
        cautionContentText.setText(String.format("Deleting \"%s\" mark. This data will be" +
                " lost and cannot be recovered. Are you sure you want to" +
                " delete this mark?", selectedMark.getName()));
        continueButton.setText("Delete Mark");
    }

    private void setupAddSemesterActivityBack(){
        cautionContentText.setText("Going back will erase any data that has been entered in this" +
                " current semester creating session. Are you sure you want to continue back to" +
                " the main menu?");
        continueButton.setText("Continue");
    }

    private void setupAddSemesterActivityDeleteCourse(){
        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("position");
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        cautionContentText.setText(String.format("Deleting \"%s\" course. All data will be lost" +
                " including all corresponding grade sections. Are you sure you want to" +
                " delete this course?", courses.get(position).getName()));
        continueButton.setText("Delete Course");
    }

    private void setupAddCourseActivityToSelectCourse(){
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        cautionContentText.setText("Going back will erase any data that has been entered in this" +
                " current course creating session. Are you sure you want to continue back to" +
                " the select course screen?");
        continueButton.setText("Continue");
    }

    private void setupAddCourseActivityToAddSemester(){
        Intent intent = getIntent();
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        cautionContentText.setText("Going back will erase any data that has been entered in this" +
                " current course creating session. Are you sure you want to continue back to" +
                " the add semester screen?");
        continueButton.setText("Continue");
    }

    private void setupAddCourseActivityDeleteGradeSection(){
        Intent intent = getIntent();
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
        position = (int) intent.getSerializableExtra("position");
        cautionContentText.setText(String.format("Deleting \"%s\" grade section. All data will be" +
                " lost including all corresponding marks. Are you sure you want to" +
                " delete this grade section?", gradeSections.get(position).getSectionName()));
        continueButton.setText("Delete Grade Section");
    }

    private void setupAddGradeSectionActivityBackToAddCourse(){
        Intent intent = getIntent();
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
        cautionContentText.setText("Going back will erase any data that has been entered in this" +
                " current grade section creating session. Are you sure you want to continue back to" +
                " the add course screen?");
        continueButton.setText("Continue");
    }

    private void setupAddGradeSectionActivityBackToCourseInfo(){
        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        cautionContentText.setText("Going back will erase any data that has been entered in this" +
                " current grade section creating session. Are you sure you want to continue back to" +
                " the course info screen?");
        continueButton.setText("Continue");
    }

    private void setupAddGradeSectionActivityDeleteMark(){
        Intent intent = getIntent();
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
        gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
        gradeSectionWeight = (String) intent.getSerializableExtra("gradeSectionWeight");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
        fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
        fromCourseInfoActivity = (boolean) intent.getSerializableExtra("fromCourseInfoActivity");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        position = (int) intent.getSerializableExtra("position");
        cautionContentText.setText(String.format("Deleting \"%s\" mark. This data will be" +
                " lost and cannot be recovered. Are you sure you want to" +
                " delete this mark?", marks.get(position).getName()));
        continueButton.setText("Delete Mark");
    }

    private void setupAddBulkMarkPopUpActivity(){
        Intent intent = getIntent();
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
        gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
        gradeSectionWeight = (String) intent.getSerializableExtra("gradeSectionWeight");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
        newMarks = (ArrayList<Mark>) intent.getSerializableExtra("newMarks");
        fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
        fromCourseInfoActivity = (boolean) intent.getSerializableExtra("fromCourseInfoActivity");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        markNameTemplate = (String) intent.getSerializableExtra("markNameTemplate");
        position = (int) intent.getSerializableExtra("position");
        cautionContentText.setText(String.format("Deleting \"%s\" mark. This data will be" +
                " lost and cannot be recovered. Are you sure you want to" +
                " delete this mark?", newMarks.get(position).getName()));
        continueButton.setText("Delete Mark");
    }

    /*
     *  Continue Button Functions
     *  These are the functions that will execute when the continue button is clicked
     */

    public void continueButtonClicked(View view){
        switch (previousActivity) {
            case "EditSemesterOptionsPopUpActivity":
                deleteSemester();
                break;
            case "EditCourseOptionsPopUpActivity":
                deleteCourse();
                break;
            case "EditGradeSectionOptionsPopUpActivity":
                deleteGradeSection();
                break;
            case "EditMarkOptionsPopUpActivity":
                deleteMark();
                break;
            case "AddSemesterActivityBack":
                addSemesterBackButton();
                break;
            case "AddSemesterActivityDeleteCourse":
                addSemesterDeleteCourse();
                break;
            case "AddCourseActivityToSelectCourse":
                addCourseBackButtonToSelectCourse();
                break;
            case "AddCourseActivityToAddSemester":
                addCourseBackButtonToAddSemester();
                break;
            case "AddCourseActivityDeleteGradeSection":
                deleteGradeSectionFromAddCourse();
                break;
            case "AddGradeSectionActivityBackToAddCourse":
                addGradeSectionBackToAddCourse();
                break;
            case "AddGradeSectionActivityBackToCourseInfo":
                addGradeSectionBackToCourseInfo();
                break;
            case "AddGradeSectionActivityDeleteMark":
                deleteMarkFromAddGradeSection();
                break;
            case "AddBulkMarkPopUpActivity":
                deleteMarkFromBulkMark();
                break;
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

    private void deleteMark(){
        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);

        Semester semester = semesterManager.getSemester(semesterName);
        Course course = semester.getCourseByName(selectedCourse.getName());
        course.deleteMarkByName(selectedGradeSection.getSectionName(), selectedMark.getName());

        dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());

        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", course);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition left_to_right_transition
        startActivity(intent);
    }

    private void addSemesterBackButton(){
        Intent intent = new Intent(this, SelectSemesterActivity.class);
        startActivity(intent);
    }

    private void addSemesterDeleteCourse(){
        courses.remove(position);
        Intent intent = new Intent(this, AddSemesterActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    private void addCourseBackButtonToSelectCourse(){
        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", semesterName);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void addCourseBackButtonToAddSemester(){
        Intent intent = new Intent(this, AddSemesterActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.right_to_left_transition, R.anim.right_to_left_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

    private void deleteGradeSectionFromAddCourse(){
        gradeSections.remove(position);
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("courses", courses);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void addGradeSectionBackToAddCourse(){
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("courses", courses);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                    R.anim.right_to_left_transition, R.anim.right_to_left_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

    private void addGradeSectionBackToCourseInfo(){
        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", selectedCourse);
        startActivity(intent);
    }

    private void deleteMarkFromAddGradeSection(){
        marks.remove(position);
        Intent intent = new Intent(this, AddGradeSectionActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionName);
        intent.putExtra("gradeSectionWeight", gradeSectionWeight);
        intent.putExtra("marks", marks);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    private void deleteMarkFromBulkMark(){
        newMarks.remove(position);
        Intent intent = new Intent(this, AddBulkMarkPopUpActivity.class);
        intent.putExtra("previousActivity", "AddBulkMarkPopUpActivity");
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionName);
        intent.putExtra("gradeSectionWeight", gradeSectionWeight);
        intent.putExtra("marks", marks);
        intent.putExtra("markNameTemplate", markNameTemplate);
        intent.putExtra("newMarks", newMarks);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation

        startActivity(intent);
    }

    public void cancelButtonClicked(View view){
        this.finish();
    }
}
