package com.example.mark.gradetracker.adding;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
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

import com.example.mark.gradetracker.R;
import com.example.mark.gradetracker.navigation.SelectCourseActivity;

import java.util.ArrayList;

import adapters.AddGradeSectionListAdapter;
import data.Course;
import data.DBManager;
import data.GradeSection;
import data.Semester;
import managers.SemesterManager;

public class AddCourseActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    ArrayList<GradeSection> gradeSections;
    ArrayList<Course> courses;
    //String semesterName;
    String newSemesterName;
    boolean fromSelectCourseActivity;

    EditText courseNameEditText;
    EditText courseCodeEditText;
    TextView addCourseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseNameEditText = (EditText) findViewById(R.id.courseNameEditText);
        courseCodeEditText = (EditText) findViewById(R.id.courseCodeEditText);
        addCourseTitle = (TextView) findViewById(R.id.addCourseTitle);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        addCourseTitle.setTypeface(font);

        Intent intent = getIntent();
        courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        newSemesterName = (String) intent.getSerializableExtra("newSemesterName");
        fromSelectCourseActivity = (boolean) intent.getSerializableExtra("fromSelectCourseActivity");

        //Get the already created grade sections for the course the user is currently creating
        if (intent.getSerializableExtra("gradeSections") == null){
            gradeSections = new ArrayList<>();
        } else {
            gradeSections = (ArrayList<GradeSection>) intent.getSerializableExtra("gradeSections");
        }

        if(intent.getSerializableExtra("newCourseName") != null){
            courseNameEditText.setText((String) intent.getSerializableExtra("newCourseName"));
        }

        if(intent.getSerializableExtra("newCourseCode") != null){
            courseCodeEditText.setText((String) intent.getSerializableExtra("newCourseCode"));
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void addGradeSectionButtonClicked(View view){
        Intent intent = new Intent(this, AddGradeSectionActivity.class);

        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", courseNameEditText.getText().toString());
        intent.putExtra("newCourseCode", courseCodeEditText.getText().toString());
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("fromSelectCourseActivity", fromSelectCourseActivity);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.left_to_right_transition, R.anim.left_to_right_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

    /**
     * Deletes the specifically selected grade section from the current list of grade sections
     * @param view The view of the AddCourseActivity activity
     */
    public void deleteButtonClicked(View view){
        int position = (Integer) view.getTag();
        deletePopUp(position);
    }

    /**
     * Method that makes a popup to confirm the deletion of a grade section
     * @param position The position of the grade section in the ArrayList
     */
    private void deletePopUp(final int position) {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Delete Mark?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteGradeSection(position);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        myAlert.show();
    }

    /**
     * Delete the grade section from the current ArrayList of newly added marks
     * @param position The position of the mark in the ArrayList
     */
    private void deleteGradeSection(int position){
        gradeSections.remove(position);
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        intent.putExtra("newCourseName", courseNameEditText.getText().toString());
        intent.putExtra("newCourseCode", courseCodeEditText.getText().toString());
        intent.putExtra("gradeSections", gradeSections);
        intent.putExtra("fromSelectCourseActivity", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition left_to_right_transition

        startActivity(intent);
    }

    public void addCourseButtonClicked(View view){

        if (!courseNameEditText.getText().toString().equals("")){
            Course newCourse;
            if(!courseCodeEditText.getText().toString().equals("")){
                newCourse = new Course(courseNameEditText.getText().toString(),
                        courseCodeEditText.getText().toString());
            } else {
                newCourse = new Course(courseNameEditText.getText().toString(), null);
            }

            for(int i=0; i<gradeSections.size(); i++){
                newCourse.addGradeSection(gradeSections.get(i));
            }

            if(fromSelectCourseActivity){
                DBManager dbManager = DBManager.getInstance(this);
                SemesterManager semesterManager = SemesterManager.getInstance(this);

                Semester semester = semesterManager.getSemester(newSemesterName);
                semester.addCourse(newCourse);

                dbManager.updateSemesterInfo(newSemesterName, semester.getCoursesStr());

                Intent intent = new Intent(this, SelectCourseActivity.class);
                intent.putExtra("semesterName", newSemesterName);
                startActivity(intent);
            } else {
                courses.add(newCourse);

                Intent intent = new Intent(this, AddSemesterActivity.class);
                intent.putExtra("courses", courses);
                intent.putExtra("newSemesterName", newSemesterName);
                startActivity(intent);
            }

        } else {
            Toast.makeText(this, "No name for this course!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method runs when the user clicks the back button. Prompts the user in whether or not they
     * want to cancel the current 'add semester' session and go to the main menu.
     */
    public void onBackPressed() {
        returnToAddSemesterPopUp();
    }

    /**
     * Method that makes a popup to confirm that the user wants to go back to the main menu and
     * lose all information from the new semester
     */
    private void returnToAddSemesterPopUp() {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);

        if(fromSelectCourseActivity){
            myAlert.setMessage("Go back to the 'Select Course' screen? New course will not be added")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToSelectCourse();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
            myAlert.show();
        } else {
            myAlert.setMessage("Go back to the 'Add Semester' screen? New course will not be added")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToAddSemester();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
            myAlert.show();
        }

    }

    /**
     * Take the user to the AddSemesterActivity screen
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToAddSemester(){
        Intent intent = new Intent(this, AddSemesterActivity.class);

        intent.putExtra("newSemesterName", newSemesterName);
        intent.putExtra("courses", courses);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.right_to_left_transition, R.anim.right_to_left_transition_2).toBundle();
        startActivity(intent, bndlanimation);
    }

    /**
     * Take the user to the AddSemesterActivity screen
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void goToSelectCourse(){
        Intent intent = new Intent(this, SelectCourseActivity.class);
        intent.putExtra("semesterName", newSemesterName);
        startActivity(intent);
    }

}
