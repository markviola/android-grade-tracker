package com.example.mark.gradetracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.AddBulkMarksListAdapter;
import adapters.AddCourseListAdapter;
import data.Course;
import data.GradeSection;
import data.Mark;

public class AddBulkMarkPopUpActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    TextView addMarksTitle;
    EditText markNameTemplateEditText;
    Spinner numMarksSpinner;
    ListView newMarksList;

    ArrayList<Course> courses;
    ArrayList<GradeSection> gradeSections;
    ArrayList<Mark> marks;
    String newSemesterName;
    String newCourseName;
    String gradeSectionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bulk_mark_pop_up);

        addMarksTitle = (TextView) findViewById(R.id.addMarksTitle);
        markNameTemplateEditText = (EditText) findViewById(R.id.markNameTemplateEditText);
        numMarksSpinner = (Spinner) findViewById(R.id.numMarksSpinner);
        newMarksList = (ListView) findViewById(R.id.newMarksList);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addMarksTitle.setTypeface(font);

        //Setup the Spinner object
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, numbers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numMarksSpinner.setAdapter(dataAdapter);

        Intent intent = getIntent();
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        newCourseName = (String) intent.getSerializableExtra("newCourseName");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
        marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8*width),(int) (0.9*height));
    }

    public void generateMarksButtonClicked(View view){
        String templateMarkName = markNameTemplateEditText.getText().toString();
        int numMarks = Integer.parseInt(numMarksSpinner.getSelectedItem().toString());
        ArrayList<Mark> newMarks = new ArrayList<>();

        for(int i=0; i<numMarks; i++){
            newMarks.add(new Mark(templateMarkName+ " #" + (i+1), null));
        }

        ListAdapter marksAdapter = new AddBulkMarksListAdapter(this, newMarks);
        newMarksList.setAdapter(marksAdapter);

    }

    public void addMarksButtonClicked(View view){

    }

    public void deleteButtonClicked(View view){

    }
}
