package com.example.mark.gradetracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.AddMarkListAdapter;
import data.Course;
import data.GradeSection;
import data.GradeSectionAllMarks;
import data.GradeSectionTopMarks;
import data.Mark;

public class AddGradeSectionActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    EditText gradeSectionNameEditText;
    EditText topMarksEditText;
    CheckBox isTopMarksCheckBox;
    TextView addGradeSectionTitle;
    TextView topMarksText1;
    TextView topMarksText2;

    ArrayList<Course> courses;
    ArrayList<GradeSection> gradeSections;
    ArrayList<Mark> marks;
    String newSemesterName;
    String newCourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade_section);

        gradeSectionNameEditText = (EditText) findViewById(R.id.gradeSectionNameEditText);
        topMarksEditText = (EditText) findViewById(R.id.topMarksEditText);
        isTopMarksCheckBox = (CheckBox) findViewById(R.id.isTopMarksCheckBox);
        addGradeSectionTitle = (TextView) findViewById(R.id.addGradeSectionTitle);
        topMarksText1 = (TextView) findViewById(R.id.topMarksText1);
        topMarksText2 = (TextView) findViewById(R.id.topMarksText2);

        topMarksEditText.setVisibility(View.GONE);
        topMarksText1.setVisibility(View.GONE);
        topMarksText2.setVisibility(View.GONE);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addGradeSectionTitle.setTypeface(font);

        Intent intent = getIntent();

        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");

        if(intent.getSerializableExtra("marks") == null){
            marks = new ArrayList<>();
        } else {
            marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
        }

        if(intent.getSerializableExtra("gradeSectionName") != null){
            gradeSectionNameEditText.setText((String) intent.getSerializableExtra("gradeSectionName"));
        }

        ListAdapter marksAdapter = new AddMarkListAdapter(this, marks);
        ListView marksListView = (ListView) findViewById(R.id.newMarksList);
        marksListView.setAdapter(marksAdapter);

        marksListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String course = String.valueOf(parent.getItemAtPosition(position));
                    }
                }
        );
    }

    public void addMarkButtonClicked(View view){
        Intent intent = new Intent(this, AddMarkOptionsPopUpActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionNameEditText.getText().toString());
        intent.putExtra("marks", marks);
        startActivity(intent);

    }

    public void addGradeSectionButtonClicked(View view){
        if(gradeSectionNameEditText.getText().toString().equals("")){
            Toast.makeText(this, "No name for this grade section", Toast.LENGTH_LONG).show();
        } else {
            if(isTopMarksCheckBox.isChecked()){
                if(isValidInteger(topMarksEditText.getText().toString())){
                    GradeSection newGradeSection = new GradeSectionTopMarks(gradeSectionNameEditText.getText().toString(),
                            0, Integer.parseInt(topMarksEditText.getText().toString()));

                    for(int i =0; i<marks.size(); i++){
                        newGradeSection.addMark(marks.get(i));
                    }

                    gradeSections.add(newGradeSection);

                    Intent intent = new Intent(this, AddCourseActivity.class);
                    intent.putExtra("newSemesterName", newSemesterName);
                    intent.putExtra("courses", courses);
                    intent.putExtra("newCourseName", newCourseName);
                    intent.putExtra("gradeSections", gradeSections);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Enter a valid number of grades", Toast.LENGTH_LONG).show();
                }
            } else {
                GradeSection newGradeSection = new GradeSectionAllMarks(gradeSectionNameEditText.getText().toString(),
                        0);
                for(int i =0; i<marks.size(); i++){
                    newGradeSection.addMark(marks.get(i));
                }
                gradeSections.add(newGradeSection);

                Intent intent = new Intent(this, AddCourseActivity.class);
                intent.putExtra("newSemesterName", newSemesterName);
                intent.putExtra("courses", courses);
                intent.putExtra("newCourseName", newCourseName);
                intent.putExtra("gradeSections", gradeSections);
                startActivity(intent);
            }
        }

    }

    public void isTopMarksCheckBoxClicked(View view){
        if(isTopMarksCheckBox.isChecked()){
            topMarksEditText.setVisibility(View.VISIBLE);
            topMarksText1.setVisibility(View.VISIBLE);
            topMarksText2.setVisibility(View.VISIBLE);
        } else{
            topMarksEditText.setVisibility(View.GONE);
            topMarksText1.setVisibility(View.GONE);
            topMarksText2.setVisibility(View.GONE);
        }
    }

    public void settingsButtonClicked(View view){

    }

    private boolean isValidInteger(String str){
        int i;

        try {
            i = Integer.parseInt(str);
        } catch(NumberFormatException nfe){
            return false;
        }

        if(i > 0){
            return true;
        }
        return false;
    }
}
