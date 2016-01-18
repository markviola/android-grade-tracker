package com.example.mark.report_card.popups;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mark.report_card.adding.AddGradeSectionActivity;
import com.example.mark.report_card.R;
import com.example.mark.report_card.navigation.CourseInfoActivity;

import java.util.ArrayList;

import data.Course;
import managers.DBManager;
import data.GradeSection;
import data.Mark;
import data.Semester;
import managers.SemesterManager;


public class AddEditSingleMarkPopUpActivity extends Activity {

    private String TAG = "customFilter";

    EditText markNameEditText;
    EditText markGradeEditText;
    TextView addMarkTitle;
    Button addMarkButton;

    String previousActivity;

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

    String semesterName;
    Course currentCourse;
    GradeSection selectedGradeSection;

    Mark selectedMark;


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
        addMarkButton = (Button) findViewById(R.id.addMarkButton);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addMarkTitle.setTypeface(font);

        Intent intent = getIntent();

        previousActivity = (String) intent.getSerializableExtra("previousActivity");

        if(previousActivity.equals("EditMarkOptionsPopUpActivity")){
            semesterName = (String) intent.getSerializableExtra("semesterName");
            currentCourse = (Course) intent.getSerializableExtra("currentCourse");
            selectedGradeSection = (GradeSection) intent.getSerializableExtra("selectedGradeSection");
            selectedMark = (Mark) intent.getSerializableExtra("selectedMark");

            addMarkTitle.setText("Edit Mark");
            markNameEditText.setText(selectedMark.getName());
            markGradeEditText.setText(String.format("%.2f", selectedMark.getMark()));
            addMarkButton.setText("Edit Mark");

        }else if(previousActivity.equals("AddMarkOptionsPopUpActivity")){
            newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
            courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
            newCourseName = (String) intent.getSerializableExtra("newCourseName");
            newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
            gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
            gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
            gradeSectionWeight = (String) intent.getSerializableExtra("gradeSectionWeight");
            marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
            fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
            fromCourseInfoActivity = (boolean) intent.getSerializableExtra("fromCourseInfoActivity");
            selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");

        } else if (previousActivity.equals("EditGradeSectionOptionsPopUpActivity")){
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

            if(previousActivity.equals("EditMarkOptionsPopUpActivity")){

                DBManager dbManager = DBManager.getInstance(this);
                SemesterManager semesterManager = SemesterManager.getInstance(this);

                Semester semester = semesterManager.getSemester(semesterName);
                Course course = semester.getCourseByName(currentCourse.getName());
                GradeSection gradeSection = course.getGradeSectionByName(selectedGradeSection.getSectionName());
                Mark mark = gradeSection.getMarkByName(selectedMark.getName());
                mark.setName(markNameEditText.getText().toString());
                mark.setMark(Double.parseDouble(markGradeEditText.getText().toString()));

                dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());

                Intent intent = new Intent(this, CourseInfoActivity.class);
                intent.putExtra("semesterName", semesterName);
                intent.putExtra("selectedCourse", course);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition left_to_right_transition
                startActivity(intent);

            }else if (previousActivity.equals("AddMarkOptionsPopUpActivity")){
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
                intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
                intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
                intent.putExtra("selectedCourse", selectedCourse);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition left_to_right_transition
                startActivity(intent);
            } else if (previousActivity.equals("EditGradeSectionOptionsPopUpActivity")){

                DBManager dbManager = DBManager.getInstance(this);
                SemesterManager semesterManager = SemesterManager.getInstance(this);

                Semester semester = semesterManager.getSemester(semesterName);
                Course course = semester.getCourseByName(currentCourse.getName());
                GradeSection gradeSection = course.getGradeSectionByName(selectedGradeSection.getSectionName());
                gradeSection.addMark(newMark);

                dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());

                Intent intent = new Intent(this, CourseInfoActivity.class);
                intent.putExtra("semesterName", semesterName);
                intent.putExtra("selectedCourse", course);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition left_to_right_transition
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
