package com.example.mark.report_card.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.mark.report_card.R;

import data.Course;

public class EditCourseOptionsPopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView courseTitleText;

    String semesterName;
    Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_options_pop_up);

        courseTitleText = (TextView) findViewById(R.id.courseTitleText);

        Intent intent = getIntent();

        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");

        courseTitleText.setText(String.format("\"%s\"\nCourse", selectedCourse.getName()));

        changeActivitySize();
    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.7 * width), (int) (0.35 * height));
    }

    public void editCourseButtonClicked(View view){
        Intent intent = new Intent(this, EditCoursePopUpActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", selectedCourse);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);

    }

    public void deleteCourseButtonClicked(View view){
        Intent intent = new Intent(this, CustomAlertPopUp.class);
        intent.putExtra("previousActivity", "EditCourseOptionsPopUpActivity");
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", selectedCourse);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    public void cancelButtonClicked(View view){
        this.finish();
    }

}
