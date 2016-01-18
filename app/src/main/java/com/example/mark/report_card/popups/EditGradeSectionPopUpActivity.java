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
import com.example.mark.report_card.navigation.CourseInfoActivity;

import data.Course;
import managers.DBManager;
import data.GradeSection;
import data.Semester;
import managers.SemesterManager;

public class EditGradeSectionPopUpActivity extends AppCompatActivity {

    TextView editSectionTitle;
    EditText sectionNameEditText;
    EditText sectionWeightEditText;

    String semesterName;
    Course selectedCourse;
    GradeSection selectedGradeSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grade_section_pop_up);

        editSectionTitle = (TextView) findViewById(R.id.editSectionTitle);
        sectionNameEditText = (EditText) findViewById(R.id.sectionNameEditText);
        sectionWeightEditText = (EditText) findViewById(R.id.sectionWeightEditText);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        editSectionTitle.setTypeface(font);

        Intent intent = getIntent();
        semesterName = (String) intent.getSerializableExtra("semesterName");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");
        selectedGradeSection = (GradeSection) intent.getSerializableExtra("selectedGradeSection");

        sectionNameEditText.setText(selectedGradeSection.getSectionName());
        sectionWeightEditText.setText(String.format("%.2f", selectedGradeSection.getWeight()));

        changeActivitySize();
    }

    private void changeActivitySize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8 * width), (int) (0.5 * height));
    }

    public void editSectionButtonClicked(View view){

        //Add confirmation popup here to makesure the user wants to update the grade section

        DBManager dbManager = DBManager.getInstance(this);
        SemesterManager semesterManager = SemesterManager.getInstance(this);

        Semester semester = semesterManager.getSemester(semesterName);
        Course course = semester.getCourseByName(selectedCourse.getName());
        GradeSection gradeSection = course.getGradeSectionByName(selectedGradeSection.getSectionName());
        gradeSection.setSectionName(sectionNameEditText.getText().toString());
        gradeSection.setWeight(Double.parseDouble(sectionWeightEditText.getText().toString()));

        dbManager.updateSemesterInfo(semesterName, semester.getCoursesStr());

        Intent intent = new Intent(this, CourseInfoActivity.class);
        intent.putExtra("semesterName", semesterName);
        intent.putExtra("selectedCourse", course);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);

    }
}
