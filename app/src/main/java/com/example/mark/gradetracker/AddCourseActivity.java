package com.example.mark.gradetracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.AddCourseListAdapter;
import adapters.AddGradeSectionListAdapter;
import data.Course;
import data.GradeSection;

public class AddCourseActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    ArrayList<GradeSection> gradeSections;
    ArrayList<Course> courses;
    String newSemesterName;

    EditText courseNameEditText;
    TextView addCourseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseNameEditText = (EditText) findViewById(R.id.courseNameEditText);
        addCourseTitle = (TextView) findViewById(R.id.addCourseTitle);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addCourseTitle.setTypeface(font);

        Intent intent = getIntent();
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");

        //Get the already created grade sections for the course the user is currently creating
        if (intent.getSerializableExtra("gradeSections") == null){
            gradeSections = new ArrayList<>();
        } else {
            gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        }

        if(intent.getSerializableExtra("newCourseName") != null){
            courseNameEditText.setText((String) intent.getSerializableExtra("newCourseName"));
        }

        ListAdapter gradeSectionAdapter = new AddGradeSectionListAdapter(this, gradeSections);
        ListView gradeSectionListView = (ListView) findViewById(R.id.newGradeSectionsList);
        gradeSectionListView.setAdapter(gradeSectionAdapter);

        gradeSectionListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String course = String.valueOf(parent.getItemAtPosition(position));
                    }
                }
        );

    }

    /**
     * Take the user to AddCourseActivity to allow them to add a course to the new semester
     * @param view The current view of the app
     */
    public void addGradeSectionButtonClicked(View view){
        Intent intent = new Intent(this, AddGradeSectionActivity.class);

        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", courseNameEditText.getText().toString());
        intent.putExtra("gradeSections", gradeSections);

        startActivity(intent);
    }

    /**
     * Deletes the specifically selected course from the current list of courses to add
     * @param view The current view of the app
     */
    public void deleteButtonPressed(View view){
        int position = (Integer) view.getTag();
        Log.i(TAG, "position: " + position);
    }

    public void addCourseButtonClicked(View view){

        if (!courseNameEditText.getText().toString().equals("")){
            Course newCourse = new Course(courseNameEditText.getText().toString());
            for(int i=0; i<gradeSections.size(); i++){
                newCourse.addGradeSection(gradeSections.get(i));
            }
            courses.add(newCourse);

            Intent intent = new Intent(this, AddSemesterActivity.class);
            intent.putExtra("courses", courses);
            intent.putExtra("newSemesterName", newSemesterName);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No name for this course!", Toast.LENGTH_LONG).show();
        }
    }


}
