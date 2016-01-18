package com.example.mark.report_card.adding;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
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

import com.example.mark.report_card.R;
import com.example.mark.report_card.navigation.SelectSemesterActivity;
import com.example.mark.report_card.popups.CustomAlertPopUp;
import com.example.mark.report_card.settings.SettingsActivity;

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
        intent.putExtra("fromSelectCourseActivity", false);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.anim.left_to_right_transition, R.anim.left_to_right_transition_2).toBundle();
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
        Intent intent = new Intent(this, CustomAlertPopUp.class);
        intent.putExtra("position", position);
        intent.putExtra("previousActivity", "AddSemesterActivityDeleteCourse");
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

            Intent intent = new Intent(this, SelectSemesterActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "No semester name inputted", Toast.LENGTH_LONG).show();
        }

    }

    public void settingsButtonClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("previousActivity", "AddSemesterActivity");
        intent.putExtra("newSemesterName", semesterNameEditText.getText().toString());
        intent.putExtra("courses", courses);
        startActivity(intent);
    }

    /**
     * Method runs when the user clicks the back button. Prompts the user in whether or not they
     * want to cancel the current 'add semester' session and go to the main menu.
     */
    public void onBackPressed() {
        returnToSelectSemesterPopUp();
    }

    /**
     * Method that makes a popup to confirm that the user wants to go back to the main menu and
     * lose all information from the new semester
     */
    private void returnToSelectSemesterPopUp() {
        Intent intent = new Intent(this, CustomAlertPopUp.class);
        intent.putExtra("previousActivity", "AddSemesterActivityBack");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //Prevent transition animation
        startActivity(intent);
    }

}
