package com.example.mark.report_card.popups;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
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

import com.example.mark.report_card.adding.AddGradeSectionActivity;
import com.example.mark.report_card.R;
import com.example.mark.report_card.navigation.TitleScreenActivity;

import java.util.ArrayList;

import adapters.AddBulkMarksListAdapter;
import data.Course;
import data.GradeSection;
import data.Mark;
import managers.SettingsManager;

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
    String newCourseCode;
    String gradeSectionName;
    String gradeSectionWeight;
    ArrayList<Mark> newMarks;
    boolean fromSelectCourseActivity;
    boolean fromCourseInfoActivity;
    Course selectedCourse;


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
        newCourseCode = (String) intent.getSerializableExtra("newCourseCode");
        gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        gradeSectionName = (String) intent.getSerializableExtra("gradeSectionName");
        gradeSectionWeight = (String) intent.getSerializableExtra("gradeSectionWeight");
        marks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
        fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");
        fromCourseInfoActivity = (boolean) intent.getSerializableExtra("fromCourseInfoActivity");
        selectedCourse = (Course) intent.getSerializableExtra("selectedCourse");

        if(intent.getSerializableExtra("markNameTemplate") != null){
            markNameTemplateEditText.setText((String) intent.getSerializableExtra("markNameTemplate"));
        }

        if(intent.getSerializableExtra("newMarks") != null){
            newMarks = (ArrayList<Mark>) intent.getSerializableExtra("newMarks");
            ListAdapter marksAdapter = new AddBulkMarksListAdapter(this, newMarks);
            newMarksList.setAdapter(marksAdapter);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.8*width),(int) (0.9*height));
    }

    public void generateMarksButtonClicked(View view){
        String templateMarkName = markNameTemplateEditText.getText().toString();
        int numMarks = Integer.parseInt(numMarksSpinner.getSelectedItem().toString());
        newMarks = new ArrayList<>();

        for(int i=0; i<numMarks; i++){
            newMarks.add(new Mark(templateMarkName+ " #" + (i+1), null));
        }

        ListAdapter marksAdapter = new AddBulkMarksListAdapter(this, newMarks);
        newMarksList.setAdapter(marksAdapter);

    }

    /**
     * Adds all of the newly generated marks into the final list of marks for the current
     * grade section being added
     * @param view The view of AddBulkMarkPopUpActivity
     */
    public void addMarksButtonClicked(View view){

        //marks ArrayList altered in the corresponding list adapter so newMarks will contain
        //updated information on the new grades
        marks.addAll(newMarks);
        goToAddGradeSection();

    }

    /**
     * Deletes the specifically selected course from the current list of courses to add
     * @param view The view of the specific list row being clicked
     */
    public void deleteButtonClicked(View view){
        int position = (Integer) view.getTag();
        deletePopUp(position);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBackPressed() {
        goToAddGradeSection();
    }

    /**
     * Method that makes a popup to confirm the deletion of a course
     * @param position The position of the course in the ArrayList
     */
    private void deletePopUp(final int position) {
        Intent intent = new Intent(this, CustomAlertPopUp.class);
        intent.putExtra("previousActivity", "AddBulkMarkPopUpActivity");
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", newCourseName);
        intent.putExtra("newCourseCode", newCourseCode);
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("gradeSectionName", gradeSectionName);
        intent.putExtra("gradeSectionWeight", gradeSectionWeight);
        intent.putExtra("marks", marks);
        intent.putExtra("markNameTemplate", markNameTemplateEditText.getText().toString());
        intent.putExtra("newMarks", newMarks);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        intent.putExtra("fromCourseInfoActivity", fromCourseInfoActivity);
        intent.putExtra("selectedCourse", selectedCourse);
        intent.putExtra("position", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

    private void goToAddGradeSection() {
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
    }

}
