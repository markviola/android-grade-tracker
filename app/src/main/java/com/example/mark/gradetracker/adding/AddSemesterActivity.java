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
import com.example.mark.gradetracker.navigation.MainMenuActivity;
import com.example.mark.gradetracker.navigation.SelectSemesterActivity;

import java.util.ArrayList;

import adapters.AddCourseListAdapter;
import data.Course;
import data.Semester;
import managers.SemesterManager;

public class AddSemesterActivity extends AppCompatActivity {

    private String TAG = "customFilter";

    //List of courses to add in the new semester
    ArrayList<Course> courses;

    //Initializing relevant activity widgets
    EditText semesterNameEditText;
    TextView selectSemesterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        semesterNameEditText = (EditText) findViewById(R.id.semesterNameEditText);
        selectSemesterTitle = (TextView) findViewById(R.id.selectSemesterTitle);

        //Change the header font to Montserrat-Bold
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Bold.ttf");
        selectSemesterTitle.setTypeface(font);

        Intent intent = getIntent();

        //Retrieve courses information
        if (intent.getSerializableExtra("courses") == null){
            courses = new ArrayList<>();
        } else {
            courses = (ArrayList<Course>) intent.getSerializableExtra("courses");
        }

        //Retrieve semester name information
        if (!(intent.getSerializableExtra("newSemesterName") == null)){
            semesterNameEditText.setText((String)intent.getSerializableExtra("newSemesterName"));
        }

        ListAdapter coursesAdapter = new AddCourseListAdapter(this, courses);
        ListView coursesListView = (ListView) findViewById(R.id.newCoursesList);
        coursesListView.setAdapter(coursesAdapter);

        coursesListView.setOnItemClickListener(
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
    public void addCourseButtonClicked(View view){
        Intent intent = new Intent(this, AddCourseActivity.class);

        intent.putExtra("newSemesterName", semesterNameEditText.getText().toString());
        intent.putExtra("courses", courses);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.animation, R.anim.animation2).toBundle();
        startActivity(intent, bndlanimation);
    }

    /**
     * Deletes the specifically selected course from the current list of courses to add
     * @param view The current view of the app
     */
    public void deleteButtonClicked(View view){
        int position = (Integer) view.getTag();
        Log.i(TAG, "position: " + position);
        //Add popup to prompt user if the really want to delete

        deletePopUp(position);
    }

    /**
     * Method that makes a popup to confirm the deletion of a course
     * @param position The position of the course in the ArrayList
     */
    private void deletePopUp(final int position) {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Delete Course?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCourse(position);
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
     * Delete the course from the current ArrayList of newly added courses
     * @param position The position of the course in the ArrayList
     */
    private void deleteCourse(int position){
        courses.remove(position);
        Intent intent = new Intent(this, AddSemesterActivity.class);
        intent.putExtra("newSemesterName", semesterNameEditText.getText().toString());
        intent.putExtra("courses", courses);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation

        startActivity(intent);
    }

    public void addSemesterButtonClicked(View view){

        if (!semesterNameEditText.getText().toString().equals("")){
            Semester newSemester = new Semester(semesterNameEditText.getText().toString(), courses);
            SemesterManager semesterManager = SemesterManager.getInstance(this);

            if(!semesterManager.addSemester(newSemester)){
                Toast.makeText(this, "The semester name is already being used!", Toast.LENGTH_LONG).show();
            }

        } else {
            //Don't add anything
            //Prompt the user asking if there needs to
        }

        Intent intent = new Intent(this, SelectSemesterActivity.class);
        startActivity(intent);
    }

    /**
     * Method that makes a popup to confirm the deletion of a course
     * @param position The position of the course in the ArrayList
     */
    private void noSemesterNamePopUp(final int position) {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Delete Course?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCourse(position);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        myAlert.show();
    }

    public void settingsButtonClicked(View view){

    }

    /**
     * Method runs when the user clicks the back button. Prompts the user in whether or not they
     * want to cancel the current 'add semester' session and go to the main menu.
     */
    public void onBackPressed() {
        returnToMainMenuPopUp();
    }

    /**
     * Method that makes a popup to confirm that the user wants to go back to the main menu and
     * lose all information from the new semester
     */
    private void returnToMainMenuPopUp() {
        final AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage("Go back to the main menu? New semester will not be added")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToMainMenu();
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
     * Take the user to the main menu
     */
    private void goToMainMenu(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
