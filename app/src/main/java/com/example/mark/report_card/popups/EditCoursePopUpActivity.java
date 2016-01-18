package com.example.mark.report_card.popups;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mark.report_card.R;
import com.example.mark.report_card.navigation.SelectCourseActivity;

import data.Course;
import managers.DBManager;
import data.Semester;
import managers.SemesterManager;

public class EditCoursePopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView editCourseTitle;
    EditText courseNameEditText;
    EditText courseCodeEditText;

    String semesterName;
    Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_pop_up);

        editCourseTitle = (TextView) findViewById(R.id.editCourseTitle);
        courseNameEditText = (EditText) findViewById(R.id.courseNameEditText);
        courseCodeEditText = (EditText) findViewById(R.id.courseCodeEditText);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        editCourseTitle.setTypeface(font);

        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");

        courseNameEditText.setText(selectedCourse.getName());
        courseCodeEditText.setText(selectedCourse.getCode());

        changeActivitySize();
    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8 * width), (int) (0.5 * height));
    }

    public void editCourseButtonClicked(View view){

        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);

        Semester semester = semesterManager.getSemester(semesterName);
        Course course = semester.getCourseByName(selectedCourse.getName());
        course.setName(courseNameEditText.getText().toString());
        course.setCode(courseCodeEditText.getText().toString());

        dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());

        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition left_to_right_transition
        startActivity(intent);
    }
}
