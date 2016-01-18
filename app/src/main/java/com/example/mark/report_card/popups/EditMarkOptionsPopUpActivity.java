package com.example.mark.report_card.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.mark.report_card.R;

import data.Course;
import data.GradeSection;
import data.Mark;

public class EditMarkOptionsPopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView markTitleText;

    String semesterName;
    Course currentCourse;
    GradeSection selectedGradeSection;
    Mark selectedMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mark_options_pop_up);

        markTitleText = (TextView) findViewById(R.id.markTitleText);

        Intent intent = getIntent();

        semesterName = (String) intent.getSerializableExtra("semesterName");
        currentCourse = (Course) intent.getSerializableExtra("currentCourse");
        selectedGradeSection = (GradeSection) intent.getSerializableExtra("selectedGradeSection");
        selectedMark = (Mark) intent.getSerializableExtra("selectedMark");

        markTitleText.setText(String.format("\"%s\"\nMark", selectedMark.getName()));

        changeActivitySize();
    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.7 * width), (int) (0.35 * height));
    }

    public void editMarkButtonClicked(View view){
        Intent intent = new Intent(this, AddEditSingleMarkPopUpActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("currentCourse", currentCourse);
        intent.putExtra("selectedGradeSection", selectedGradeSection);
        intent.putExtra("selectedMark", selectedMark);
        intent.putExtra("previousActivity", "EditMarkOptionsPopUpActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    public void deleteMarkButtonClicked(View view){
        Intent intent = new Intent(this, CustomAlertPopUp.class);
        intent.putExtra("previousActivity", "EditMarkOptionsPopUpActivity");
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", currentCourse);
        intent.putExtra("selectedGradeSection", selectedGradeSection);
        intent.putExtra("selectedMark", selectedMark);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    public void cancelButtonClicked(View view){
        this.finish();
    }
}
